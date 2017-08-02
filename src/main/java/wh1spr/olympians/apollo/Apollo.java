package wh1spr.olympians.apollo;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.apollo.music.commands.*;
import wh1spr.olympians.command.CommandHandler;
import wh1spr.olympians.command.CommandRegistry;
import wh1spr.olympians.template.Bot;

public class Apollo implements Bot{

	private JDA jda = null;
	
	public static final CommandRegistry registry = new CommandRegistry();

	@Override
	public JDA run() {
		
//		registry.registerCommand("", );
		//TODO: queue command, for seeing the next X in queue (5 to 10, not sure)
		registry.registerCommand("play", new PlayCommand());
		registry.registerCommand("stop", new StopCommand());
		registry.registerCommand("leave", new LeaveCommand());
		registry.registerCommand("skip", new SkipCommand());
		registry.registerCommand("shuffle", new ShuffleCommand());
		registry.registerCommand("repeat", new RepeatCommand());
		registry.registerCommand("pause", new PauseCommand(), "p");
		registry.registerCommand("now", new NowCommand(), "np", "nowplaying");
		registry.registerCommand("volume", new VolumeCommand(), "vol");
		registry.registerCommand("join", new JoinCommand());
		registry.registerCommand("bansong", new BanSongCommand());
		registry.registerCommand("unbansong", new UnbanSongCommand());
		
		
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(BotControl.APOLLO_TOKEN).addEventListener(new CommandHandler("!", registry))
			        .buildBlocking();
			BotControl.addBot(jda);
		} catch (Exception e) {
			System.out.println("[APOLLO] ERROR: Apollo could not be initialized. " + e.getClass().getSimpleName());
		}
		return jda;
	}

	@Override
	public JDA getJDA() {
		return jda;
	}
	
	@Override
	public void shutdown() {
		if (jda == null) {
			return;
		} else {
			this.jda.shutdown();
			BotControl.removeBot(this.jda);
			this.jda = null;
		}
	}

}
