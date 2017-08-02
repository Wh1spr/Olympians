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

public class ImmunityGrantCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isOwner(invoker.getUser())) return;
		
		for (User user : message.getMentionedUsers()) {
			if (!Tools.isInFile("data/immune.txt", user.getId())) {
				user.openPrivateChannel().complete().sendMessage("You are now immune from mutes, kicks, bans and deafens issued by the Olympians. Also, you cannot be denied or be revoked admin if you have that status. Immunity can only be revoked by Wh1spr and will only happen if you break his trust.").queue();
			}
		}
		
		BotControl.usage.makeImmune(message.getMentionedUsers().get(0));
	}

}
