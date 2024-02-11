package me.oscarcusick.homeplugin.Commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import javax.print.DocFlavor;

public class HomeCommandExecutor implements CommandExecutor {
    Plugin PluginInstance;
    public HomeCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player && strings.length == 1) { // if the person running the command is a player && strings is only one long
            Player User = (Player)commandSender;

            // if the home index DOESN'T CONTAIN the home specified, return false
            if (!User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING).contains(strings[0].toLowerCase())) {
                // message user
                User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "No Home Named " + ChatColor.LIGHT_PURPLE + "\"" + ChatColor.GREEN + strings[0].toLowerCase() + ChatColor.LIGHT_PURPLE + "\"");
                return false;
            }

            // if the index does contain the home, then we pull the XYZ data from the other PDC
            long CoordData[] = User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, strings[0].toLowerCase()), PersistentDataType.LONG_ARRAY);

            // get the world
            String WorldName = User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, (strings[0].toLowerCase() + "_world")), PersistentDataType.STRING);
            PluginInstance.getServer().createWorld(new WorldCreator(WorldName)); // start the world so the .getWorld call can actually see it
            World TPWorld = PluginInstance.getServer().getWorld(WorldName);
            if (TPWorld == null) { // error check
                User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "Somethig Went Wrong! (TPWorld == NULL) " + ChatColor.LIGHT_PURPLE + "\"" + ChatColor.GREEN + strings[0].toLowerCase() + ChatColor.LIGHT_PURPLE + "\"");
                return false;
            }

            // load the target world IF it's not the current world
            if (User.getWorld() != TPWorld) {
                PluginInstance.getServer().createWorld(new WorldCreator(TPWorld.getName()));
            }

            // Teleport them to their location, assuming its in the same world, CHANGE LATER
            User.teleport(new Location(TPWorld, CoordData[0], CoordData[1], CoordData[2]));

            // message user
            User.sendTitle(("" + ChatColor.GREEN + "Teleported!"), ("" + ChatColor.AQUA + "Arrived to " + ChatColor.LIGHT_PURPLE + strings[0].toLowerCase()), 10, 90, 10);

            return true;
        } // end of command logic
        return false;
    }
}
