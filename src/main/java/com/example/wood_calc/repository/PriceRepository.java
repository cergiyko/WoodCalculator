package com.example.wood_calc.repository;

import com.example.wood_calc.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    List<Price> findByTypeId(int id);
    List<Price> findByManufacturerId(int id);

}
