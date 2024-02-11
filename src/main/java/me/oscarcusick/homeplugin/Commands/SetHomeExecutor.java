package me.oscarcusick.homeplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class SetHomeExecutor implements CommandExecutor {
    Plugin PluginInstance;
    public SetHomeExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override // Set home works by putting player homes in their PDC or NBT tag
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player User = (Player)commandSender;

            // giving them the index PDC, this stores the namespace key for all other homes if they don't have it
            if (!User.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "HomeIndex"))) {
                User.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING, "");
            }

            // does a home already exist under that name?
            if (User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING).contains(strings[0].toLowerCase())) {
                // message user
                User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "A Home Already Exists Named " + ChatColor.LIGHT_PURPLE + "\"" + ChatColor.GREEN + strings[0].toLowerCase() + ChatColor.LIGHT_PURPLE + "\"");
                return false;
            }
            // after that all checks are clear, append the new home name onto the index
            String NewIndex = User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING);
            NewIndex = NewIndex.concat((strings[0].toLowerCase() + ","));
            User.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING, NewIndex); // set the new updated index

            // get users coords
            long UserCoords[] = {(long)User.getLocation().getX(), (long)User.getLocation().getY(), (long)User.getLocation().getZ()};

            // create a new namespaced key under the same name with the coords stored as a long array NOTE: THIS DOES NOT STORE WORLD INFORMATION, USE SEPARATE PDC FOR THIS
            User.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, strings[0].toLowerCase()), PersistentDataType.LONG_ARRAY, UserCoords);

            // create a new namespaced key under the same name with the world the name is {HomeName}+"_world". THIS STORED THE WORLD OF THE HOME
            User.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, (strings[0].toLowerCase() + "_world")), PersistentDataType.STRING, User.getWorld().getName());

            // message user
            User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "New Home Called " + ChatColor.LIGHT_PURPLE + "\"" + ChatColor.GREEN + strings[0].toLowerCase() + ChatColor.LIGHT_PURPLE + "\"");


        } // end of command logic
        return false;
    }
}
