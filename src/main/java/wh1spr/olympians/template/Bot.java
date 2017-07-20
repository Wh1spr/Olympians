package wh1spr.olympians.template;

import net.dv8tion.jda.core.JDA;

public interface Bot {
	
	public JDA run();
	
	public default void shutdown() {
		this.getJDA().shutdown();
	}
	
	public JDA getJDA();
	
	public default boolean isOnline() {
		return this.getJDA() != null;
	}
}
