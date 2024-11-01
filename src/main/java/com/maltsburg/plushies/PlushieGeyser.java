package com.maltsburg.plushies;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.item.custom.CustomItemData;
import org.geysermc.geyser.api.item.custom.CustomItemOptions;


import java.io.File;
import java.util.*;

public class PlushieGeyser implements Extension {

    @Subscribe
    public void onGeyserPreInitializeEvent(GeyserDefineCustomItemsEvent event) {
        Plugin plushiesPlugin = Bukkit.getPluginManager().getPlugin("Plushies");

        // Check if the plugin is loaded
        if (plushiesPlugin == null) {
            this.logger().warning("Could not find Plushie folder.");
            return; // exit if plushie doesn't exist in the plugins folder
        }

        File pluginDataFolder = plushiesPlugin.getDataFolder();
        File plushiesFile = new File(pluginDataFolder, "plushies.yml");

        if (!plushiesFile.exists()) {
            this.logger().warning("The file " + plushiesFile.getAbsolutePath() + " does not exist.");
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(plushiesFile);
        ConfigurationSection plushiesSection = config.getConfigurationSection("plushies");

        if (plushiesSection == null) {
            this.logger().warning("No 'plushies' section found in the file: " + plushiesFile.getAbsolutePath());
            return;
        }

        Set<String> topLevelKeys = plushiesSection.getKeys(false);

        if (topLevelKeys.isEmpty()) {
            this.logger().warning("No keys found in the 'plushies' section of the file: " + plushiesFile.getAbsolutePath());
            return;
        }

        for (String key : topLevelKeys) {
            int customModelData = plushiesSection.getInt(key);
            key = key.toLowerCase();

            String name = key.replaceAll("[-_]", ""); // plushie config is so gross

            CustomItemOptions itemOptions = CustomItemOptions.builder()
                    .customModelData(customModelData)
                    .build();

            CustomItemData data = CustomItemData.builder()
                    .name(name)
                    .displayName(name)
                    .allowOffhand(true)
                    .icon(name)
                    .customItemOptions(itemOptions)
                    .build();

            event.register("minecraft:totem_of_undying", data);
        }
    }
}
