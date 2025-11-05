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
@Entity(name = "PartStatus")
@Table
@SequenceGenerator(name = "PartStatus_seq", sequenceName = "PartStatus_seq", allocationSize = 1)
public class PartStatus extends BaseData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PartStatus_seq")
    private Long id;
}