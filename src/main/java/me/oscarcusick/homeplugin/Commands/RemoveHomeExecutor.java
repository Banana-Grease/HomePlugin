package me.oscarcusick.homeplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.bukkit.plugin.Plugin;

public class RemoveHomeExecutor implements CommandExecutor {
    Plugin PluginInstance;

    public RemoveHomeExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player User = (Player) commandSender;

            // remove home PDCs
            if (User.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, strings[0].toLowerCase()))) { // if they have the home they asked to delete
                // remove the coordinate PDC && World name PDC
                User.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, strings[0].toLowerCase()));
                User.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, (strings[0] + "_world")));

                // update the index
                String NewIndex = User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING);
                NewIndex = NewIndex.replace((strings[0].toLowerCase() + ","), "");
                User.getPersistentDataContainer().set(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING, NewIndex); // set the new updated index

                // message user
                User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "Removed Home Called " + ChatColor.LIGHT_PURPLE + "\"" + ChatColor.GREEN + strings[0].toLowerCase() + ChatColor.LIGHT_PURPLE + "\"");

                return true;
            }

            // message user
            User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "No Home Named " + ChatColor.LIGHT_PURPLE + "\"" + ChatColor.GREEN + strings[0].toLowerCase() + ChatColor.LIGHT_PURPLE + "\"");

            return false;
        }
        return false;
    }
}
