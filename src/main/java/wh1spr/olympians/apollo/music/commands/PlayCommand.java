package wh1spr.olympians.apollo.music.commands;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.Tools;
import wh1spr.olympians.apollo.music.AudioScheduler;
import wh1spr.olympians.apollo.music.GuildMusicManager;
import wh1spr.olympians.apollo.music.Music;
import wh1spr.olympians.command.Command;

public class PlayCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		
		GuildMusicManager mng = Music.mngs.get(guild.getId());
		if (mng == null) {
			mng = new GuildMusicManager(Music.playerManager, guild);
			Music.mngs.put(guild.getId(), mng);
		}
		
		AudioPlayer player = mng.player;
	    AudioScheduler scheduler = mng.scheduler;
		
		if (args.size() == 0) {
            if (player.isPaused()) {
                player.setPaused(false);
                channel.sendMessage("Playback as been resumed.").queue();
            } else if (player.getPlayingTrack() != null) {
                channel.sendMessage("Player is already playing.").queue();
            } else if (scheduler.queue.isEmpty()) {
                channel.sendMessage("The current audio queue is empty! Add something to the queue first!").queue();
            }
        } else {
        	for (String ban : Tools.getLinesFromFile("data/bannedsongs.txt")) {
    			if (args.get(0).contains(ban)) {
    				channel.sendMessage("Sorry, but that song/video has been banned.").queue();
    				return;
    			}
    		}
            loadAndPlay(mng, channel, args.get(0), true);
        }
	}
	
	private void loadAndPlay(GuildMusicManager mng, final MessageChannel channel, String url, final boolean addPlaylist) {
		
        final String trackUrl;

        //Strip <>'s that prevent discord from embedding link resources
        if (url.startsWith("<") && url.endsWith(">"))
            trackUrl = url.substring(1, url.length() - 1);
        else
            trackUrl = url;

        Music.playerManager.loadItemOrdered(mng, trackUrl, new AudioLoadResultHandler()
        {
            @Override
            public void trackLoaded(AudioTrack track)
            {
                String msg = "Adding to queue: " + track.getInfo().title;

                mng.scheduler.queue(track);
                channel.sendMessage(msg).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist)
            {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                List<AudioTrack> tracks = playlist.getTracks();


                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                if (addPlaylist)
                {
                    channel.sendMessage("Adding **" + playlist.getTracks().size() +"** tracks to queue from playlist: " + playlist.getName()).queue();
                    tracks.forEach(mng.scheduler::queue);
                }
                else
                {
                    channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                    mng.scheduler.queue(firstTrack);
                }
            }

            @Override
            public void noMatches()
            {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception)
            {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

}
