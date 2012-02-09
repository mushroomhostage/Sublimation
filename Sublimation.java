
package me.exphc.Sublimation;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.Material.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.*;

class SublimationPlaceListener implements Listener {
    Plugin plugin;

    public SublimationPlaceListener(Plugin pl) {
        plugin = pl;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        World world = block.getWorld();

        if (world.getEnvironment() == World.Environment.NETHER && block.getType() == Material.ICE && !player.hasPermission("sublimation.bypass")) {
            if (plugin.getConfig().getBoolean("consume", true)) {
                block.setType(Material.AIR);
            } else {
                // return to player
                event.setCancelled(true); 
            }

            if (plugin.getConfig().getBoolean("smoke", true)) {
                // turn into smoke
                world.playEffect(block.getLocation(), Effect.SMOKE, 0); 
            }
        }
    }
}

class SublimationBreakListener implements Listener {
    Sublimation plugin;

    public SublimationBreakListener(Sublimation plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    // drop ice
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.ICE) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack tool = player.getItemInHand();

        if (tool != null && tool.containsEnchantment(Enchantment.SILK_TOUCH) 
            && plugin.getConfig().getBoolean("collect") 
            && player.hasPermission("sublimation.collect")) {

            World world = block.getWorld();
            ItemStack drop = new ItemStack(block.getType(), 1);

            world.dropItemNaturally(block.getLocation(), drop);

            block.setType(Material.AIR);
            // we would like to NOT cancel event, since the block DID in fact break,
            // BUT, some modded craftbukkits (1.1-R3+MLMP+MCF+IC2) will NPE if we don't!
            event.setCancelled(true);
            // however, durability is still used up
        }
    }
}

public class Sublimation extends JavaPlugin {
    public Logger log = Logger.getLogger("Minecraft");
    SublimationPlaceListener placeListener;
    SublimationBreakListener breakListener;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        placeListener = new SublimationPlaceListener(this);

        if (getConfig().getBoolean("collect")) {
            breakListener = new SublimationBreakListener(this);
        }
    }

    public void onDisable() {
    }
}
