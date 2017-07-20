package wh1spr.olympians.apollo;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.apollo.music.MusicMessageHandler;
import wh1spr.olympians.template.Bot;

public class Apollo implements Bot{

	private JDA jda = null;

	@Override
	public JDA run() {
		try {
			jda = new JDABuilder(AccountType.BOT)
			        .setToken(BotControl.APOLLO_TOKEN).addEventListener(new MusicMessageHandler())
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
