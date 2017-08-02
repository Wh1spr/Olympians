package wh1spr.olympians.apollo.music.commands;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.apollo.music.AudioScheduler;
import wh1spr.olympians.apollo.music.GuildMusicManager;
import wh1spr.olympians.apollo.music.Music;
import wh1spr.olympians.command.Command;

public class SkipCommand extends Command {

	//TODO: skipping multiple songs?
	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		GuildMusicManager mng = Music.mngs.get(guild.getId());
		if (mng == null) {
			mng = new GuildMusicManager(Music.playerManager, guild);
			Music.mngs.put(guild.getId(), mng);
		}
		
		AudioPlayer player = mng.player;
	    AudioScheduler scheduler = mng.scheduler;
		
		if (player.getPlayingTrack() == null) {
			channel.sendMessage("I'm not playing anything.").queue();
		} else {
			channel.sendMessage("Skipped " + player.getPlayingTrack().getInfo().title).queue();
			scheduler.nextTrack();
		}
	}

}
