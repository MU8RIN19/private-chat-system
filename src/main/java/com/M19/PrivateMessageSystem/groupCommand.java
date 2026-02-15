package com.M19.PrivateMessageSystem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class groupCommand implements CommandExecutor {

    private final Main main;

    public groupCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0){
            player.sendMessage(ChatColor.RED + "Usage: /group make <player1> <player2> ... OR /group leave");
            return true;
        }

        if (args[0].equalsIgnoreCase("make")){
            if (args.length < 2){
                player.sendMessage(ChatColor.RED + "Usage: /group make <player1> <player2> ...");
                return true;
            }

            if (main.getGroupByMember().containsKey(player.getUniqueId())){
                player.sendMessage(ChatColor.RED + "You are already in a group. Use /group leave first.");
                return true;
            }

            Group group = new Group(player.getUniqueId());
            main.getGroupByMember().put(player.getUniqueId(), group);

            for (int i = 1; i < args.length; i++){
                Player target = player.getServer().getPlayerExact(args[i]);
                if (target == null){
                    player.sendMessage(ChatColor.RED + "Player not found: " + args[i]);
                    continue;
                }
                if (main.getGroupByMember().containsKey(target.getUniqueId())){
                    player.sendMessage(ChatColor.RED + target.getName() + " is already in a group.");
                    continue;
                }
                if (target.getUniqueId().equals(player.getUniqueId())){
                    continue;
                }
                group.addMember(target.getUniqueId(), main);
                target.sendMessage(ChatColor.YELLOW + player.getName() + " added you to a group.");
            }

            player.sendMessage(ChatColor.GREEN + "Group created with " + group.getMembers().size() + " members.");
            return true;
        }

        if (args[0].equalsIgnoreCase("leave")){
            Group group = main.getGroupByMember().get(player.getUniqueId());
            if (group == null){
                player.sendMessage(ChatColor.RED + "You are not in a group.");
                return true;
            }

            group.removeMember(player.getUniqueId(), main);
            player.sendMessage(ChatColor.YELLOW + "You left the group.");
            return true;
        }

        player.sendMessage(ChatColor.RED + "Unknown subcommand. Use /group make or /group leave.");
        return true;
    }
}
