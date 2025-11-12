package com.niam.kardan.model.basedata.enums;

import com.niam.kardan.model.basedata.ProjectStatus;

public enum PROJECT_STATUS {
    PLANNED, IN_PROGRESS, COMPLETED, PAUSED;
    static final Class<ProjectStatus> aClass = ProjectStatus.class;
}