package wh1spr.olympians.zeus.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.Command;
import wh1spr.olympians.zeus.Zeus;

public class CommandDisableCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		if (args.size() > 0) {
			for (String cmd : args) {
				Zeus.adminRegistry.removeCommand(cmd);
			}
		}
		
	}

}
