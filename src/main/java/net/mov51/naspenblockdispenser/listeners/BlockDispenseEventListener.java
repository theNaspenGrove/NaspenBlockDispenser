package net.mov51.naspenblockdispenser.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

public class BlockDispenseEventListener implements Listener {
    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        if(event.getBlock().getType() == org.bukkit.Material.DISPENSER){
            if(event.getItem().getType() == Material.TNT){
                return;
            }
            if(event.getItem().getType() == Material.WATER_BUCKET){
                return;
            }
            if(event.getItem().getType().isBlock()){
                Block placeAtBlock = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().add(((Directional) event.getBlock().getBlockData()).getFacing().getDirection()));
                if(placeAtBlock.getType().isAir() && !containsEntity(placeAtBlock)){
                    placeAtBlock.setType(event.getItem().getType());
                    event.setItem(new ItemStack(org.bukkit.Material.AIR));
                }
            }
        }
    }
    private static boolean containsEntity(Block block){
        return block.getLocation().toCenterLocation().getNearbyLivingEntities(0.5).size() > 0;
    }
}
