package wh1spr.olympians.zeus;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.BotUsage;
import wh1spr.olympians.Tools;

/**
 * This class handles all the messages involving denied users, admins and immunity. Basically it controls
 * who can and who can't control the bot from within the Discord client.
 * 
 * @author Robbe
 */
public class UsageMessageHandler extends ListenerAdapter {
	
	private final String BC_PREFIX = "&&";

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		
		if (!event.getMessage().getRawContent().startsWith(BC_PREFIX)) {
			return;
		}
		
		if (!BotControl.usage.isStaff(event.getAuthor(), event.getGuild())) { //admin check
			event.getChannel().deleteMessageById(event.getMessageId()).queue();
			return;
		}
		
		String[] command = event.getMessage().getContent().split(" ",3);
		BotUsage usage = BotControl.usage;
		Guild guild = event.getGuild();
		
		
		String a = command[0].toLowerCase();
		String msg = "";
		
		switch (a.replaceFirst(BC_PREFIX, "")) {
			case "allow":
				for (User user : event.getMessage().getMentionedUsers()) {
					if (Tools.isInFile("data/denied.txt", user.getId())) {
						msg += user.getAsMention() + "\n";
					}
				}
				if (!msg.equals(""))
					event.getChannel().sendMessage(new EmbedBuilder().addField("Don't betray my trust again.", msg, false).setColor(Color.green).build()).queue();
				usage.allow(event.getMessage().getMentionedUsers());
				break;
			case "deny":
				for (User user : event.getMessage().getMentionedUsers()) {
					if (!Tools.isInFile("data/denied.txt", user.getId())) {
						if (!usage.isStaff(user, guild)) {	
							msg += user.getAsMention() + "\n";
						}
					}
				}
				if (!msg.equals(""))
					event.getChannel().sendMessage(new EmbedBuilder().addField("May these mortals live in eternal shame!", msg, false).setColor(Color.red).build()).queue();
				usage.deny(event.getMessage().getMentionedUsers());
				break;
			case "grantadmin": //Only I can grant admin to others, this ID is mine.
				if (event.getAuthor().getId().equals("204529799912226816")) {
					for (User user : event.getMessage().getMentionedUsers()) {
						if (!Tools.isInFile("data/admins.txt", user.getId())) {
							msg += user.getAsMention() + "\n";
						}
					}
					
					usage.makeAdmin(event.getMessage().getMentionedUsers());
					if (!msg.equals(""))
						event.getChannel().sendMessage(new EmbedBuilder().addField("Behold our new masters.", msg, false).setColor(Color.green).build()).queue();
				}
				break;
			case "revokeadmin":  //admins can revoke admin from others, except for when the others are immune.
				if (usage.isAdmin(event.getAuthor())) {
					for (User user : event.getMessage().getMentionedUsers()) {
						if (Tools.isInFile("data/admins.txt", user.getId())) {
							msg += user.getAsMention() + "\n";
						}
					}
					if (!msg.equals(""))
						event.getChannel().sendMessage(new EmbedBuilder().addField("You are no longer above me.", msg, false).setColor(Color.orange).build()).queue();
					usage.revokeAdmin(event.getMessage().getMentionedUsers());
				}
				break;
			case "grantimmunity": // Only I can grant or revoke Immunity. This ID is mine.
				if (event.getAuthor().getId().equals("204529799912226816")) {
					for (User user : event.getMessage().getMentionedUsers()) {
						if (!Tools.isInFile("data/immune.txt", user.getId())) {
							user.openPrivateChannel().complete().sendMessage("You are now immune from mutes, kicks, bans and deafens issued by the Olympians. Also, you cannot be denied or be revoked admin if you have that status. Immunity can only be revoked by Wh1spr and will only happen if you break his trust.").queue();
						}
					}
					
					usage.makeImmune(event.getMessage().getMentionedUsers().get(0));
				}
				break;
			case "revokeimmunity":
				if (event.getAuthor().getId().equals("204529799912226816")) {
					for (User user : event.getMessage().getMentionedUsers()) {
						if (Tools.isInFile("data/immune.txt", user.getId())) {
							user.openPrivateChannel().complete().sendMessage("You are now no longer immune to mutes, kicks, bans and deafens issued by the Olympians. Also, you can now be denied to use the bots and be revoked admin status if you have it.").queue();
						}
					}
					usage.revokeImmune(event.getMessage().getMentionedUsers().get(0));
				}
				break;
				
		}
	}
}
