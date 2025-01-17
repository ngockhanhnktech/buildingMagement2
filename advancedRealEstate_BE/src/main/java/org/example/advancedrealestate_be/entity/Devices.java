package org.example.advancedrealestate_be.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "devices")
@Getter
@Setter
public class Devices {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String device_name;
    private LocalDate installation_date;
    private int status;
    private double price;
    private String description;

    @ManyToOne
    @JoinColumn(name="building_id")
    private Building building;
}
