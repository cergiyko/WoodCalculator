package com.example.wood_calc.repository;

import com.example.wood_calc.models.Wood;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WoodRepository extends JpaRepository<Wood, Integer> {
    List<Wood> findByPriceId(int id);
    List<Wood> findByManufacturerId(int id);
   List<Wood> findByTypeId(int id);
}
