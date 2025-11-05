package com.niam.kardan.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MACHINE_TYPE {
    CNC("CNC");

    private final String name;
}