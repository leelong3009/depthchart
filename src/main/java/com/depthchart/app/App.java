package com.depthchart.app;

import com.depthchart.enums.NFLPositionEnum;

public class App {
    public static void main(String[] args) {
        PlayerRepository playerRepository = new PlayerRepository();
        AbstractSport sport = new NFL(playerRepository);

        Player bob = new Player(1, "Bob", NFLPositionEnum.WR);
        Player alice = new Player(2, "Alice", NFLPositionEnum.WR);
        Player charlie = new Player(3, "Cherlie", NFLPositionEnum.WR);

        playerRepository.addPlayer(bob);
        playerRepository.addPlayer(alice);
        playerRepository.addPlayer(charlie);

        sport.addPlayerToDepthChart(1, NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(2, NFLPositionEnum.WR, 0);
        sport.addPlayerToDepthChart(3, NFLPositionEnum.WR, 2);
        sport.addPlayerToDepthChart(1, NFLPositionEnum.KR, 0);

        System.out.println("Full Depth Chart");
        System.out.println(sport.getFullDepthChart());

        System.out.println("Players Under Player in Depth Chart");
        System.out.println(sport.getPlayersUnderPlayerInDepthChart(2, NFLPositionEnum.WR));
    }
}
