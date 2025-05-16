package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.niam.authserver.persistence.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity(name = "Part")
@Table
@SequenceGenerator(name = "part_seq", sequenceName = "part_seq", allocationSize = 1)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_seq")
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
    @OneToOne
    private User user;

    private LocalDateTime dueDate;
}