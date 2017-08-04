package wh1spr.olympians.dionysus.commands;

import java.io.File;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.command.Command;

public class GitGudCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		channel.sendFile(new File("data/images/gitgud.jpg"), new MessageBuilder().append("Git gud scrub.").build()).queue();
	}

}
