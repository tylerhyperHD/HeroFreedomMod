package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.camutils.CUtils_Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandParameters(name="admininfo", description="Find how to become admin.", usage="/admininfo",aliases="ai")
public class Command_admininfo
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
    {
        sender.sendMessage(CUtils_Methods.randomChatColour() + "In order to apply for admin you need to go to the forums @ http://herofreedommc.boards.net/");
        return true;
    }
}
