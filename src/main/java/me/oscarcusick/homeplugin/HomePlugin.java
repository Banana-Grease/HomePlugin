package me.oscarcusick.homeplugin;

import me.oscarcusick.homeplugin.Commands.*;
import me.oscarcusick.homeplugin.DataStructers.HomeClass;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class HomePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("sethome").setExecutor(new SetHomeExecutor(this)); // set home command
        getCommand("sethome").setTabCompleter(new SetHomeTabCompleter());

        getCommand("home").setExecutor(new HomeCommandExecutor(this)); // home command
        getCommand("home").setTabCompleter(new HomeCommandTabCompleter(this)); // tab completer to show what homes are available

        getCommand("removehome").setExecutor(new RemoveHomeExecutor(this)); // remove home command
        getCommand("removehome").setTabCompleter(new HomeCommandTabCompleter(this)); // tab completer to show what homes are available

        getCommand("reset").setExecutor(new ResetCommandExecutor(this)); // reset PDC command

    }
}
