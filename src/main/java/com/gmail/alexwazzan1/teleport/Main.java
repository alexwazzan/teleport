package com.gmail.alexwazzan1.teleport;

import com.gmail.alexwazzan1.teleport.commands.Teleport;
import com.gmail.alexwazzan1.teleport.listeners.Click;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class Main extends JavaPlugin {

    public static HashSet<String> players;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");

        players = new HashSet<>();

        new Teleport(this);
        new Click(this);
    }

}
