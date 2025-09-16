package com.example.dialogue;

import com.example.dialogue.client.events.DialogueEventHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DialogueMod.MODID)
public class DialogueMod {
    public static final String MODID = "dialogue";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DialogueMod() {
        // Register client setup
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        // Register quest + villager interaction handlers
        MinecraftForge.EVENT_BUS.register(DialogueEventHandler.class);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Dialogue Mod Client setup complete!");
    }
}
