package wh1spr.olympians;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.dv8tion.jda.core.JDA;
import wh1spr.olympians.apollo.Apollo;
import wh1spr.olympians.athena.Athena;
import wh1spr.olympians.dionysus.Dionysus;
import wh1spr.olympians.template.Bot;
import wh1spr.olympians.zeus.Zeus;

public class BotControl {
	
	public static final String APOLLO_TOKEN = "[REDACTED]";
	public static final String DIONYSUS_TOKEN = "[REDACTED]";
	public static final String ATHENA_TOKEN = "[REDACTED]";
	public static final String ZEUS_TOKEN = "[REDACTED]";

	//All the bots
	private static Bot apollo = new Apollo();
	private static Bot dionysus = new Dionysus();
	private static Bot athena = new Athena();
	private static Bot zeus = new Zeus();
	
	public static Bot getApollo() { return apollo;}
	public static Bot getAthena() { return athena;}
	public static Bot getDionysus() { return dionysus;}
	public static Bot getZeus() { return zeus;}
	
	public static BotUsage usage = new BotUsage();
	public static UserControl userControl;
	
	public static void main(String[] args) {
		zeus.run();
		apollo.run();
		dionysus.run();
		athena.run();
		userControl = new UserControl();
	}
	
	private static Set<JDA> bots = new HashSet<JDA>();
	public static void removeBot(JDA toRemove) {
		BotControl.bots.remove(toRemove);
	}
	public static void addBot(JDA toAdd) {
		BotControl.bots.add(toAdd);
	}
	public static int nrOfBots() {
		return BotControl.bots.size();
	}

	//I know that this isn't actually good but it's here now
	public static void shutdown() {
		BotControl.userControl.shutdown();
		BotControl.apollo.shutdown();
		BotControl.dionysus.shutdown();
		BotControl.athena.shutdown();
		BotControl.zeus.shutdown();
	}
	
	public static void shutdownWithExit() {
		shutdown();
		System.exit(0);
	}
	
	public static void update() {
		shutdown();
		ProcessBuilder pb = new ProcessBuilder("./update.sh > update.txt");
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
