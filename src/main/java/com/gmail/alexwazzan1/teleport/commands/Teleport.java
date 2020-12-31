package com.gmail.alexwazzan1.teleport.commands;

import com.gmail.alexwazzan1.teleport.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {

    private Main plugin;

    public Teleport(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("teleport").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command.");
            return true;
        }

        Player p = (Player) sender;

        // Check for an invalid command:
        if (args.length != 0) {
            p.sendMessage(ChatColor.RED + "Invalid command. Try again.");
            return true;
        }

        // Toggle ON:
        if (!(Main.players.contains(p.getName()))) {
            p.sendMessage("Teleport: " + ChatColor.GREEN + "[ON] " + ChatColor.RED + "[OFF]");
            Main.players.add(p.getName());
        // Toggle OFF:
        } else {
            p.sendMessage("Teleport: " + ChatColor.RED + "[ON] " + ChatColor.GREEN + "[OFF]");
            Main.players.remove(p.getName());
        }

        return true;
    }
}
