package com.niam.kardan.model.basedata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "ExecutionStatus")
@Table
@SequenceGenerator(name = "ExecutionStatus_seq", sequenceName = "ExecutionStatus_seq", allocationSize = 1)
public class ExecutionStatus extends BaseData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExecutionStatus_seq")
    private Long id;
}