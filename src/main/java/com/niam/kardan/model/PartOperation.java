package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.niam.kardan.model.basedata.PartOperationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity(name = "PartOperation")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"part_id", "sequence"}))
@SequenceGenerator(name = "PartOperation_seq", sequenceName = "PartOperation_seq", allocationSize = 1)
public class PartOperation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PartOperation_seq")
    private Long id;
    @NotNull
    private Integer sequence;
    @ManyToOne
    @JoinColumn(name = "part_id", referencedColumnName = "id", nullable = false)
    private Part part;
    @ManyToOne
    @JoinColumn(name = "operation_id", referencedColumnName = "id", nullable = false)
    private Operation operation;
    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id", nullable = false)
    private Machine machine;
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private PartOperationStatus partOperationStatus;
    private Duration estimatedDuration;
}