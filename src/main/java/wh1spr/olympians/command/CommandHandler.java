package wh1spr.olympians.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import wh1spr.olympians.BotControl;

public class CommandHandler extends ListenerAdapter {

	public CommandHandler(String prefix, CommandRegistry registry) {
		this.PREFIX = prefix;
		this.registry = registry;
	}
	
	private final String PREFIX;
	private final CommandRegistry registry;
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (event.getAuthor().isBot()) return; //no response on bots
		
		//no response on something that doesnt start with the right prefix
		if (!event.getMessage().getStrippedContent().startsWith(PREFIX)) return; 
		
		if (BotControl.usage.isDenied(event.getAuthor())) {
			return; // no response on denied users
		}
		
		//if this command exists
		String cmdName = event.getMessage().getStrippedContent().split(" ")[0].replaceFirst(PREFIX, "").toLowerCase();
		if (registry.getRegisteredCommandsAndAliases().contains(cmdName)) {
			Command cmd = registry.getCommand(cmdName).command;
			List<String> args = new ArrayList<String>();
			args.addAll(Arrays.asList(event.getMessage().getRawContent().split(" ")));
			args.remove(0);
			cmd.onCall(event.getJDA(), event.getGuild(), event.getChannel(), event.getMember(), event.getMessage(), args);
		}
		
	}
	
}
