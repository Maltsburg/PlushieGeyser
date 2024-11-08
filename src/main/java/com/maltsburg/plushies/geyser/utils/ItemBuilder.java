package com.maltsburg.plushies.geyser.utils;

import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomItemsEvent;
import org.geysermc.geyser.api.item.custom.CustomItemData;
import org.geysermc.geyser.api.item.custom.CustomItemOptions;

public class ItemBuilder {
    public void buildAndRegisterItem(GeyserDefineCustomItemsEvent event, int cmd, String name, String itemType) {

        String cleanName = name.replaceAll("\\..*$", "");
        String displayName = cleanName.replaceAll("_"," ");

        CustomItemOptions itemOptions = CustomItemOptions.builder()
                .customModelData(cmd)
                .build();

        CustomItemData data = CustomItemData.builder()
                .name(name)
                .displayName(displayName)
                .allowOffhand(true)
                .icon(cleanName)
                .customItemOptions(itemOptions)
                .build();

        event.register(itemType, data);
    }
}
