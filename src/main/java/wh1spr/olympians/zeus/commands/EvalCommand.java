package wh1spr.olympians.zeus.commands;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.BotControl;
import wh1spr.olympians.command.Command;

public class EvalCommand extends Command {

	@Override
	public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args) {
		ScriptEngine se = new ScriptEngineManager().getEngineByName("nashorn");
        se.put("jda", jda);
        se.put("guild", guild);
        se.put("channel", channel);
        se.put("zeus", BotControl.getZeus());
        se.put("apollo", BotControl.getApollo());
        se.put("dionysus", BotControl.getDionysus());
        se.put("athena", BotControl.getAthena());
        try
        {
            channel.sendMessage("Evaluated Successfully:\n```\n"+se.eval(message.getStrippedContent().split(" ",2)[1])+" ```").queue();
        } 
        catch(Exception e)
        {
            channel.sendMessage("An exception was thrown:\n```\n"+e+" ```").queue();
        }
	}

}
