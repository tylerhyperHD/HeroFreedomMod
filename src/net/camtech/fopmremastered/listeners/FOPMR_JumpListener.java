package net.camtech.fopmremastered.listeners;

import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FOPMR_JumpListener implements Listener
{
    public FOPMR_JumpListener()
    {
        Bukkit.getPluginManager().registerEvents(this, FreedomOpModRemastered.plugin);
    }

    @EventHandler
    public void onPlayerJump(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if(!player.isFlying())
        {
            if(event.getTo().getY() > event.getFrom().getY())
            {
                if(FOPMR_Configs.getMainConfig().getConfig().getBoolean("jumppads.enabled"))
                {
                    Location loc = event.getFrom();
                    final Block block = event.getTo().getBlock();
                    if(block.getRelative(0, -1, 0).getType() == Material.WOOL)
                    {
                        player.setVelocity(player.getVelocity().setY(FOPMR_Configs.getMainConfig().getConfig().getDouble("jumppads.strength")));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if(event.getPlayer().isSprinting())
        {
            if(FOPMR_Configs.getMainConfig().getConfig().getBoolean("jumppads.speed"))
            {
                Location loc = event.getPlayer().getLocation();
                Block one = new Location(loc.getWorld(), loc.getX(), loc.getBlockY() - 1, loc.getZ()).getBlock();
                Block two = new Location(loc.getWorld(), loc.getX(), loc.getBlockY() - 2, loc.getZ()).getBlock();
                if(one.getType() == Material.WOOL && two.getType() == Material.PISTON_BASE && two.getData() == 0)
                {
                    event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(FOPMR_Configs.getMainConfig().getConfig().getDouble("jumppads.speedstrength")).setY(0));
                }
            }
        }
    }
}
