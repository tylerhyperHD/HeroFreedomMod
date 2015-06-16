package net.camtech.fopmremastered.commands;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import net.camtech.fopmremastered.camutils.CUtils_Methods;
import net.camtech.fopmremastered.FOPMR_Rank;
import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import static net.camtech.fopmremastered.FreedomOpModRemastered.plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabExecutor;

public abstract class FOPMR_Command implements CommandExecutor, TabExecutor
{

    protected static CommandMap cmap;
    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permMessage;
    protected final Rank rank;

    public FOPMR_Command(String command)
    {
        this(command, null, null, null, null, Rank.OP);
    }

    public FOPMR_Command(String command, String usage)
    {
        this(command, usage, null, null, null, Rank.OP);
    }

    public FOPMR_Command(String command, String usage, String description)
    {
        this(command, usage, description, null, null, Rank.OP);
    }

    public FOPMR_Command(String command, String usage, String description, String permissionMessage)
    {
        this(command, usage, description, permissionMessage, null, Rank.OP);
    }

    public FOPMR_Command(String command, String usage, String description, List<String> aliases)
    {
        this(command, usage, description, null, aliases, Rank.OP);
    }

    public FOPMR_Command(String command, String usage, String description, Rank rank)
    {
        this(command, usage, description, null, null, rank);
    }

    public FOPMR_Command(String command, String usage, String description, String permissionMessage, Rank rank)
    {
        this(command, usage, description, permissionMessage, null, rank);
    }

    public FOPMR_Command(String command, String usage, String description, List<String> aliases, Rank rank)
    {
        this(command, usage, description, null, aliases, rank);
    }

    public FOPMR_Command(String command, String usage, String description, String permissionMessage, List<String> aliases, Rank rank)
    {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
        this.rank = rank;
    }

    public void register()
    {
        ReflectCommand cmd = new ReflectCommand(this.command);
        if (this.alias != null)
        {
            cmd.setAliases(this.alias);
        }
        if (this.description != null)
        {
            cmd.setDescription(this.description);
        }
        if (this.usage != null)
        {
            cmd.setUsage(this.usage);
        }
        if (this.permMessage != null)
        {
            cmd.setPermissionMessage(this.permMessage);
        }
        if (!getCommandMap().register("", cmd))
        {
            this.unRegisterBukkitCommand(Bukkit.getPluginCommand(cmd.getName()));
            getCommandMap().register("", cmd);
        }
        cmd.setExecutor(this);
    }

    final CommandMap getCommandMap()
    {
        if (cmap == null)
        {
            try
            {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (cmap != null)
        {
            return cmap;
        }
        return getCommandMap();
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
    {
        return null;
    }

    private Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    private void unRegisterBukkitCommand(PluginCommand cmd)
    {
        try
        {
            Object result = getPrivateField(plugin.getServer().getPluginManager(), "commandMap");
            SimpleCommandMap commandMap = (SimpleCommandMap) result;
            Object map = getPrivateField(commandMap, "knownCommands");
            @SuppressWarnings("unchecked")
            HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
            knownCommands.remove(cmd.getName());
            for (String registeredalias : cmd.getAliases())
            {
                knownCommands.remove(registeredalias);
            }
        } catch (SecurityException | IllegalArgumentException | NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public final class ReflectCommand extends Command
    {

        private FOPMR_Command exe = null;

        protected ReflectCommand(String command)
        {
            super(command);
        }

        public void setExecutor(FOPMR_Command exe)
        {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args)
        {
            if (exe != null)
            {
                if (!FOPMR_Rank.isEqualOrHigher(FOPMR_Rank.getRank(sender), rank))
                {
                    sender.sendMessage(ChatColor.RED + "You must be " + CUtils_Methods.aOrAn(rank.name) + " " + rank.name + " to use this command.");
                    return true;
                }
                if (!exe.onCommand(sender, this, commandLabel, args))
                {
                    sender.sendMessage(this.usageMessage.replaceAll("<command>", command));
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args)
        {
            if (exe != null)
            {
                return exe.onTabComplete(sender, this, alias, args);
            }
            return null;
        }
    }
}
