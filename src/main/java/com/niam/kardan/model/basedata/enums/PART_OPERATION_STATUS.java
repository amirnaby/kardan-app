package com.niam.kardan.model.basedata.enums;

import com.niam.kardan.model.basedata.PartOperationStatus;

public enum PART_OPERATION_STATUS {
    PENDING, IN_PROGRESS, COMPLETED;
    static final Class<PartOperationStatus> aClass = PartOperationStatus.class;
}