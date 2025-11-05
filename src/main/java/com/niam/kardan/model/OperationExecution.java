package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.niam.common.utils.CustomLocalDateTimeDeserializer;
import com.niam.common.utils.CustomLocalDateTimeSerializer;
import com.niam.kardan.model.basedata.ExecutionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity(name = "OperationExecution")
@Table
@SequenceGenerator(name = "OperationExecution_seq", sequenceName = "OperationExecution_seq", allocationSize = 1)
public class OperationExecution extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OperationExecution_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private PartOperationTask task;
    @ManyToOne
    @JoinColumn(name = "part_operation_id", referencedColumnName = "id")
    private PartOperation partOperation;
    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;
    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "id")
    private Operator operator;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private ExecutionStatus executionStatus;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startTime;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime stopTime;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime endTime;
}