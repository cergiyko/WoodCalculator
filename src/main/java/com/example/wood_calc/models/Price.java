package com.example.wood_calc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Table(name = "price")
@AllArgsConstructor
@NoArgsConstructor
public class Price {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;

        @OneToOne
        @JoinColumn(name = "manufacturer_id")
        private Manufacturer manufacturer;

        @OneToOne
        @JoinColumn(name = "type_id")
        private WoodType type;

        @Column(name = "price")
        private double price;
}
