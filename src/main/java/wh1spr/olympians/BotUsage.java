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
	
	private final String ownerID = "204529799912226816";
	
	public String getOwnerId() {
		return ownerID;
	}
	public User getOwner() {
		return BotControl.getZeus().getJDA().getUserById(ownerID);
	}
	public Set<String> getAdminIds() {
		Set<String> allAdmins = new HashSet<String>();
		allAdmins.addAll(admins);
		allAdmins.add(ownerID);
		return allAdmins;
	}
	public Set<User> getAdmins() {
		Set<User> allAdmins = new HashSet<User>();
		admins.forEach(e->allAdmins.add(BotControl.getZeus().getJDA().getUserById(e)));
		allAdmins.add(getOwner());
		return allAdmins;
	}
	public Set<String> getImmuneIds() {
		Set<String> immunes = new HashSet<String>();
		immunes.addAll(immune);
		return immunes;
	}
	public Set<User> getImmunes() {
		Set<User> immunes = new HashSet<User>();
		getImmuneIds().forEach(e->immunes.add(BotControl.getZeus().getJDA().getUserById(e)));
		return immunes;
	}
	public Set<String> getDeniedIds() {
		Set<String> denieds = new HashSet<String>(denied);
		return denieds;
	}
	public Set<User> getDenied() {
		Set<User> denieds = new HashSet<User>();
		getDeniedIds().forEach(e->denieds.add(BotControl.getZeus().getJDA().getUserById(e)));
		return denieds;
	}
	
	
	public boolean isOwner(User user) {
		return user.getId().equals(ownerID);
	}
	
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
	
//	public boolean isStaff(User user, Guild guild) {
//		if (user.isBot()) return true;
//		if (isDenied(user)) return false;
//		if (guild.getMember(user).getRoles().contains(guild.getRolesByName("staff", true))) return true;
//		if (isAdmin(user)) return true;
//		return false;
//	}
	
	public boolean isImmune(User user) {
		if (immune.contains(user.getId())) return true;
		if (user.isBot()) return true;
		return false;
	}
	
	public void makeAdmin(User user) {
		if (user == null) return;
		if (isDenied(user)) return;
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
		if (isOwner(user)) return;
		Tools.removeLineFromFile(PATH_IMMUNE, user.getId());
	}
	
}
