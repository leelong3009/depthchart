package com.depthchart.app;

import com.depthchart.enums.MLBPositionEnum;

public class MLB extends AbstractSport<MLBPositionEnum> {
    public MLB(PlayerRepository playerRepository) {
        super(playerRepository);
    }
}
