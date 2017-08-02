package wh1spr.olympians.apollo.music;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.core.entities.TextChannel;

public class AudioScheduler extends AudioEventAdapter {
	
	private boolean repeating = false;
    final AudioPlayer player;
    public final Queue<AudioTrack> queue;
    AudioTrack lastTrack;
    final TextChannel channel;

    /**
     * @param player The audio player this scheduler uses
     */
    public AudioScheduler(AudioPlayer player, TextChannel channel)
    {
        this.player = player;
        this.queue = new LinkedBlockingQueue<AudioTrack>();
        this.channel = channel;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track)
    {
    	if (!player.startTrack(track, true)) {
    		queue.offer(track);
    	}
    }

    /**
     * Start the next track, stopping the current on if it is playing.
     */
    public void nextTrack()
    {
    	
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
        if (channel != null) if (player.getPlayingTrack() != null) channel.sendMessage("**Playing: ** " + player.getPlayingTrack().getInfo().title).queue();
       
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        this.lastTrack = track;
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext)
        {
            if (repeating)
                player.startTrack(lastTrack.makeClone(), false);
            else
                nextTrack();
        }

    }

    public boolean isRepeating()
    {
        return repeating;
    }

    public void setRepeating(boolean repeating)
    {
        this.repeating = repeating;
    }

    public void shuffle()
    {
        Collections.shuffle((List<?>) queue);
    }

}
