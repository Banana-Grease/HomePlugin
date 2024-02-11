package me.oscarcusick.homeplugin.Commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeCommandTabCompleter implements TabCompleter {
    Plugin PluginInstance;
    public HomeCommandTabCompleter(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player User = (Player)commandSender;

        // get home index and split it up into HomeList
        String HomeIndex = "";
        if (User.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, "HomeIndex"))) {
            HomeIndex = User.getPersistentDataContainer().get(new NamespacedKey(PluginInstance, "HomeIndex"), PersistentDataType.STRING);
        }
        ArrayList<String> HomeList = new ArrayList<>();
        if (!HomeIndex.isEmpty()) {
            String HomeListArr[] = HomeIndex.split(",");
            for (int i = 0; i < HomeListArr.length; i++) {
                HomeList.add(HomeListArr[i]);
            }
        } else {
            HomeList.add("");
        }
        return HomeList;
    }
}
