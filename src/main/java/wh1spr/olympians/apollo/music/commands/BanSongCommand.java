package wh1spr.olympians.apollo.music.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.Tools;
import wh1spr.olympians.command.Command;

public class BanSongCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		if (!args.isEmpty()) 
			if (!Tools.isInLineInFile("data/bannedsongs.txt", args.get(0))) {
				channel.sendMessage("**Banned video/song:** *" + args.get(0) +"*").queue();
				Tools.addLineToFile("data/bannedsongs.txt", args.get(0));
			}
		
	}

}
