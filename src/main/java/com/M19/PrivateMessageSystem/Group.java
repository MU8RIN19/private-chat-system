package com.M19.PrivateMessageSystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Group {
    private UUID owner;
    private final Set<UUID> members = new HashSet<>();

    public Group(UUID owner) {
        this.owner = owner;
        this.members.add(owner);
    }

    public UUID getOwner() {
        return owner;
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID memberId, Main main) {
        members.add(memberId);
        main.getGroupByMember().put(memberId, this);
    }

    public void removeMember(UUID memberId, Main main) {
        members.remove(memberId);
        main.getGroupByMember().remove(memberId);

        if (members.isEmpty()){
            return;
        }

        if (owner.equals(memberId)){
            owner = members.iterator().next();
            Player newOwner = Bukkit.getPlayer(owner);
            if (newOwner != null){
                newOwner.sendMessage(ChatColor.YELLOW + "You are now the group owner.");
            }
        }
    }
}
