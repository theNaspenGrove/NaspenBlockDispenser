package net.mov51.naspenblockdispenser.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

import static net.mov51.naspenblockdispenser.NaspenBlockDispenser.plugin;

public class BlockDispenseEventListener implements Listener {
    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        if(event.getBlock().getType() == org.bukkit.Material.DISPENSER){
            if(event.getItem().getType() == Material.SHULKER_BOX){
                return;
            }
            if(event.getItem().getType().isBlock()){
                Block placeAtBlock = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().add(((Directional) event.getBlock().getBlockData()).getFacing().getDirection()));
                if(event.getItem().getType() == Material.TNT){
                    if (!placeAtBlock.getType().isAir()) {
                        event.setCancelled(true);
                        placeAtBlock.breakNaturally();
                        Dispenser dispenser = (Dispenser) event.getBlock().getState();
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            dispenser.getInventory().removeItem(event.getItem());
                            dispenser.update();
                        }, 1L);
                    }
                    return;
                }
                if(placeAtBlock.getType().isAir() && !containsEntity(placeAtBlock)){
                    placeAtBlock.setType(event.getItem().getType());
                    Dispenser dispenser = (Dispenser) event.getBlock().getState();
                    event.setCancelled(true);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        dispenser.getInventory().removeItem(event.getItem());
                        dispenser.update();
                    }, 1L);
                }
            }
        }
    }
    private static boolean containsEntity(Block block){
        return block.getLocation().toCenterLocation().getNearbyLivingEntities(0.5).size() > 0;
    }
}
