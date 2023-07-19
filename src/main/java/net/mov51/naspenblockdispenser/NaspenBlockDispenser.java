package net.mov51.naspenblockdispenser;

import net.mov51.naspenblockdispenser.listeners.BlockDispenseEventListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class NaspenBlockDispenser extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new BlockDispenseEventListener(), this);
        getLogger().info("Dispense your blocks!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
