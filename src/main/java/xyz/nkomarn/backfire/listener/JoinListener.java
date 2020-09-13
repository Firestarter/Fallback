package xyz.nkomarn.backfire.listener;

import io.papermc.lib.PaperLib;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import xyz.nkomarn.backfire.Backfire;

import java.util.Collections;

public class JoinListener implements Listener {

    private final Backfire backfire;
    private final Location spawn;
    private final ItemStack boots;

    private final String serverVersion;

    public JoinListener(@NotNull Backfire backfire) {
        this.backfire = backfire;
        this.spawn = new Location(Bukkit.getWorld("world"), 603.5, 38.2, -820.5, -90, 0);
        this.serverVersion = backfire.getConfig().getString("server.version");

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setColor(Color.fromRGB(135, 190, 255));
        meta.addEnchant(Enchantment.DEPTH_STRIDER, 1, false);
        meta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lnyoom boots"));
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Make you go nyooooooom."));
        meta.setUnbreakable(true);
        boots.setItemMeta(meta);
        boots.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        this.boots = boots;
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        event.setJoinMessage("");

        player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 99999, 0, true, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 0, true, false));
        player.getInventory().setBoots(boots);

        PaperLib.teleportAsync(player, spawn).thenAccept((a) -> handleConnection(player));
    }

    private void handleConnection(@NotNull Player player) {
        player.playSound(spawn, Sound.MUSIC_UNDER_WATER, 1.0f, 1.0f);

        if (isCompatible(player)) {
            player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_RETURN, 1.0f, 1.0f);
            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
        player.sendTitle(ChatColor.RED + ChatColor.BOLD.toString() + "Incompatible client.",
                "You must be running " + serverVersion + " to join.", 10, 1024, 20);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lNOTICE: &eYou must be running version "
                + serverVersion + " to connect to the main server."));

        Bukkit.getLogger().warning(player.getName() + " joined with an outdated version (" + getPlayerProtocol(player) + ").");
    }

    private int getPlayerProtocol(@NotNull Player player) {
        return backfire.getViaAPI().getPlayerVersion(player.getUniqueId());
    }

    private boolean isCompatible(@NotNull Player player) {
        int playerProtocol = getPlayerProtocol(player);
        return playerProtocol >= backfire.getConfig().getInt("server.protocol.min")
                && playerProtocol <= backfire.getConfig().getInt("server.protocol.max");
    }
}
