package com.M19.PrivateMessageSystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class replyCommand implements CommandExecutor {

    private Main main;

    public replyCommand(Main main) {
        this.main = main;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (args.length <= 1){

                if(main.getRecentMessages().containsKey(player.getUniqueId())){
                    UUID uuid = main.getRecentMessages().get(player.getUniqueId());
                    if(Bukkit.getPlayer(uuid) != null){
                        Player target = Bukkit.getPlayer(uuid);

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
                        for (int i = 0; i < args.length; i++){
                            builder.append(args[i]).append(' ');
                        }
                        player.sendMessage(ChatColor.GREEN + "You -> " + ChatColor.AQUA + target.getName() + ' ');
                        target.sendMessage(ChatColor.YELLOW + player.getName() + " -> You: " + ChatColor.AQUA + builder);

                        main.getRecentMessages().put(player.getUniqueId(), target.getUniqueId());
                    }else{
                        player.sendMessage(ChatColor.RED + "there is no one to reply to. YOU HAVE NO FRIENDS!");
                    }
                }

            }else{
                player.sendMessage(ChatColor.RED + "Invalid usage!, use  /reply <Player> <reply>");
            }
        }

        return false;
    }
}
