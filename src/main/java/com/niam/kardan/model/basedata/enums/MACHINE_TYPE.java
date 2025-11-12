package com.niam.kardan.model.basedata.enums;

import com.niam.kardan.model.basedata.MachineType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MACHINE_TYPE {
    CNC("CNC");

    static final Class<MachineType> aClass = MachineType.class;
    private final String name;
}