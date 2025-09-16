package com.example.dialogue.client.events;

import com.example.dialogue.client.screen.DialogueScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DialogueEventHandler {

    @SubscribeEvent
    public static void onRightClickVillager(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Villager villager)) return;

        Player player = event.getEntity();

        // Client-side only
        if (event.getSide().isClient()) {
            if (player.isShiftKeyDown()) {
                // Freeze villager while talking
                villager.setTradingPlayer((Player) player); // villager stays in place like trading


                // Open DialogueScreen
                Minecraft.getInstance().setScreen(
                        new DialogueScreen(Component.literal("Greetings, traveler!"), villager)
                );

                // Cancel vanilla trading
                event.setCanceled(true);
            }
            // else: vanilla trade opens normally
        }
    }
}
