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
@Entity(name = "MachineType")
@Table
@SequenceGenerator(name = "MachineType_seq", sequenceName = "MachineType_seq", allocationSize = 1)
public class MachineType extends BaseData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MachineType_seq")
    private Long id;
}