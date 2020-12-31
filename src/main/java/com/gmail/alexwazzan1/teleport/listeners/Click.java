package com.gmail.alexwazzan1.teleport.listeners;

import com.gmail.alexwazzan1.teleport.Main;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static java.lang.Math.*;

public class Click implements Listener {

    private Main plugin;
    private final static int REACH = 256;
    private final static Material ITEM = Material.BLAZE_ROD;

    public Click(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (Main.players.contains(p.getName()) && e.getClickedBlock() == null && e.getItem() != null && e.getItem().getType() == ITEM) {
            Location l = p.getLocation();

            float yaw = l.getYaw();
            float pitch = l.getPitch();

            float scale = (float) cos(pitch * PI / 180);
            float xDir = -1 * (float) sin(yaw * PI / 180) * abs(scale);
            float yDir = -1 * (float) sin(pitch * PI / 180);
            float zDir = (float) cos(yaw * PI / 180) * abs(scale);

            float height = (float) (p.isSneaking() ? 1.5 : 1.8);

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(255, 25, 25), 1);
            for (int i = 1; i <= 50; i++) {
                Location current = l.clone().add(i * xDir, i * yDir + height, i * zDir);
                p.getWorld().spawnParticle(Particle.REDSTONE, current, 5, dustOptions);
                if (!current.getBlock().isPassable()) {
                    break;
                }
            }

            // Force a delay:
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            int i;
            for (i = 5; i <= REACH; i++) {
                Location current = l.clone().add(i * xDir, i * yDir + height, i * zDir);
                if (!current.getBlock().isPassable()) {
                    current.subtract(xDir, yDir, zDir);
                    while (!current.getBlock().isPassable() || !current.clone().add(0, 1, 0).getBlock().isPassable()) {
                        current.add(0, 1, 0);
                    }
                    p.sendMessage(ChatColor.GREEN + "Teleported.");
                    p.teleport(current);
                    break;
                }
            }
            if (i > REACH) {
                p.sendMessage(ChatColor.RED + "Distance is too far. Failed to teleport.");
            }
        }

        return;
    }

}
