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
@Entity(name = "TaskStatus")
@Table
@SequenceGenerator(name = "TaskStatus_seq", sequenceName = "TaskStatus_seq", allocationSize = 1)
public class TaskStatus extends BaseData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TaskStatus_seq")
    private Long id;
}