package wh1spr.olympians.zeus;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.CommandHandler;
import wh1spr.olympians.command.CommandRegistry;
import wh1spr.olympians.template.Bot;
import wh1spr.olympians.zeus.commands.*;


public class Zeus implements Bot{

	private JDA jda = null;

	@Override
	public JDA run() {
		CommandRegistry adminRegistry = new CommandRegistry();
		
		//for each command
//		adminRegistry.registerCommand("", null);
		adminRegistry.registerCommand("shutdown", new ShutdownCommand());
		adminRegistry.registerCommand("start", new StartCommand());
		adminRegistry.registerCommand("changegame", new ChangeGameCommand(), "cg");
		adminRegistry.registerCommand("update", new UpdateCommand(), "u");
		
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(BotControl.ZEUS_TOKEN).addEventListener(new AutoEventHandler(), new UsageMessageHandler(), new UserControlMessageHandler(), new CommandHandler("&&", adminRegistry))
			        .buildBlocking();
			BotControl.addBot(jda);
		} catch (Exception e) {
			System.out.println("[ZEUS] ERROR: Zeus could not be initialized. " + e.getClass().getSimpleName());
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
		} else if (BotControl.nrOfBots() == 1){
			this.jda.shutdown();
			BotControl.removeBot(this.jda);
			this.jda = null;
		} else {
			BotControl.shutdown();
		}
	}

}
