package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity(name = "Plan")
@Table
@SequenceGenerator(name = "plan_seq", sequenceName = "plan_seq", allocationSize = 1)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_seq")
    private Long id;
    private String name;
    private String code;
    @ManyToOne
    @JoinColumn
    private Part part;
    @ManyToOne
    @JoinColumn
    private Operation operation;
    private Integer priority;
    private String username;
    private LocalDateTime dueDate;
}