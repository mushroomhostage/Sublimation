***This this plugin has been merged into
[EnchantMore](http://dev.bukkit.org/server-mods/enchantmore/)!
If you want the same behavior as the original Sublimation plugin
below, disable all other effects except for Pickaxe + Silk Touch,
then minLevel:1, and sublimateIce:true. Since EnchantMore
encompasses the the functionality of Sublimation, this plugin
will no longer be maintained.***

---
---

Sublimation - silk touch ice, no longer overpowered

Remember in the Minecraft 1.9 beta prereleases, when the Silk Touch enchantment
was first introduced, you could legitimately obtain ice? Minecraft 1.0 removed this ability
as it was too overpowered: you could break ice and flood The Nether!

*Sublimation* gives you the best of both worlds: silk touch works on ice,
but if you attempt to place ice in The Nether, it will vaporize immediately
with a puff of smoke, just like emptying a bucket of water.
Much more realistic, right?


### Permissions 
sublimation.bypass (false) - Allows you to place ice in the nether

sublimation.collect (true) - Allows you to acquire ice by mining with silk touch

### Configuration 
*consume (true)*

If true, turns placed ice into air, removing from the player's inventory

If false, cancels event, giving the player their ice back

*smoke (true)*

Generate smoke particles, representing the sublimated ice

*collect (true)*

Enables the acquisition of ice as an item in the player's inventory, 
by mining it with a tool containing the Silk Touch enchantment. The player must
also have the sublimation.collect permission.

If your server allows ice to be obtained by other means (creative mode,
shops, trading, etc.), and you do not want to enable collection by silk touch,
you can disable this option to use **Sublimation** just for preventing 
Nether ice placement as a security measure. When disabled, block break
events are not registered.

### See also
Want to make Silk Touch even more useful? Check out [SilkSpawners](http://dev.bukkit.org/server-mods/silkspawners/).

***[Fork me on GitHub](https://github.com/mushroomhostage/Sublimation)***
