package net.mov51.naspenblockdispenser.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static net.mov51.naspenblockdispenser.NaspenBlockDispenser.plugin;

public class BlockDispenseEventListener implements Listener {
    private static final Material[] tools = {
            //wooden
            Material.WOODEN_PICKAXE,
            Material.WOODEN_AXE,
            Material.WOODEN_SHOVEL,
            Material.WOODEN_HOE,
            //stone
            Material.STONE_PICKAXE,
            Material.STONE_AXE,
            Material.STONE_SHOVEL,
            Material.STONE_HOE,
            //iron
            Material.IRON_PICKAXE,
            Material.IRON_AXE,
            Material.IRON_SHOVEL,
            Material.IRON_HOE,
            //golden
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_AXE,
            Material.GOLDEN_SHOVEL,
            Material.GOLDEN_HOE,
            //diamond
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_AXE,
            Material.DIAMOND_SHOVEL,
            Material.DIAMOND_HOE,
            //netherite
            Material.NETHERITE_PICKAXE,
            Material.NETHERITE_AXE,
            Material.NETHERITE_SHOVEL,
            Material.NETHERITE_HOE,
            //other
            Material.SHEARS
    };

    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        if (event.getBlock().getType() == org.bukkit.Material.DISPENSER) {
            if (event.getItem().getType() == Material.SHULKER_BOX) {
                return;
            }
            if (Arrays.asList(tools).contains(event.getItem().getType())) {
                ItemStack tool = event.getItem();
                Block breakAtBlock = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().add(((Directional) event.getBlock().getBlockData()).getFacing().getDirection()));
                if(breakAtBlock.getType().isAir()){
                    return;
                }
                breakAtBlock.breakNaturally(tool);
                event.setCancelled(true);
            }
            if (event.getItem().getType().isBlock()) {
                Block placeAtBlock = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().add(((Directional) event.getBlock().getBlockData()).getFacing().getDirection()));
                if (placeAtBlock.getType().isAir() && !containsEntity(placeAtBlock)) {
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

    private static boolean containsEntity(Block block) {
        return !block.getLocation().toCenterLocation().getNearbyLivingEntities(0.5).isEmpty();
    }
}
