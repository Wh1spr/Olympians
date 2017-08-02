package wh1spr.olympians.zeus.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.Command;

public class StartCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		if (args.size() == 1) {
			switch (args.get(0).toLowerCase()) {
			case "athena":
				if (!BotControl.getAthena().isOnline()) BotControl.getAthena().run();
				break;
			case "zeus":
				channel.sendMessage("I'm already online you mortal fool!").queue();
				break;
			case "dionysus":
				if (!BotControl.getDionysus().isOnline()) BotControl.getDionysus().run();
				break;
			case "apollo":
				if (!BotControl.getApollo().isOnline()) BotControl.getApollo().run();
				break;
			}
		}
	}

}
