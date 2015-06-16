package net.camtech.fopmremastered.commands;

import net.camtech.fopmremastered.FOPMR_Rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FOPMR_BlankCommand extends FOPMR_Command
{
    Class clazz;
    Object object;

    public FOPMR_BlankCommand(String name, String usage, String description, List<String> aliases, Rank rank, Class clazz) throws NoSuchMethodException
    {
        super(name, usage, description, aliases, rank);
        this.clazz = clazz;
        try
        {
            this.object = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(FOPMR_BlankCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        try
        {
            return (boolean) clazz.getMethod("onCommand", CommandSender.class, Command.class, String.class, String[].class).invoke(object, sender, cmd, label, args);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            Logger.getLogger(FOPMR_BlankCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
