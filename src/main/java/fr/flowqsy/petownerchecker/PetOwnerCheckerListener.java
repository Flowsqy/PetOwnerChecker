package fr.flowqsy.petownerchecker;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PetOwnerCheckerListener implements Listener {

    private final String message;

    public PetOwnerCheckerListener(YamlConfiguration configuration) {
        message = ChatColor.translateAlternateColorCodes('&',
                configuration.getString("message", "&a&nPropriétaire : &b%owner%")
        );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onClickEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Tameable pet) {
            final AnimalTamer owner = pet.getOwner();
            if (
                    owner != null
                    && !owner.getUniqueId().equals(event.getPlayer().getUniqueId())
                    && owner.getName() != null
            ) {
                event.getPlayer().spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        new TextComponent(TextComponent.fromLegacyText(
                                message.replace("%owner%", owner.getName())
                        ))
                );
            }
        }
    }

}
