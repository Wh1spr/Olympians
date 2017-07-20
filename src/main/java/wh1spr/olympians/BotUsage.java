package wh1spr.olympians;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

public class BotUsage {

	public BotUsage() {
		//read immunities,
		Tools.getLinesFromFile(PATH_IMMUNE).forEach(e->immune.add(e));
		//read admins
		Tools.getLinesFromFile(PATH_ADMIN).forEach(e->admins.add(e));
		//read denied list
		Tools.getLinesFromFile(PATH_DENIED).forEach(e->denied.add(e));
	}
	
	private final String PATH_ADMIN = "data/admins.txt";
	private final String PATH_IMMUNE = "data/immune.txt";
	private final String PATH_DENIED = "data/denied.txt";
	
	private final Set<String> admins = new HashSet<String>();
	private final Set<String> denied = new HashSet<String>();
	private final Set<String> immune = new HashSet<String>();
	
	public boolean isDenied(User user) {
		if (user.isBot()) return false;
		if (denied.contains(user.getId())) return true;
		return false;
	}
	
	public boolean isAdmin(User user) { // for bot control
		if (user.isBot()) return true;
		if (admins.contains(user.getId())) return true;
		return false;
	}
	
	public boolean isStaff(User user, Guild guild) {
		if (user.isBot()) return true;
		if (isDenied(user)) return false;
		if (guild.getMember(user).getRoles().contains(guild.getRolesByName("staff", true))) return true;
		if (isAdmin(user)) return true;
		return false;
	}
	
	public boolean isImmune(User user) {
		if (immune.contains(user.getId())) return true;
		if (user.isBot()) return true;
		return false;
	}
	
	public void makeAdmin(User user) {
		if (user == null) return;
		Tools.addLineToFile(PATH_ADMIN, user.getId());
		admins.add(user.getId());
	}
	
	public void makeAdmin(List<User> users) {
		for (User user:users) {
			makeAdmin(user);
		}
	}
	
	public void revokeAdmin(User user) {
		if (user == null) return;
		if (isImmune(user)) return;
		Tools.removeLineFromFile(PATH_ADMIN, user.getId());
		admins.remove(user.getId());
	}
	
	public void revokeAdmin(List<User> users) {
		for (User user : users) {
			revokeAdmin(user);
		}
	}
	
	public void deny(User user) {
		if (user == null) return;
		if (isImmune(user)) return;
		revokeAdmin(user);
		Tools.addLineToFile(PATH_DENIED, user.getId());
		denied.add(user.getId());
	}
	
	public void deny(List<User> users) {
		for (User user:users) {
			deny(user);
		}
	}
	
	public void allow(User user) {
		if (user == null) return;
		Tools.removeLineFromFile(PATH_DENIED, user.getId());
		denied.remove(user.getId());
	}
	
	public void allow(List<User> users) {
		for (User user : users) {
			allow(user);
		}
	}
	
	public void makeImmune(User user) {
		if (user == null) return;
		Tools.addLineToFile(PATH_IMMUNE, user.getId());
		immune.add(user.getId());
	}
	
	public void revokeImmune(User user) {
		if (user == null) return;
		if (user.getId().equals("204529799912226816")) return;
		Tools.removeLineFromFile(PATH_IMMUNE, user.getId());
	}
	
}
