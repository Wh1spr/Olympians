package wh1spr.olympians.zeus.commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.Tools;
import wh1spr.olympians.command.Command;

public class AllowCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		String msg = "";
		for (User user : message.getMentionedUsers()) {
			if (Tools.isInFile("data/denied.txt", user.getId())) {
				msg += user.getAsMention() + "\n";
			}
		}
		
		if (!msg.equals(""))
			channel.sendMessage(new EmbedBuilder().addField("Don't betray my trust again.", msg, false).setColor(Color.green).build()).queue();
		BotControl.usage.allow(message.getMentionedUsers());
	}

}
