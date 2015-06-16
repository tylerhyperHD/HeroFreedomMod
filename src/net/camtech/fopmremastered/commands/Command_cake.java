package net.camtech.fopmremastered.commands;

import java.util.Arrays;
import net.camtech.fopmremastered.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Command_cake extends FOPMR_Command
{
    public Command_cake()
    {
        super("cake", "/cake", "ITS A FRIGGIN LIE!", Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Bukkit.broadcastMessage(CUtils_Methods.randomChatColour() + "But there's no sense crying over every mistake. You just keep on trying till you run out of " + CUtils_Methods.randomChatColour() + "cake.");   
        for (Player player : Bukkit.getOnlinePlayers())
        {
            ItemStack cookie = new ItemStack(Material.CAKE, 1);
            ItemMeta cookiemeta = cookie.getItemMeta();
            cookiemeta.setLore(Arrays.asList(CUtils_Methods.randomChatColour() + "A FRIGGIN LIE TO YOU", CUtils_Methods.randomChatColour() + " - " + sender.getName()));
            cookiemeta.setDisplayName((new StringBuilder()).append(ChatColor.WHITE).append("The ").append(ChatColor.DARK_GRAY).append("Lie").toString());
            cookie.setItemMeta(cookiemeta);
            player.getInventory().addItem(cookie);
        }
        return true;
    }

}