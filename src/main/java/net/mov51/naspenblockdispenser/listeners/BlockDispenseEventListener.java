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

    private static final Material[] ShulkerBoxes = {
            Material.SHULKER_BOX,
            Material.BLACK_SHULKER_BOX,
            Material.BLUE_SHULKER_BOX,
            Material.BROWN_SHULKER_BOX,
            Material.CYAN_SHULKER_BOX,
            Material.GRAY_SHULKER_BOX,
            Material.GREEN_SHULKER_BOX,
            Material.LIGHT_BLUE_SHULKER_BOX,
            Material.LIGHT_GRAY_SHULKER_BOX,
            Material.LIME_SHULKER_BOX,
            Material.MAGENTA_SHULKER_BOX,
            Material.ORANGE_SHULKER_BOX,
            Material.PINK_SHULKER_BOX,
            Material.PURPLE_SHULKER_BOX,
            Material.RED_SHULKER_BOX,
            Material.WHITE_SHULKER_BOX,
            Material.YELLOW_SHULKER_BOX
    };

    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        if (event.getBlock().getType() == org.bukkit.Material.DISPENSER) {
            if (Arrays.asList(ShulkerBoxes).contains(event.getItem().getType())) {
                return;
            }
            if(event.getItem().getType() == Material.WITHER_SKELETON_SKULL){
                return;
            }
            if (event.getItem().getType().getMaxDurability() > 0) {
                ItemStack tool = event.getItem();
                Block breakAtBlock = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().add(((Directional) event.getBlock().getBlockData()).getFacing().getDirection()));
                if(breakAtBlock.getType().isAir()){
                    event.setCancelled(true);
                    event.getBlock().getLocation().getWorld().playSound(event.getBlock().getLocation(),org.bukkit.Sound.BLOCK_DISPENSER_FAIL,1,1);
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
        return !block.getLocation().toCenterLocation().getNearbyEntities(0.5, 0.5, 0.5).isEmpty();
    }
}
