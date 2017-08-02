package wh1spr.olympians.zeus.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.Command;

public class ShutdownCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		if (args.size() == 0) BotControl.shutdown();
		else if (args.size() == 1) {
			switch (args.get(0).toLowerCase()){
			case "zeus":
				BotControl.shutdownWithExit();
				break;
			case "athena":
				BotControl.getAthena().shutdown();
				break;
			case "apollo":
				BotControl.getApollo().shutdown();
				break;
			case "dionysus":
				BotControl.getDionysus().shutdown();
				break;
			}
		}
	}
	
	@Override
	public String help() {
		return "`{0}{1} <bot name>`: Shuts down all bots, or the specified bot.";
	}

}
