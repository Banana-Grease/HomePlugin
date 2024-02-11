package me.oscarcusick.homeplugin.DataStructers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HomeClass {
    Location HomeLoc; // where is it, map? x,y,z?
    Player Owner; // who owns it
    public HomeClass(Location HomeLoc, Player Owner) {
        this.HomeLoc = HomeLoc;
        this.Owner = Owner;
    }

    // getters to get info
    public Location getHomeLoc() {
        return HomeLoc;
    }

    public Player getOwner() {
        return Owner;
    }
}
