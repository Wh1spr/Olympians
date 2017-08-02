package wh1spr.olympians.zeus.commands;

import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.Command;

public class ChangeGameCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
		
		if (args.size() > 1) {
			switch (args.get(0).toLowerCase()) {
			case "zeus":
				BotControl.getZeus().getJDA().getPresence().setGame(Game.of(message.getStrippedContent().split(" ",3)[2]));
				break;
			case "athena":
				BotControl.getAthena().getJDA().getPresence().setGame(Game.of(message.getStrippedContent().split(" ",3)[2]));
				break;
			case "apollo":
				BotControl.getApollo().getJDA().getPresence().setGame(Game.of(message.getStrippedContent().split(" ",3)[2]));
				break;
			case "dionysus":
				BotControl.getDionysus().getJDA().getPresence().setGame(Game.of(message.getStrippedContent().split(" ",3)[2]));
				break;
			}
		} else if (args.size() == 1) {
			switch (args.get(0).toLowerCase()) {
			case "zeus":
				BotControl.getZeus().getJDA().getPresence().setGame(null);
				break;
			case "athena":
				BotControl.getAthena().getJDA().getPresence().setGame(null);
				break;
			case "apollo":
				BotControl.getApollo().getJDA().getPresence().setGame(null);
				break;
			case "dionysus":
				BotControl.getDionysus().getJDA().getPresence().setGame(null);
				break;
			}
		}
	}

}
