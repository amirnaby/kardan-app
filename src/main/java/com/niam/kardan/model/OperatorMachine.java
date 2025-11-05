package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.niam.common.utils.CustomLocalDateTimeDeserializer;
import com.niam.common.utils.CustomLocalDateTimeSerializer;
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
@Entity(name = "OperatorMachine")
@Table
@SequenceGenerator(name = "OperatorMachine_seq", sequenceName = "OperatorMachine_seq", allocationSize = 1)
public class OperatorMachine extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OperatorMachine_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = false)
    private Operator operator;
    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id", nullable = false)
    private Machine machine;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime assignedAt;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime unassignedAt;
}