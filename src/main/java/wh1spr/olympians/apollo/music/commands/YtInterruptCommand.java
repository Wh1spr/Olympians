package wh1spr.olympians.apollo.music.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.apollo.music.GuildMusicManager;
import wh1spr.olympians.apollo.music.Music;
import wh1spr.olympians.command.Command;

public class YtInterruptCommand extends Command {
	
	public YtInterruptCommand(String url) {
		URL = url;
	}
	
	private final String URL;

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		
		GuildMusicManager mng = Music.mngs.get(guild.getId());
		if (mng == null) {
			mng = new GuildMusicManager(Music.playerManager, guild);
			Music.mngs.put(guild.getId(), mng);
		}
		
		loadAndPlay(mng, channel, URL, false);
		
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
            	if (mng.player.getPlayingTrack() != null) {
            		AudioTrack wasplaying = mng.player.getPlayingTrack();
            		long playingPos = wasplaying.getPosition();
	                List<AudioTrack> tracks = new ArrayList<AudioTrack>();
	            	while (!mng.scheduler.queue.isEmpty()) {
	            		tracks.add(mng.scheduler.queue.poll());
	            	}
	            	
	            	AudioTrack newTrack = wasplaying.makeClone();
	            	newTrack.setPosition(playingPos);
	            	tracks.add(0, newTrack);
	            	
	            	Iterator<AudioTrack> trackiterator = tracks.iterator();
	            	while(trackiterator.hasNext()) {
	            		mng.scheduler.queue.offer(trackiterator.next());
	            	} 
	            	mng.player.startTrack(track, false);
	            	mng.scheduler.noMessageOnNext = true;
	            } else {
	            	mng.player.startTrack(track, false);
	            }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {}

            @Override
            public void noMatches() {}

            @Override
            public void loadFailed(FriendlyException exception) {}
        });
    }

}
