package wh1spr.olympians.zeus;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import wh1spr.olympians.BotControl;

public class BotMessageHandler extends ListenerAdapter{
	
	private final String BC_PREFIX = "&&";
	
	@SuppressWarnings("unused")
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		//Since Zeus controls ALL the bots, he should have following commands:
		//shutdown (including other bots), start <name>, changegame [name] <game>, update, 
		if (false) { //admin check
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			return;
		}
		
		if (!event.getMessage().getRawContent().startsWith(BC_PREFIX)) {
			return;
		}
		
		String[] command = event.getMessage().getContent().split(" ",3);
		Guild guild = event.getGuild();
		
		String a = command[0].toLowerCase();
		
		switch (a) {
			case BC_PREFIX + "shutdown":
				if (command.length == 1) {
					BotControl.shutdown();
				} else {
					switch (command[1].toLowerCase()) {
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
				break;
			case BC_PREFIX + "start":
				if (command.length > 1) {
					switch (command[1].toLowerCase()) {
					case "athena":
						if (!BotControl.getAthena().isOnline()) BotControl.getAthena().run();
						break;
					case "zeus":
						event.getChannel().sendMessage("I'm already online you mortal fool!").queue();
						break;
					case "dionysus":
						if (!BotControl.getDionysus().isOnline()) BotControl.getDionysus().run();
						break;
					case "apollo":
						if (!BotControl.getApollo().isOnline()) BotControl.getApollo().run();
						break;
					case "default":
						//send something like oh yeah that bot doesnt exist
						break;
					}
				}
				break;
			case BC_PREFIX + "cg":
			case BC_PREFIX + "changegame":
				if (command.length > 2) {
					switch (command[1].toLowerCase()) {
					case "zeus":
						BotControl.getZeus().getJDA().getPresence().setGame(Game.of(command[2]));
						break;
					case "athena":
						BotControl.getAthena().getJDA().getPresence().setGame(Game.of(command[2]));
						break;
					case "apollo":
						BotControl.getApollo().getJDA().getPresence().setGame(Game.of(command[2]));
						break;
					case "dionysus":
						BotControl.getDionysus().getJDA().getPresence().setGame(Game.of(command[2]));
						break;
					}
				} else {
					//usage or something idk
				}
				break;
			case BC_PREFIX + "update":
			case BC_PREFIX + "u":
				BotControl.update();
				break;
		}
	}
}
