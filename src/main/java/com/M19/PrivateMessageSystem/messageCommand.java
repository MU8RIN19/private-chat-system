package com.M19.PrivateMessageSystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;



public class messageCommand implements CommandExecutor {

    private Main main;

    public messageCommand(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length >= 2){
                if (Bukkit.getPlayerExact(args[0]) != null){

                    Player target = Bukkit.getPlayerExact(args[0]);

                    Set<java.util.UUID> senderBlocked = main.getBlockedList(player.getUniqueId());
                    if (senderBlocked.contains(target.getUniqueId())){
                        player.sendMessage(ChatColor.RED + "You have blocked " + target.getName() + ".");
                        return true;
                    }

                    Set<java.util.UUID> targetBlocked = main.getBlockedList(target.getUniqueId());
                    if (targetBlocked.contains(player.getUniqueId())){
                        player.sendMessage(ChatColor.RED + "You are blocked by " + target.getName() + ".");
                        return true;
                    }

                    StringBuilder builder = new StringBuilder();
                    for (int i = 1; i < args.length; i++){
                        builder.append(args[i]).append(' ');
                    }
                    player.sendMessage(ChatColor.YELLOW + "You -> " + target.getName() + ' ');
                    target.sendMessage(ChatColor.YELLOW + player.getName() + " -> You: " + builder);

                    main.getRecentMessages().put(player.getUniqueId(), target.getUniqueId());
                }else{
                    player.sendMessage(ChatColor.RED + "This Player was not found!");
                }

            }else{
                player.sendMessage(ChatColor.RED + "Invalid usage!, use  /message <Player> <message>");

            }

        }

        return false;
    }
}
