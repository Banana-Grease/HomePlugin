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

import java.util.ArrayList;

public class ResetCommandExecutor implements CommandExecutor {
    Plugin PluginInstance;
    public ResetCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player User = (Player)commandSender;

            // get all homes out of HomeIndex to delete all PDCs
            String HomeIndex = User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING);
            ArrayList<String> HomeList= new ArrayList<>();
            String HomeListArr[] = HomeIndex.split(",");
            for (int i  = 0; i < HomeListArr.length; i++) {
                HomeList.add(HomeListArr[i]);
            }

            // use these homes to delete all other PDCs in a loop
            for (int i = 0; i < HomeList.size(); i++) {
                User.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, HomeList.get(i)));
                // remove the world name PDC aswell
                User.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, (HomeList.get(i) + "_world")));
            }

            // home index last becuase it needs to be refered to
            User.getPersistentDataContainer().remove(new NamespacedKey(PluginInstance, "HomeIndex"));

            // message user
            User.sendMessage("" + ChatColor.LIGHT_PURPLE + "[" + ChatColor.GREEN + "HomePlugin" + ChatColor.LIGHT_PURPLE + "]" + ChatColor.GREEN + " - " + ChatColor.AQUA + "Reset All PDCs");


            return true;
        }
        return false;
    }
}
