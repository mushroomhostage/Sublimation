
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

    // drop ice
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.ICE) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack tool = player.getItemInHand();

        if (tool.containsEnchantment(Enchantment.SILK_TOUCH) && plugin.getConfig().getBoolean("collect")) {
            World world = block.getWorld();
            ItemStack drop = new ItemStack(block.getType(), 1);

            world.dropItemNaturally(block.getLocation(), drop);

            block.setType(Material.AIR);
            // do not cancel the event, so that it still uses up tool durability
        }
    }
}

public class Sublimation extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    BlockListener blockListener;

    public void onEnable() {
        log.info(getDescription().getFullName() + " enabled");

        getConfig().options().copyDefaults(true);
        saveConfig();

        blockListener = new SublimationListener(this);

        Bukkit.getPluginManager().registerEvent(org.bukkit.event.Event.Type.BLOCK_PLACE, blockListener, org.bukkit.event.Event.Priority.Highest, this);
        if (getConfig().getBoolean("collect")) {
            Bukkit.getPluginManager().registerEvent(org.bukkit.event.Event.Type.BLOCK_BREAK, blockListener, org.bukkit.event.Event.Priority.Highest, this);
        }
    }

    public void onDisable() {
        log.info(getDescription().getFullName() + " disabled");
    }
}
