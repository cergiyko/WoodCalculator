package com.example.wood_calc.controller;

import com.example.wood_calc.models.Manufacturer;
import com.example.wood_calc.models.Price;
import com.example.wood_calc.models.Wood;
import com.example.wood_calc.repository.ManufacturerRepository;
import com.example.wood_calc.repository.PriceRepository;
import com.example.wood_calc.repository.WoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ManufacturerController {
    private ManufacturerRepository manufacturerRepository;
    private PriceRepository priceRepository;
    private WoodRepository woodRepository;

    @GetMapping("/manufacturer")
    public String woodTypes(Model model){
        Iterable<Manufacturer> manufacturer = manufacturerRepository.findAll();
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer";
    }

    @PostMapping("/add_manufacturer")
    public String addManufacturer(@RequestParam String name, Model model){
        Manufacturer manufacturer = new Manufacturer();
       return verification(name, 0 , manufacturer, model);
    }
    @GetMapping("/manufacturer_delete/{id}")
    public String deleteWood(@PathVariable("id") int id) {

        List<Wood> woods = woodRepository.findByManufacturerId(id);
        if(woods.size()>=0){
            woodRepository.deleteAll(woods);
        }
        List<Price> price = priceRepository.findByManufacturerId(id);
        if(price.size()>=0){
            priceRepository.deleteAll(price);}
        manufacturerRepository.deleteById(id);
        return "redirect:/manufacturer";
    }

    @GetMapping("/manufacturer_edit/{id}")
    public String manufacturerEdit(@PathVariable("id") int id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isEmpty()) {
            model.addAttribute("massage", "Позиция с id " + id + " не найдена");
            return "error";
        } else {
            model.addAttribute("el", manufacturer.get());
            return "manufacturer_edit";
        }
    }
    @PostMapping("/manufacturer_update/{id}")
    public String updateGroup(@PathVariable("id") int id, Manufacturer manufacturer, Model model) {
        Optional<Manufacturer> m = manufacturerRepository.findById(id);
        Manufacturer mo = m.get();
        return  verification(manufacturer.getName(), id, mo, model);
    }
    private String verification (String name, int id, Manufacturer manufacturer, Model model){
     if(name == ""){
        model.addAttribute("massage", "Поле должно быть заполнено!");
        return "/error";
    }
    List<Manufacturer> manufacturers = manufacturerRepository
            .findAll()
            .stream()
            .filter(x->x.getName().equalsIgnoreCase(name))
            .filter(x->x.getId()!=id)
            .toList();
        if (manufacturers.size()>0){
        model.addAttribute("massage", "Производитель с таким именем уже существует!");
        return "/error";
    }else
    {
        manufacturer.setName(name);
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturer";}
}}
