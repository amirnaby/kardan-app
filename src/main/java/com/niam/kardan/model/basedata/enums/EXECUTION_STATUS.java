package com.niam.kardan.model.basedata.enums;

import com.niam.kardan.model.basedata.ExecutionStatus;

public enum EXECUTION_STATUS {
    STARTED, STOPPED, RUNNING, COMPLETED;
    static final Class<ExecutionStatus> aClass = ExecutionStatus.class;
}