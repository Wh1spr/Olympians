package wh1spr.olympians.zeus;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AutoEventHandler extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		if (event.getGuild().getId().equals("319896741233033216")) event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRoleById("319920322100985858"));
	}
}
