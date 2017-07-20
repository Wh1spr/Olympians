package wh1spr.olympians.dionysus;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.template.Bot;

public class Dionysus implements Bot{

	private JDA jda = null;
	private final String TOKEN = "[REDACTED]";

	@Override
	public JDA run() {
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(TOKEN).addEventListener()
			        .buildBlocking();
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
