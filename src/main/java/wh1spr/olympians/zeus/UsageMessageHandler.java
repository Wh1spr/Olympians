package wh1spr.olympians.zeus;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.BotUsage;

public class UsageMessageHandler extends ListenerAdapter {
	
	private final String BC_PREFIX = "&&";

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if (!BotControl.usage.isStaff(event.getAuthor(), event.getGuild())) { //admin check
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			return;
		}
		
		if (!event.getMessage().getRawContent().startsWith(BC_PREFIX)) {
			return;
		}
		
		String[] command = event.getMessage().getContent().split(" ",3);
		BotUsage usage = BotControl.usage;
		
		String a = command[0].toLowerCase();
		
		switch (a.replaceFirst(BC_PREFIX, "")) {
			case "allow":
				usage.allow(event.getMessage().getMentionedUsers());
				break;
			case "deny":
				usage.deny(event.getMessage().getMentionedUsers());
				break;
			case "grantadmin":
				if (event.getAuthor().getId().equals("204529799912226816")) {
					usage.makeAdmin(event.getMessage().getMentionedUsers());
				}
				break;
			case "revokeadmin":
				if (usage.isAdmin(event.getAuthor())) {
					usage.revokeAdmin(event.getMessage().getMentionedUsers());
				}
				break;
			case "grantimmunity":
				if (event.getAuthor().getId().equals("204529799912226816")) {
					usage.makeImmune(event.getMessage().getMentionedUsers().get(0));
				}
				break;
			case "revokeimmunity":
				if (event.getAuthor().getId().equals("204529799912226816")) {
					usage.revokeImmune(event.getMessage().getMentionedUsers().get(0));
				}
				break;
				
		}
	}
}
