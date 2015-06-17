package net.camtech.fopmremastered.commands;

import java.util.Arrays;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import static net.camtech.fopmremastered.FreedomOpModRemastered.plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Command_blowjob extends FOPMR_Command
{
    public Command_blowjob()
    {
        super("blowjob", "/blowjob [player]", "Give someone a blowjob.", Arrays.asList("blow"), Rank.HFMCREATOR);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length != 1)
        {
            return false;
        }

        final Player player = Bukkit.getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(ChatColor.GRAY + "Player not found.");
            return true;
        }

        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Giving " + player.getName() + " a blowjob.");
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 69, 0));
        Player player2 = (Player) sender;
        player2.teleport(new Location(Bukkit.getWorld("world"), 0, 69, 0));
        player.chat("Ohhhh yeahhhhh.");
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " got fucked too hard, and fell asleep whilst the fuck happened.");
                player.setHealth(0.0);
                if (sender.getName().equals("tylerhyperHD"))
                {
                    player.sendMessage(ChatColor.RED + "You just got a blowjob by a gay guy in Minecraft!");
                }
            }
        }.runTaskLater(plugin, 4L * 40L);
       return true;
    }
}
