package de.kuerbisskraft.gummi

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Gummi : JavaPlugin(), Listener {
    val namespaceKeys = mutableListOf<NamespacedKey>()

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val p = event.player
        for (nsk in namespaceKeys) {
            if (!p.hasDiscoveredRecipe(nsk)) {
                p.discoverRecipe(nsk)
            }
        }
    }

    override fun onEnable() {
        for (a in 1..5) {
            Bukkit.addRecipe(gummiBSaft(a))
        }
    }

    fun gummiBSaft(amplifier: Int): ShapelessRecipe {
        val saft = ItemStack(Material.POTION, 1)
        val saftMeta = saft.itemMeta as PotionMeta?
        if (saftMeta != null) {
            when (amplifier) {
                1 -> saftMeta.setDisplayName("Gummibärensaft I")
                2 -> saftMeta.setDisplayName("Gummibärensaft II")
                3 -> saftMeta.setDisplayName("Gummibärensaft III")
                4 -> saftMeta.setDisplayName("Gummibärensaft IV")
                5 -> saftMeta.setDisplayName("Gummibärensaft V")
                6 -> saftMeta.setDisplayName("Gummibärensaft VI")
                7 -> saftMeta.setDisplayName("Gummibärensaft VII")
                8 -> saftMeta.setDisplayName("Gummibärensaft VIII")
                9 -> saftMeta.setDisplayName("Gummibärensaft IX")
                10 -> saftMeta.setDisplayName("Gummibärensaft X")
                else -> saftMeta.setDisplayName("Gummibärensaft $amplifier")
            }
            saftMeta.clearCustomEffects()
            val duration = 800 + amplifier * 400
            saftMeta.addCustomEffect(PotionEffect(PotionEffectType.JUMP, duration, amplifier, false, true, true), false)
            saftMeta.addCustomEffect(
                PotionEffect(
                    PotionEffectType.FAST_DIGGING,
                    duration,
                    amplifier / 2,
                    false,
                    true,
                    true
                ), false
            )
            saftMeta.addCustomEffect(PotionEffect(PotionEffectType.SPEED, duration, 1, false, true, true), false)
            saftMeta.addCustomEffect(
                PotionEffect(PotionEffectType.REGENERATION, 40, amplifier * 2, false, true, true),
                false
            )
        }
        saft.itemMeta = saftMeta
        val nsk = NamespacedKey(this, "gummisaft$amplifier")
        namespaceKeys.add(nsk)
        val r = ShapelessRecipe(nsk, saft)
        for (i in 0 until amplifier) {
            r.addIngredient(Material.SWEET_BERRIES)
        }
        r.addIngredient(Material.HONEY_BOTTLE)
        return r
    }
}
