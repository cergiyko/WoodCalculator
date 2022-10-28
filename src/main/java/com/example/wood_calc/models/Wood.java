package com.example.wood_calc.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wood")

public class Wood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "type_id")
    private WoodType type;

    @OneToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "length")
    private double length;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;

    @Column(name = "valume")
    private double value;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "quantity_val")
    private double quantityVal;

    @OneToOne
    @JoinColumn(name = "price_id")
    private Price price;

    @Column(name = "cost")
    private double cost;
}

