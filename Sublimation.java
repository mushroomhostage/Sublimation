
package com.exphc.Sublimation;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.Material.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.*;

class SmokeTask implements Runnable {
    Location location;

    public SmokeTask(Location loc) {
        location = loc;
    }

    public void run() {
        location.getWorld().playEffect(location, Effect.SMOKE, 0);
    }
}

class SublimationListener extends BlockListener {
    Plugin plugin;

    public SublimationListener(Plugin pl) {
        plugin = pl;
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        World world = block.getWorld();

        if (world.getEnvironment() == World.Environment.NETHER && block.getType() == Material.ICE && !player.hasPermission("sublimation.bypass")) {
            if (plugin.getConfig().getBoolean("giveBack", false)) {
                // return to player
                event.setCancelled(true); 
            } else {
                Location location = block.getLocation();

                // make air
                block.setType(Material.AIR);

                //SmokeTask smokeTask = new SmokeTask(location);
                //Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, smokeTask, 0);

                // sublimate
                // TODO: delay smoke? doesn't seem to show
                world.playEffect(block.getLocation(), Effect.SMOKE, 0);    
            }
        }
    }
}

public class Sublimation extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    BlockListener blockListener;

    public void onEnable() {
        log.info("Enabling sublimation");

        if (!this.getConfig().contains("version")) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }


        blockListener = new SublimationListener(this);

        Bukkit.getPluginManager().registerEvent(org.bukkit.event.Event.Type.BLOCK_PLACE, blockListener, org.bukkit.event.Event.Priority.Highest, this);
    }

    public void onDisable() {
        log.info("Disabling sublimation");
    }
}
