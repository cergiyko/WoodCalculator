package com.example.wood_calc.repository;

import com.example.wood_calc.models.WoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WoodTypeRepository extends JpaRepository<WoodType, Integer> {
    List<WoodType> findByType(String type);
}
