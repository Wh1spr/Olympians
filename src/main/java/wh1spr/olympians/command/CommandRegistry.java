package wh1spr.olympians.command;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import wh1spr.olympians.apollo.Apollo;
import wh1spr.olympians.zeus.Zeus;

public  class CommandRegistry {
	
	private HashMap<String, CommandEntry> registry = new HashMap<>();

    public void registerCommand(String name, Command command, String... aliases) {
        CommandEntry entry = new CommandEntry(command, name);
        registry.put(name, entry);
        for (String alias : aliases) {
            registry.put(alias, entry);
        }
    }

    public CommandEntry getCommand(String name) {
        return registry.get(name);
    }

    public int getSize() {
        return registry.size();
    }

    public Set<String> getRegisteredCommandsAndAliases() {
        return registry.keySet();
    }

    public void removeCommand(String name) {
        CommandEntry entry = new CommandEntry(new Command() {
            @Override
            public void onCall(JDA jda, Guild guild, TextChannel channel, Member invoker, Message message, List<String> args ) {
                channel.sendMessage("This command is temporarily disabled");
            }

            @Override
            public String help() {
                return "Temporarily disabled command";
            }
        }, name);

        Zeus.adminRegistry.registry.put(name, entry);
        Apollo.registry.registry.put(name, entry);
        
    }

    public class CommandEntry {

        public Command command;
        public String name;

        CommandEntry(Command command, String name) {
            this.command = command;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCommand(Command command) {
            this.command = command;
        }
    }
	
}

/*
 * MIT License
 *
 * Copyright (c) 2017 Frederik Ar. Mikkelsen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */