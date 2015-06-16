package net.camtech.fopmremastered.camutils;

import java.util.Random;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import static net.camtech.fopmremastered.FreedomOpModRemastered.plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CUtils_Listeners implements Listener
{

    static Random random = new Random();

    public CUtils_Listeners()
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event)
    {
        if(FreedomOpModRemastered.exploded.contains(event.getEntity().getName()))
        {
            event.setDeathMessage(event.getEntity().getName() + randomDeathMessage());
            FreedomOpModRemastered.exploded.remove(event.getEntity().getName());
        }
    }

    private static String randomDeathMessage()
    {
        int i = random.nextInt(5);
        if(i == 1)
        {
            return " turned into a spray!";
        }
        if(i == 2)
        {
            return " was splattered across the walls!";
        }
        if(i == 3)
        {
            return " exploded!";
        } else
        {
            return " blew up!";
        }
    }
}
