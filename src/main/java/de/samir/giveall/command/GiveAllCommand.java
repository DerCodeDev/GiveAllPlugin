package de.samir.giveall.command;

import de.samir.giveall.GiveAllPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(GiveAllPlugin.getPREFIX() + GiveAllPlugin.getValuesFromConfig("Messages.NoConsole"));
            return true;
        }
        final Player player = (Player) sender;

        if (!player.hasPermission(GiveAllPlugin.getValuesFromConfig("Permission"))) {
            player.sendMessage(GiveAllPlugin.getPREFIX() + GiveAllPlugin.getValuesFromConfig("Messages.NoPermissions"));
            return true;
        }

        if (args.length == 0) {

            final ItemStack itemStack = player.getItemInHand();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                player.sendMessage(GiveAllPlugin.getPREFIX() + GiveAllPlugin.getValuesFromConfig("Messages.NoItemInHand"));
                return true;
            }
            player.setItemInHand(null);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.getInventory().addItem(itemStack);
                if (itemStack.getItemMeta().getDisplayName() == null) {
                    onlinePlayer.sendMessage(GiveAllPlugin.getPREFIX() + GiveAllPlugin.
                            getValuesFromConfig("Messages.GiveItemWithType")
                            .replace("%TYPENAME%", itemStack.getType().name()));

                    onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.LEVEL_UP, 1, 1);
                    return true;
                }
                onlinePlayer.sendMessage(GiveAllPlugin.getPREFIX() + GiveAllPlugin.
                        getValuesFromConfig("Messages.GiveItemWithDisplayName")
                        .replace("%DISPLAYNAME%", itemStack.getItemMeta().getDisplayName()));
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.LEVEL_UP, 1, 1);
            }


            return true;
        }
        player.sendMessage(GiveAllPlugin.getPREFIX() + GiveAllPlugin.getValuesFromConfig("Messages.Usage"));
        return true;
    }
}
