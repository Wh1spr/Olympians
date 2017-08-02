package wh1spr.olympians.apollo.music.commands;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.apollo.music.AudioScheduler;
import wh1spr.olympians.apollo.music.GuildMusicManager;
import wh1spr.olympians.apollo.music.Music;
import wh1spr.olympians.command.Command;

public class NowCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		GuildMusicManager mng = Music.mngs.get(guild.getId());
		if (mng == null) {
			mng = new GuildMusicManager(Music.playerManager, guild);
			Music.mngs.put(guild.getId(), mng);
		}
		
		AudioPlayer player = mng.player;
	    
	    AudioTrack currentTrack = player.getPlayingTrack();
        if (currentTrack != null)
        {
            String title = currentTrack.getInfo().title;
            String position = getTimestamp(currentTrack.getPosition());
            String duration = getTimestamp(currentTrack.getDuration());

            String nowplaying = String.format("**Playing:** %s\n**Time:** [%s / %s]", title, position, duration);

            channel.sendMessage(nowplaying).queue();
        } else {
        	channel.sendMessage("The player is not currently playing anything!").queue();
        }
		
	}
	
	private static String getTimestamp(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours   = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        if (hours > 0)
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            return String.format("%02d:%02d", minutes, seconds);
    }

}
