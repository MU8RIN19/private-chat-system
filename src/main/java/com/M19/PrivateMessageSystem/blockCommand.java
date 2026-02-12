package com.M19.PrivateMessageSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class blockCommand implements CommandExecutor {

    private Main main;

    public blockCommand(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length != 1){
                player.sendMessage("§cInvalid usage! Use /block <player>");
                return true;
            }

            Player target = player.getServer().getPlayerExact(args[0]);
            if (target == null){
                player.sendMessage("§cThis player was not found!");
                return true;
            }

            if (target.getUniqueId().equals(player.getUniqueId())){
                player.sendMessage("§cYou cannot block yourself.");
                return true;
            }

            Set<java.util.UUID> blocked = main.getBlockedList(player.getUniqueId());
            if (blocked.contains(target.getUniqueId())){
                blocked.remove(target.getUniqueId());
                player.sendMessage("§aYou have unblocked " + target.getName() + ".");
            }else{
                blocked.add(target.getUniqueId());
                player.sendMessage("§eYou have blocked " + target.getName() + ".");
            }
            return true;
        }

        return false;
    }
}
