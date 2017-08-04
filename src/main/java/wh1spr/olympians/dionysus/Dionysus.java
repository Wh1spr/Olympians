package wh1spr.olympians.dionysus;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.CommandHandler;
import wh1spr.olympians.command.CommandRegistry;
import wh1spr.olympians.dionysus.commands.*;
import wh1spr.olympians.template.Bot;

public class Dionysus implements Bot{

	private JDA jda = null;

	public static final CommandRegistry registry = new CommandRegistry();
	
	@Override
	public JDA run() {
		
		//registry.registerCommand("", null);
		
		registry.registerCommand("ayy", new AyyLmaoCommand());
		registry.registerCommand("gitgud", new GitGudCommand());
		
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(BotControl.DIONYSUS_TOKEN).addEventListener(new CommandHandler(".", registry))
			        .buildBlocking();
			BotControl.addBot(jda);
		} catch (Exception e) {
			System.out.println("[DIONYSUS] ERROR: Dionysus could not be initialized. " + e.getClass().getSimpleName());
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
