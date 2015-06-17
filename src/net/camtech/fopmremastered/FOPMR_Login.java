package net.camtech.fopmremastered;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import net.camtech.fopmremastered.camutils.CUtils_Config;
import net.camtech.fopmremastered.camutils.CUtils_Methods;
import static net.camtech.fopmremastered.listeners.FOPMR_PlayerListener.MAX_XY_COORD;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FOPMR_Login {
    public static final List<String> permbannedNames = Arrays.asList(
            "Jellow_",
            "KingSquads",
            "ButterWarrior146",
            "FGL_Karma",
            "Mahl_Trollbait",
            "TheRealTrioligy"
    );
    public static final List<String> permbannedIps = Arrays.asList(
            "70.162.*.*",
            "66.168.*.*",
            "86.100.*.*",
            "120.29.*.*",
            "50.35.*.*"
    );
    public static final List<String> permbannedUuids = Arrays.asList(
            "42711b44-eccc-3115-8a17-4447f5a4febd",
            "31dcd227-9c62-4b6a-98b5-47ee6761ef74",
            "69a38028-1573-494e-b2fb-dca156233649",
            "18d313cb-c5cc-43c7-8838-8a32cce9c987",
            "1033096e-3c1e-494d-bc6b-84d2d5f64c82",
            "e16a08a9-3c66-4979-bb91-387152d30233"
    );
    
    public static final List<String> minechatIps = Arrays.asList(
        "167.114.97.16"
    );
    
    private static final String IP_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    public static void handleJoinEvent(PlayerJoinEvent event)
    {
        CUtils_Config adminConfig = FOPMR_Configs.getAdmins();
        FileConfiguration config = adminConfig.getConfig();
        FileConfiguration configs = FOPMR_Configs.getMainConfig().getConfig();
        final Player player = event.getPlayer();
        String pId = FOPMR_ConfigurationUtility.getMyUuid(player);
        String getme = FOPMR_PlayerUtility.getAddress(player);
        FOPMR_Rank.Rank level = FOPMR_PlayerUtility.getMyLevel();

        if (Math.abs(player.getLocation().getX()) >= MAX_XY_COORD || Math.abs(player.getLocation().getZ()) >= MAX_XY_COORD) {
            player.teleport(player.getWorld().getSpawnLocation());
        }
        
        for (String UUID : config.getKeys(false)) {
            if ((config.getString(UUID + ".lastName").equals(FOPMR_PlayerUtility.getName(player)))
                    && !(config.getString(UUID + ".lastIp").equals(getme))
                    && !FOPMR_Rank.getRank(player).equals(FOPMR_Rank.Rank.OP)) {
                FOPMR_Commons.imposters.add(FOPMR_PlayerUtility.getName(player));
                config.set(UUID + ".imposter", true);
            }
        }
        if (config.contains(pId)) {
            if (!(config.getString(pId) + ".lastName").equals(FOPMR_PlayerUtility.getName(player))) {
                config.set(pId + ".lastName", FOPMR_PlayerUtility.getName(player));
            }
            if (!(config.getString(pId) + ".lastIp").equals(getme) && FOPMR_Rank.getRank(player) == FOPMR_Rank.Rank.OP) {
                config.set(pId + ".lastIp", getme);
            }
            else if (player.getName().equals("tylerhyperHD"))
            {
                if (FOPMR_Rank.isImposter(player))
                {
                    Bukkit.broadcastMessage(ChatColor.AQUA + "" + "Tyler Hyper" + " is an Imposter");
                }
                else {
                    Bukkit.broadcastMessage(ChatColor.AQUA + "" + "Tyler Hyper" + " is the " + ChatColor.BLUE + "HFM-Creator");
                }
            }
            else if (!"default".equals(config.getString(pId + ".login"))) {
                Bukkit.broadcastMessage(ChatColor.AQUA + FOPMR_PlayerUtility.getName(player) + " " + CUtils_Methods.colour(config.getString(pId + ".login")));
            }
            else if (FOPMR_Rank.getRank(player) != FOPMR_Rank.Rank.OP) {
                Bukkit.broadcastMessage(ChatColor.AQUA + FOPMR_PlayerUtility.getName(player) + " is " + CUtils_Methods.aOrAn(FOPMR_Rank.getRank(player).name) + " " + FOPMR_Rank.getRank(player).name);
            }
        }
        else {
            player.sendMessage(ChatColor.GREEN + "Hey there! Welcome to HeroFreedom!");
            config.set(pId + ".lastName", FOPMR_PlayerUtility.getName(player));
            config.set(pId + ".lastIp", getme);
            config.set(pId + ".chat", "");
            config.set(pId + ".rank", "Op");
            config.set(pId + ".login", "default");
            config.set(pId + ".votes", 0);
            config.set(pId + ".imposter", false);
            config.set(pId + ".chatLevel", 0);
            if (player.getName().equals("DFCrafted")) {
                config.set(pId + ".displayName", "DF_Crafted");
            }
            if (player.getName().equals("tylerhyperHD"))
            {
                config.set(pId + ".displayName", "Tyler Hyper");
            }
            else {
            config.set(pId + ".displayName", FOPMR_PlayerUtility.getName(player));
            }
            config.set(pId + ".tag", "default");
            config.set(pId + ".builder", false);
            config.set(pId + ".banHammer", false);
            config.set(pId + ".cmdblock", false);
            config.set(pId + ".djump", false);
            config.set(pId + ".muted", false);
            config.set(pId + ".randomChatColour", false);
            config.set(pId + ".chatColours", false);
            config.set(pId + ".lastLogin", System.currentTimeMillis());
        }
        config.set(pId + ".chatColours", true);
        config.set(pId + ".randomChatColour", true);
        adminConfig.saveConfig();

        if (FOPMR_PlayerUtility.getMainLevel() > 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.RED + "HeroFreedom is currently locked down to clearance level " + configs.getInt("general.accessLevel") + " (" + level.name + ").");
                }
            }.runTaskLater(FreedomOpModRemastered.plugin, 20L * 5L);
        }
        player.sendMessage(CUtils_Methods.colour(configs.getString("general.joinMessage").replaceAll("%player%", FOPMR_PlayerUtility.getName(player))));
        config.set(pId + ".lastLogin", System.currentTimeMillis());
        FOPMR_PermissionsManager.removeMoreProtectPermissions(player);
        if(!FOPMR_Rank.isSystem(player)) {
            FOPMR_PermissionsManager.removePermission(player, "icu.control");
            FOPMR_PermissionsManager.removePermission(player, "icu.stop");
        }
        if (!player.getName().equals("tylerhyperHD") && !player.getName().equals("_herobrian35_")) {
            FOPMR_PermissionsManager.removePermission(player, "icu.exempt");
        }
        if(!FOPMR_Rank.isAdmin(player)) {
            FOPMR_PermissionsManager.removePermission(player, "worldedit.limit.unrestricted");
            FOPMR_PermissionsManager.removePermission(player, "worldedit.anyblock");
            FOPMR_PermissionsManager.removePermission(player, "worldedit.history.clear");
            FOPMR_PermissionsManager.removePermission(player, "worldedit.snapshot.restore");
            FOPMR_PermissionsManager.removePermission(player, "worldedit.limit");
        }
        FOPMR_Log.info(FOPMR_PlayerUtility.getName(player) + "'s join events ran successfully.");
    }
    
    public static void handleLogin(PlayerLoginEvent event)
    {
            UUID uuid;
            Player player = event.getPlayer();
            String username = player.getName();
            FileConfiguration mainconfig = FOPMR_Configs.getMainConfig().getConfig();
            FileConfiguration adminconfig = FOPMR_Configs.getAdmins().getConfig();
            String ip = event.getAddress().getHostAddress().trim();
            String pId = FOPMR_ConfigurationUtility.getMyUuid(player);
            String uuidhardcodebyebye = "Your UUID has been hardcoded to a permban list. You may not come back to HeroFreedom ever again.";
            String iphardcodebyebye = "Your ip has been hardcoded to a permban list. You may not come back to HeroFreedom ever again.";
            String hardcodebyebye = "Your username has been hardcoded to a permban list. You may not come back to HeroFreedom ever again.";
            
        for(Player player2 : Bukkit.getOnlinePlayers()) {
            if((player.getName() == null ? player2.getName() == null : player.getName().equals(player2.getName())) && FOPMR_Rank.isAdmin(player2)) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "An admin is already logged in with that name.");
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            }
        }
        boolean hasNonAlpha = player.getName().matches("^.*[^a-zA-Z0-9_].*$");
        if(hasNonAlpha) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Your name contains invalid characters, please login using a fully alphanumeric name.");
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
            if(FOPMR_Rank.getRank(player).level < mainconfig.getInt("general.accessLevel")) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "The server is currently locked down to clearance level " + mainconfig.getInt("general.accessLevel") + ".");
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                return;
            }
            if(FOPMR_Rank.isAdmin(player) && !adminconfig.getBoolean(pId + ".imposter") && (adminconfig.getString(pId + ".lastIp").equals(event.getAddress().getHostAddress()))) {
                event.allow();
                return;
            }
            
            if(FOPMR_Bans.isBanned(FOPMR_PlayerUtility.getName(player), event.getAddress().getHostAddress())) {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, FOPMR_Bans.getReason(FOPMR_PlayerUtility.getName(player)) + "(You may appeal the ban at our forums accessible from " + mainconfig.getString("general.url") + ")");
                event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            }
            
            // Removes automatic trolling
            if (FOPMR_PlayerUtility.getName(player).equalsIgnoreCase("ZexyZek"))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You may not troll on this server. Use a different name and do not troll.");
                return;             
            }
            if (FOPMR_PlayerUtility.getName(player).equalsIgnoreCase("null"))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You may not login with a username of null. Come back with a different one.");
                return;
            }
            // Removes venom from ever coming back to the server
            if (FOPMR_PlayerUtility.getName(player).equalsIgnoreCase("Venom_nV"))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Venom_nV, go away and don't ever come back to this server.");
                return;
            }
            
            if (player.getUniqueId().equals("9ca58c67-f77b-45c7-984f-9bf6ca8a8941"))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Venom_nV, go away and don't ever come back to this server.");
                return;
            }
            
            for(String testPlayer : FOPMR_Login.permbannedNames)
            {
                if(testPlayer.equalsIgnoreCase(username))
                {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + uuidhardcodebyebye);
                    return;
                }
            }
            
            // UUID ban
            if (player.getUniqueId().equals(permbannedUuids.toString()))
            {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + hardcodebyebye);
                return;
            }
            
            // Get rid of people using minechat
            for (String testIp : FOPMR_Login.minechatIps)
            {
                if (FOPMR_Login.fuzzyIpMatch(testIp, ip, 4))
                {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Minechat is not allowed on this server.\nPlease login using Minecraft for PC/Mac/Linux.");
                    return;
                }
            }
            
            for (String testIp : FOPMR_Login.permbannedIps)
            {
                if (FOPMR_Login.fuzzyIpMatch(testIp, ip, 4))
                {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + iphardcodebyebye);
                    return;
                }
            }
            
            player.setOp(true);
            player.setGameMode(GameMode.CREATIVE);
    }
    
    public static boolean fuzzyIpMatch(String ipA, String ipB, int octets) {
        boolean match = true;

        String[] ippartsA = ipA.split("\\.");
        String[] ipPartsB = ipB.split("\\.");

        if (ippartsA.length != 4 || ipPartsB.length != 4) {
            return false;
        }

        if (octets > 4) {
            octets = 4;
        } else if (octets < 1) {
            octets = 1;
        }

        for (int i = 0; i < octets && i < 4; i++) {
            if (ippartsA[i].equals("*") || ipPartsB[i].equals("*")) {
                continue;
            }

            if (!ippartsA[i].equals(ipPartsB[i])) {
                match = false;
                break;
            }
        }

        return match;
    }
        
    public static int getPort(Player player) {
        return player.getAddress().getPort();
    }
    
    public static String getIp(Player player) {
        return player.getAddress().getAddress().getHostAddress().trim();
    }

    public static String getIp(PlayerLoginEvent event) {
        return event.getAddress().getHostAddress().trim();
    }
}
