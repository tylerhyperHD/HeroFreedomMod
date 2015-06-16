package net.camtech.fopmremastered.camutils;

import java.lang.reflect.Field;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class CUtils_Command implements CommandExecutor, TabExecutor
{

    protected final String command;
    protected final String description;
    protected final List<String> alias;
    protected final String usage;
    protected final String permMessage;

    protected static CommandMap cmap;

    public CUtils_Command(String command)
    {
        this(command, null, null, null, null);
    }

    public CUtils_Command(String command, String usage)
    {
        this(command, usage, null, null, null);
    }

    public CUtils_Command(String command, String usage, String description)
    {
        this(command, usage, description, null, null);
    }

    public CUtils_Command(String command, String usage, String description, String permissionMessage)
    {
        this(command, usage, description, permissionMessage, null);
    }

    public CUtils_Command(String command, String usage, String description, List<String> aliases)
    {
        this(command, usage, description, null, aliases);
    }

    public CUtils_Command(String command, String usage, String description, String permissionMessage, List<String> aliases)
    {
        this.command = command.toLowerCase();
        this.usage = usage;
        this.description = description;
        this.permMessage = permissionMessage;
        this.alias = aliases;
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
        getCommandMap().register("", cmd);
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
            }
            catch (Exception e)
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

    private final class ReflectCommand extends Command
    {

        private CUtils_Command exe = null;

        protected ReflectCommand(String command)
        {
            super(command);
        }

        public void setExecutor(CUtils_Command exe)
        {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args)
        {
            if (exe != null)
            {
                if(!exe.onCommand(sender, this, commandLabel, args))
                {
                    sender.sendMessage(this.usageMessage);
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alais, String[] args)
        {
            if (exe != null)
            {
                return exe.onTabComplete(sender, this, alais, args);
            }
            return null;
        }
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
    {
        return null;
    }
}
