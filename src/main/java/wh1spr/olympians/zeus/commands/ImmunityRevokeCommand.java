package wh1spr.olympians.zeus.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.Tools;
import wh1spr.olympians.command.Command;

public class ImmunityRevokeCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isOwner(invoker.getUser())) return;
		
		for (User user : message.getMentionedUsers()) {
			if (Tools.isInFile("data/immune.txt", user.getId())) {
				user.openPrivateChannel().complete().sendMessage("You are now no longer immune to mutes, kicks, bans and deafens issued by the Olympians. Also, you can now be denied to use the bots and be revoked admin status if you have it.").queue();
			}
		}
		BotControl.usage.revokeImmune(message.getMentionedUsers().get(0));
	}

}
