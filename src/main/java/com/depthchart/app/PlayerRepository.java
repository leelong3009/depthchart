package com.depthchart.app;

import com.depthchart.exceptions.DuplicatedResourceException;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {
    private Map<Integer, Player> playerMap;

    public PlayerRepository() {
        playerMap = new HashMap<>();
    }

    public void addPlayer(Player player) {
        if (playerMap.containsKey(player.getId())) {
            throw new DuplicatedResourceException("Player already existed!");
        }
        playerMap.put(player.getId(), player);
    }

    public Player getPlayer(int playerId) {
        return playerMap.get(playerId);
    }

    public boolean containsPlayer(int playerId) {
        return playerMap.containsKey(playerId);
    }
}
