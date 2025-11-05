package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.niam.common.utils.CustomLocalDateTimeDeserializer;
import com.niam.common.utils.CustomLocalDateTimeSerializer;
import com.niam.kardan.model.basedata.TaskStatus;
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
@Entity(name = "PartOperationTask")
@Table
@SequenceGenerator(name = "PartOperationTask_seq", sequenceName = "PartOperationTask_seq", allocationSize = 1)
public class PartOperationTask extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PartOperationTask_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "part_operation_id", referencedColumnName = "id", nullable = false)
    private PartOperation partOperation;
    @ManyToOne
    @JoinColumn(name = "target_machine_id", referencedColumnName = "id", nullable = false)
    private Machine machine;
    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private PartOperationTask parentTask;
    @ManyToOne
    @JoinColumn(name = "claimed_by_operator_id")
    private Operator claimedBy;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private TaskStatus taskStatus;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime claimedAt;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime startedAt;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime finishedAt;
}