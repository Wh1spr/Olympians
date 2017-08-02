package wh1spr.olympians.apollo.music.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import wh1spr.olympians.apollo.music.GuildMusicManager;
import wh1spr.olympians.apollo.music.Music;
import wh1spr.olympians.command.Command;

public class JoinCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		VoiceChannel vchannel;
		
		GuildMusicManager mng = Music.mngs.get(guild.getId());
		if (mng == null) {
			mng = new GuildMusicManager(Music.playerManager, guild);
			Music.mngs.put(guild.getId(), mng);
		}
		
		if (mng.player.getPlayingTrack() != null || !mng.scheduler.queue.isEmpty()) {
			channel.sendMessage("I'm still playing in another channel, I can't switch right now!").queue();
			return;
		} else if (args.size() == 0) {
			vchannel = guild.getVoiceChannels().get(0);
		} else {
			String name = message.getStrippedContent().split(" ", 2)[1];
			vchannel = guild.getVoiceChannelsByName(name, true).get(0);
		}
		if (channel == null) {
			channel.sendMessage("There is no channel with name ```" + message.getStrippedContent().split(" ", 2)[1] + "```").queue();
		}
        guild.getAudioManager().setSendingHandler(mng.getSendHandler());
        guild.getAudioManager().openAudioConnection(vchannel); 
		
	}

}
