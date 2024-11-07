package com.maltsburg.plushies.geyser.utils;

import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.CustomItemData;
import org.geysermc.geyser.api.item.custom.CustomItemOptions;

import java.util.logging.Logger;

public class ItemBuilder {
    private static final Logger log = Logger.getLogger("Plushie-Geyser");
    public void buildAndRegisterItem(GeyserDefineCustomItemsEvent event, int cmd, String name, String itemType) {

        CustomItemOptions itemOptions = CustomItemOptions.builder()
                .customModelData(cmd)
                .build();

        CustomItemData data = CustomItemData.builder()
                .name(name)
                .displayName(name)
                .allowOffhand(true)
                .icon(name)
                .customItemOptions(itemOptions)
                .build();

        event.register(itemType, data);
    }
}
