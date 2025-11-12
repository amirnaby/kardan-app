package com.niam.kardan.model.basedata.enums;

import com.niam.kardan.model.basedata.TaskStatus;

public enum TASK_STATUS {
    PENDING, CLAIMED, IN_PROGRESS, COMPLETED, CANCELED;
    static final Class<TaskStatus> aClass = TaskStatus.class;
}