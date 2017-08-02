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

	public static final CommandRegistry adminRegistry = new CommandRegistry();
	
	@Override
	public JDA run() {
		//for each command
		//adminRegistry.registerCommand("", null);
		
		// BotControl
		adminRegistry.registerCommand("shutdown", new ShutdownCommand());
		adminRegistry.registerCommand("start", new StartCommand());
		adminRegistry.registerCommand("changegame", new ChangeGameCommand(), "cg");
		adminRegistry.registerCommand("update", new UpdateCommand(), "u");
		adminRegistry.registerCommand("disablecmd", new CommandDisableCommand(), "dmcd");
		
		// BotUsage
		adminRegistry.registerCommand("grantadmin", new AdminGrantCommand(), "ga");
		adminRegistry.registerCommand("revokeadmin", new AdminRevokeCommand(), "ra");
		adminRegistry.registerCommand("allow", new AllowCommand());
		adminRegistry.registerCommand("deny", new DenyCommand());
		adminRegistry.registerCommand("grantimmunity", new ImmunityGrantCommand(), "gi");
		adminRegistry.registerCommand("revokeimmunity", new ImmunityRevokeCommand(), "ri");
		
		
		
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(BotControl.ZEUS_TOKEN).addEventListener(new AutoEventHandler(), new CommandHandler("&&", adminRegistry))
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
