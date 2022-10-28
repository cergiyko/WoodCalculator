package com.example.wood_calc.repository;

import com.example.wood_calc.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    List<Manufacturer> findByName(String name);

}
