package wh1spr.olympians.zeus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.Tools;

/**
 * This MessageHandler takes care of bans, kicks, mutes,... and the timed versions of these events.
 * We can assume most of these will be either permban or the timed versions of the others.
 * So, tempbans, timed mutes,...
 * 
 * @author Robbe
 */
public class UserControlMessageHandler extends ListenerAdapter{	
	
	private final String BC_PREFIX = "&&";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		try {
		
		if (!event.getMessage().getRawContent().startsWith(BC_PREFIX)) {
			return;
		}
		
		String[] command = event.getMessage().getRawContent().split(" ");
		
		//For now, ONLY BOT ADMINS, or Leaders in Maelstrom's case who are all admins, can use these commands
		// the check for staff above is there because in UsageMessageHandler, the same prefix is used, but staff
		// can do stuff as well.
		if (!BotControl.usage.isAdmin(event.getAuthor())) {
			return;
		}
		
		String a = command[0].toLowerCase();
		Calendar cal = Calendar.getInstance();
		
		switch (a.replaceFirst(BC_PREFIX, "")) {
		case "ban":
			if (event.getMessage().getMentionedUsers().size() == 1) {
				User toBan = event.getMessage().getMentionedUsers().get(0);
				if (command.length > 3) {
					command = event.getMessage().getRawContent().split(" ", 4);
					BotControl.userControl.permban(toBan, Integer.valueOf(command[2]), command[3]);
				}
				if (Tools.isInLineInFile("data/bans.txt", toBan.getId())) {
					event.getChannel().sendMessage("Banned user " + toBan.getName() + " with reason *" + command[4] + "*" ).queue();
				}
			}
			break;
		case "tempban":
			if (event.getMessage().getMentionedUsers().size() == 1) {
				User toBan = event.getMessage().getMentionedUsers().get(0);
				if (command.length > 4) {
					command = event.getMessage().getRawContent().split(" ", 5);
					
					Date now = cal.getTime();
					
					parseTime(command[3], cal);
					
					if (now.after(cal.getTime())) {
						event.getChannel().sendMessage("This time is in the past.").queue();
						return;
					}
					
					BotControl.userControl.tempban(toBan, command[4], cal.getTime(), Integer.valueOf(command[2]));
					
					if (Tools.isInLineInFile("data/tempbans.txt", toBan.getId())) {
						event.getChannel().sendMessage("Tempbanned user " + toBan.getName() + " with reason *" + command[4] + "*" ).queue();
					}
				}
			}
			break;
		case "kick":
			if (event.getMessage().getMentionedUsers().size() == 1) {
				User toKick = event.getMessage().getMentionedUsers().get(0);
				if (command.length > 2) {
					command = event.getMessage().getRawContent().split(" ", 3);
					BotControl.userControl.kick(toKick, command[2]);
				}
				if (Tools.isInLineInFile("data/kicks.txt", toKick.getId())) {
					event.getChannel().sendMessage("Kicked user " + toKick.getName() + " with reason *" + command[4] + "*" ).queue();
				}
			}
			
			break;
		
		case "default":
			break;
		}
	
	} catch (Exception e) {
		event.getChannel().sendMessage("This command was malformed, please try again.").queue();
	}
	
	}
	
	private boolean parseTime(String toParse, Calendar cal) {
		try {
			try {
				//Parse the time to yyyy.MM.dd-kk:mm:ss
				// If it fails, thus catching an exception, parse it to _m_d_h_m_s
				DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-kk:mm:ss");
				Date date = sdf.parse(toParse);
				cal.setTime(date);
				return true;
			} catch (Exception b) {
				// do nothing, code will go on
			}
			
			String[] time = toParse.split("M");
			if (time.length == 2) { cal.add(Calendar.MONTH, Integer.valueOf(time[0])); toParse = toParse.replaceFirst(time[0] + "M", "");}
			time = toParse.split("d");
			if (time.length == 2) { cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(time[0])); toParse = toParse.replaceFirst(time[0] + "d", "");}
			time = toParse.split("h");
			if (time.length == 2) { cal.add(Calendar.HOUR_OF_DAY, Integer.valueOf(time[0])); toParse = toParse.replaceFirst(time[0] + "h", "");}
			time = toParse.split("m");
			if (time.length == 2) { cal.add(Calendar.MINUTE, Integer.valueOf(time[0])); toParse = toParse.replaceFirst(time[0] + "m", "");}
			time = toParse.split("s");
			if (time.length == 2) { cal.add(Calendar.SECOND, Integer.valueOf(time[0])); toParse = toParse.replaceFirst(time[0] + "s", "");}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
