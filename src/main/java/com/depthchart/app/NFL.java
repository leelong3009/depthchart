package com.depthchart.app;

import com.depthchart.enums.NFLPositionEnum;

public class NFL extends AbstractSport<NFLPositionEnum> {
    public NFL(PlayerRepository playerRepository) {
        super(playerRepository);
    }
}
