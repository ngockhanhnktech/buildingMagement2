package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "maintenances")
@Getter
@Setter
public class Maintenances {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private DateTime maintenance_date;
    private String description;
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;


}
