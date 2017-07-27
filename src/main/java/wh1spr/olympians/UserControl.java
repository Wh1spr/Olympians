package wh1spr.olympians;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.User;

/**
 * This class takes care of the timed events from tempbans/kicks/mutes/deafens and perm bans and kicks.
 * 
 * @author Robbe
 */
public class UserControl {
	
	private static Timer timer;
	private static final List<TimerTask> scheduledTasks = new ArrayList<TimerTask>();
	
	public void shutdown() {
		Iterator it = scheduledTasks.iterator();
		while (it.hasNext()) {
			TimerTask next = (TimerTask) it.next();
			if (next instanceof unBanTask) {
				unBanTask task = (unBanTask) next;
				if (!task.hasRun) {
					task.run();
				}
			} else if (next instanceof Object) {
				//this is for other tasks.
			}
			
		}
	}
	
	public UserControl() {
		guild = BotControl.getZeus().getJDA().getGuildById("319896741233033216");
		timer = new Timer();
	}

	private static Guild guild;
	
	public void permban(User user, int days, String reason) {
		if (BotControl.usage.isImmune(user)) return;
		if (BotControl.usage.isAdmin(user)) return;
		guild.getController().ban(user, days, "BAN issued by Leader").queue();
		Tools.addLineToFile("data/bans.txt", String.format("%s - %s with ID [%s] - %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd kk:mm:ss")),user.getName(), user.getId(), reason));
	}
	
	public void kick(User user, String reason) {
		if (BotControl.usage.isImmune(user)) return;
		if (BotControl.usage.isAdmin(user)) return;
		guild.getController().kick(user.getId(), "KICK issued by Leader").queue();
		Tools.addLineToFile("data/kicks.txt", String.format("%s - %s with ID [%s] - %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd kk:mm:ss")),user.getName(), user.getId(), reason));
	}
	
	/**
	 * @param time The duration in milliseconds until ban has ended.
	 */
	public void tempban(User user, String reason, long time, int daysToDelete) {
		if (BotControl.usage.isImmune(user)) return;
		if (BotControl.usage.isAdmin(user)) return;
		guild.getController().ban(user, daysToDelete, "TEMPBAN issued by Leader").queue();
		Tools.addLineToFile("data/tempbans.txt", String.format("%s - %s with ID [%s] - %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd kk:mm:ss")),user.getName(), user.getId(), reason));
		timer.schedule(new unBanTask(user), time);
	}
	/**
	 * @param time The date at which to lift the ban.
	 */
	public void tempban(User user, String reason, Date time, int daysToDelete) {
		if (BotControl.usage.isImmune(user)) return;
		if (BotControl.usage.isAdmin(user)) return;
		guild.getController().ban(user, daysToDelete, "TEMPBAN issued by Leader").queue();
		Tools.addLineToFile("data/tempbans.txt", String.format("%s - %s with ID [%s] - %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd kk:mm:ss")),user.getName(), user.getId(), reason));
		timer.schedule(new unBanTask(user), time);
	}
	private static class unBanTask extends TimerTask {

		private final User user;
		private boolean hasRun = false;
		
		public boolean hasRun() {return hasRun;}
		
		public User getUser() {
			return this.user;
		}
		
		public unBanTask(User user) {
			this.user = user;
			scheduledTasks.add(this);
		}
		
		@Override
		public void run() {
			hasRun = true;
			guild = BotControl.getZeus().getJDA().getGuildById("319896741233033216");
			Invite invite = guild.getPublicChannel().createInvite().setMaxUses(1).complete();
			guild.getController().unban(this.user).queue();
			Tools.addLineToFile("data/kicks.txt", String.format("%s - %s with ID [%s] - %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd kk:mm:ss")),user.getName(), user.getId(), "Unban event"));
			user.openPrivateChannel().complete().sendMessage("Your tempban has been lifted. Here is your invite: discord.gg/" + invite.getCode()).queue();
		}
		
	}
	
}
