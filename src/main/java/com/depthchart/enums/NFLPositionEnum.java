package com.depthchart.enums;

import com.depthchart.exceptions.ResourceNotFoundException;

public enum NFLPositionEnum implements BasePositionEnum {
    QB,
    WR,
    RB,
    TE,
    K,
    P,
    KR,
    PR;

    public static NFLPositionEnum fromValue(String position) {
        try {
            return NFLPositionEnum.valueOf(position);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid position value");
        }
    }

    @Override public String getValue() {
        return this.name();
    }
}
