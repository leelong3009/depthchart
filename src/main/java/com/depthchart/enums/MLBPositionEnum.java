package com.depthchart.enums;

public enum MLBPositionEnum implements BasePositionEnum {
    P_SP("SP"),
    P_RP("RP"),
    P_C("C"),
    P_1B("1B"),
    P_2B("2B"),
    P_3B("3B"),
    P_SS("SS"),
    P_LF("LF"),
    P_RF("RF"),
    P_CF("CF"),
    P_DH("DH");

    private String value;

    MLBPositionEnum(String value) {
        this.value = value;
    }

    @Override public String getValue() {
        return value;
    }
}
