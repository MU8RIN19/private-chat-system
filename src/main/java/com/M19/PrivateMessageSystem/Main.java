package com.M19.PrivateMessageSystem;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    // /messageCommand <Player> <messageCommand>
    // /replyCommand <messageCommand>
    // /blockCommand <player>
    // all of them can have custom colors

    private HashMap<UUID, UUID> recentMessages ;
    private HashMap<UUID, HashSet<UUID>> blockedPlayers;

    @Override
    public void onEnable() {
    getCommand("message").setExecutor(new messageCommand(this));
    getCommand("reply").setExecutor(new replyCommand(this));
    getCommand("block").setExecutor(new blockCommand(this));

    recentMessages = new HashMap<>();
    blockedPlayers = new HashMap<>();

    Bukkit.getPluginManager().registerEvents(this, this);


    }
    public HashMap<UUID, UUID> getRecentMessages(){return recentMessages;}
    public HashMap<UUID, HashSet<UUID>> getBlockedPlayers(){return blockedPlayers;}
    public Set<UUID> getBlockedList(UUID playerId){
        return blockedPlayers.computeIfAbsent(playerId, k -> new HashSet<>());
    }

    @EventHandler
    public void onQueue(PlayerQuitEvent e){
        recentMessages.remove(e.getPlayer().getUniqueId());
    }

}
