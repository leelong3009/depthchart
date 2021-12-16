package com.depthchart.app;

import com.depthchart.enums.BasePositionEnum;
import com.depthchart.exceptions.ResourceNotFoundException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringJoiner;

public class AbstractSport<T extends BasePositionEnum> {
    private PlayerRepository playerRepository;
    Map<T, LinkedList<Integer>> depthChart;

    public AbstractSport(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        depthChart = new HashMap<>();
    }

    /**
     *
     * @param playerId id of the player
     * @param position position of the player
     * @param positionDepth position depth, passing -1 to add to the end of the chart
     */
    public void addPlayerToDepthChart(int playerId, T position, int positionDepth) {
        Player player = playerRepository.getPlayer(playerId);
        if (player == null) {
            throw new ResourceNotFoundException("Invalid player ID");
        }
        if (!depthChart.containsKey(position)) {
            LinkedList<Integer> playerIds = new LinkedList<>();
            validateDepthChartPosition(positionDepth, playerIds);
            playerIds.add(player.getId());
            depthChart.put(position, playerIds);
        } else {
            LinkedList<Integer> players = depthChart.get(position);
            validateDepthChartPosition(positionDepth, players);
            if (playerWasInDepthChart(player.getId(), players)) {
                removePlayerFromDepthChart(player.getId(), position);
            }
            if (positionDepth >= 0) {
                players.add(positionDepth, player.getId());
            } else {
                players.add(player.getId());
            }
        }
    }

    /**
     *
     * @param playerId id of the player
     * @param position position of the player
     */
    public void addPlayerToDepthChart(int playerId, T position) {
        addPlayerToDepthChart(playerId, position, -1);
    }

    /**
     *
     * @param playerId id of the player
     * @param position position of the player
     */
    public void removePlayerFromDepthChart(int playerId, T position) {
        LinkedList<Integer> players = depthChart.get(position);
        if (players != null) {
            players.remove((Integer) playerId);
        }
    }

    private void validateDepthChartPosition(int positionDepth, LinkedList<Integer> players) {
        if (positionDepth > players.size()) {
            throw new ResourceNotFoundException("Not valid position depth!");
        }
    }

    private boolean playerWasInDepthChart(int playerId, LinkedList<Integer> players) {
        return players.contains(playerId);
    }

    public int getPlayerPosition(int playerId, T position) {
        if (depthChart.containsKey(position)) {
            return depthChart.get(position).indexOf(playerId);
        } else {
            return -1;
        }
    }

    public String getFullDepthChart() {
        StringJoiner joiner = new StringJoiner(",\n");
        for (T position : depthChart.keySet()) {
            StringJoiner positionJoiner = new StringJoiner(", ", position.getValue() + ": [", "]");
            depthChart.get(position).forEach(playerId -> positionJoiner.add(playerId.toString()));
            joiner.add(positionJoiner.toString());
        }
        return joiner.toString();
    }

    /**
     *
     * @param playerId id of the player
     * @param position position of the player
     * @return A string in array format containing the player ids under this player in the chart.
     *
     * Time complexity O(n)
     */
    public String getPlayersUnderPlayerInDepthChart(int playerId, T position) {
        LinkedList<Integer> players = depthChart.get(position);
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        if (players != null) {
            Iterator<Integer> i = players.iterator();
            boolean foundPlayerInChart = false;
            while (i.hasNext()) {
                if (foundPlayerInChart) {
                    joiner.add(i.next().toString());
                } else if (playerId == i.next()) {
                    foundPlayerInChart = true;
                }
            }
        }
        return joiner.toString();
    }
}
