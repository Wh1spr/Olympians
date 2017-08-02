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

public class AdminRevokeCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		String msg = "";
		for (User user : message.getMentionedUsers()) {
			if (Tools.isInFile("data/admins.txt", user.getId())) {
				msg += user.getAsMention() + "\n";
			}
		}
		
		if (!msg.equals(""))
			channel.sendMessage(new EmbedBuilder().addField("You are no longer above me.", msg, false).setColor(Color.orange).build()).queue();
		BotControl.usage.revokeAdmin(message.getMentionedUsers());
	}

}
