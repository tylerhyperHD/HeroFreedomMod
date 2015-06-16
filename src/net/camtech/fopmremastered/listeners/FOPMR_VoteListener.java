package net.camtech.fopmremastered.listeners;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import net.camtech.fopmremastered.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Configs;
import net.camtech.fopmremastered.FreedomOpModRemastered;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FOPMR_VoteListener implements Listener
{
    public FOPMR_VoteListener()
    {
        Bukkit.getPluginManager().registerEvents(this, FreedomOpModRemastered.plugin);
    }
    
    @EventHandler
    public void voteShopClicked(InventoryClickEvent event)
    {
        if(event.getInventory().getName().equals(ChatColor.GREEN + "" +  ChatColor.BOLD + "$$ " + ChatColor.GOLD + "VoteShop " + ChatColor.GREEN + "" + ChatColor.BOLD + "$$"))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            FileConfiguration config = FOPMR_Configs.getAdmins().getConfig();
            String entry = ((Player) event.getWhoClicked()).getUniqueId().toString();
            int votes = config.getInt(entry + ".votes");
            switch(item.getType())
            {
                case SIGN:
                    if(votes >= 3 && !config.getBoolean(entry + ".randomChatColour"))
                    {
                        config.set(entry + ".votes", votes -= 3);
                        config.set(entry + ".randomChatColour", true);
                    }
                    break;
                case PAPER:
                    if(votes >= 3 && !config.getBoolean(entry + ".chatColours"))
                    {
                        config.set(entry + ".votes", votes -= 3);
                        config.set(entry + ".chatColours", true);
                    }
                break;
                default:
                    break;
            }
            FOPMR_Configs.getAdmins().saveConfig();
        }

    }
    
    @EventHandler
    public void voteMade(VotifierEvent event)
    {
        Vote vote = event.getVote();
        Bukkit.broadcastMessage(CUtils_Methods.colour(CUtils_Methods.randomChatColour() + vote.getUsername() + " &-has just voted from " + CUtils_Methods.randomChatColour() + vote.getServiceName() + "&- and have received a vote to spend in the VoteShop (/vs)!"));
        Player player = Bukkit.getPlayer(vote.getUsername());
        int votes = 0;
        if(player != null)
        {
            votes = FOPMR_Configs.getAdmins().getConfig().getInt(player.getUniqueId().toString() + ".votes");
            votes++;
            FOPMR_Configs.getAdmins().getConfig().set(player.getUniqueId().toString() + ".votes", votes);
            player.sendMessage(CUtils_Methods.colour("&-Thank you for voting! You now have " + CUtils_Methods.randomChatColour() + votes + " &-votes to spend in the VoteShop!"));
        }
        else
        {
            OfflinePlayer offplayer = Bukkit.getOfflinePlayer(vote.getUsername());
            votes = FOPMR_Configs.getAdmins().getConfig().getInt(offplayer.getUniqueId().toString() + ".votes");
            FOPMR_Configs.getAdmins().getConfig().set(offplayer.getUniqueId().toString() + ".votes", votes++);
        }
        FOPMR_Configs.getAdmins().saveConfig();
    }
    
}
