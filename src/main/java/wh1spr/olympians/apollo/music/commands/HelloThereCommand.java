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

public class HelloThereCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		
		GuildMusicManager mng = Music.mngs.get(guild.getId());
		if (mng == null) {
			mng = new GuildMusicManager(Music.playerManager, guild);
			Music.mngs.put(guild.getId(), mng);
		}
		
		boolean skip = false;
		if (!mng.scheduler.queue.isEmpty()) skip = true;
		
		loadAndPlay(mng, channel, "eaEMSKzqGAg", false);
		
		if (skip)
			mng.scheduler.nextTrack();
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
                List<AudioTrack> tracks = new ArrayList<AudioTrack>();
            	while (!mng.scheduler.queue.isEmpty()) {
            		tracks.add(mng.scheduler.queue.poll());
            	}
            	tracks.add(0, track);
            	
            	Iterator<AudioTrack> trackiterator = tracks.iterator();
            	while(trackiterator.hasNext()) {
            		mng.scheduler.queue.offer(trackiterator.next());
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
