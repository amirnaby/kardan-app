package com.niam.kardan.model.basedata.enums;

import com.niam.kardan.model.basedata.MachineStatus;

public enum MACHINE_STATUS {
    ACTIVE, BUSY, MAINTENANCE, OFFLINE;
    static final Class<MachineStatus> aClass = MachineStatus.class;
}