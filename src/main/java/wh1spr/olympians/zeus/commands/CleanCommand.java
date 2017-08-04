package wh1spr.olympians.zeus.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.Command;

//Possibilities for this command are:
// &&clean <int> 					Removes <int> past messages.
// &&clean @user1 @user2...			Removes messages by these users in the past 99 messages.
// &&clean <int> @user1 @user2...	Removes messages by these users int he past <int> messages.
public class CleanCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		if (!BotControl.usage.isAdmin(invoker.getUser())) return;
			
		if (!message.getMentionedUsers().isEmpty() && message.getMentionedUsers().size() < args.size()) {
			int nr = 0;
			try {
				nr = Integer.valueOf(args.get(0));
			} catch (Exception e) {
				return;
			}
			if (nr > 99) nr = 99; //max amount that can be deleted.
			nr++; //for the clean message itself.
			
			List<Message> messages = channel.getHistory().retrievePast(nr).complete();
			Set<Message> toDelete = new HashSet<Message>(); 
			
			for (User user : message.getMentionedUsers()) {
				for (Message msg : messages) {
					if (msg.getAuthor() == user) toDelete.add(msg);
				}
			}
			
			channel.deleteMessages(toDelete).queue();
		} else if (!message.getMentionedUsers().isEmpty()) {
			List<Message> messages = channel.getHistory().retrievePast(100).complete();
			Set<Message> toDelete = new HashSet<Message>(); 
			
			for (User user : message.getMentionedUsers()) {
				for (Message msg : messages) {
					if (msg.getAuthor() == user) toDelete.add(msg);
				}
			}
			
			channel.deleteMessages(toDelete).queue();
		} else if (args.size() == 1) {
			int nr = 0;
			try {
				nr = Integer.valueOf(args.get(0));
			} catch (Exception e) {
				return;
			}
			if (nr > 99) nr = 99; //max amount that can be deleted.
			nr++; //for the clean message itself.
			
			channel.deleteMessages(channel.getHistory().retrievePast(nr).complete()).queue();
		}
	}

}
