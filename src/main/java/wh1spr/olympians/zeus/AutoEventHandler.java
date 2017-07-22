package wh1spr.olympians.zeus;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AutoEventHandler extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRoleById("319920322100985858"));
	}
}
