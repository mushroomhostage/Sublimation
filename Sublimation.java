
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

class SublimationListener extends BlockListener {
    Plugin plugin;

    public SublimationListener(Plugin pl) {
        plugin = pl;
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        World world = block.getWorld();

        if (world.getEnvironment() == World.Environment.NETHER && block.getType() == Material.ICE) {
            // sublimate
            world.playEffect(player.getLocation(), Effect.SMOKE, 0);    

            event.setCancelled(true);
        }
    }
}

public class Sublimation extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    BlockListener blockListener;

    public void onEnable() {
        log.info("Enabling sublimation");

        blockListener = new SublimationListener(this);

        Bukkit.getPluginManager().registerEvent(org.bukkit.event.Event.Type.BLOCK_PLACE, blockListener, org.bukkit.event.Event.Priority.Highest, this);
    }

    public void onDisable() {
        log.info("Disabling sublimation");
    }
}
