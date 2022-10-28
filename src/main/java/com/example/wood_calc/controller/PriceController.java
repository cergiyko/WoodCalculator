package com.example.wood_calc.controller;

import com.example.wood_calc.models.Manufacturer;
import com.example.wood_calc.models.Price;
import com.example.wood_calc.models.Wood;
import com.example.wood_calc.models.WoodType;
import com.example.wood_calc.repository.ManufacturerRepository;
import com.example.wood_calc.repository.PriceRepository;
import com.example.wood_calc.repository.WoodRepository;
import com.example.wood_calc.repository.WoodTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
public class PriceController {
    private PriceRepository priceRepository;
    private WoodTypeRepository woodTypeRepository;
    private ManufacturerRepository manufacturerRepository;
    private WoodRepository woodRepository;

    @GetMapping ("/price")
    public String priceList(Model model){
        Iterable<Price> price = priceRepository.findAll();
        model.addAttribute("price", price);
        return "price";
    }
    @ModelAttribute("/type")
    public String typesAtr(Model model) {
            Iterable<WoodType> wt = woodTypeRepository.findAll();
            model.addAttribute("types", wt);
            return "types";
    }
    @ModelAttribute("/manufacturer")
    public String manufacturerAtr(Model model) {
        Iterable<Manufacturer> m = manufacturerRepository.findAll();
        model.addAttribute("manufacturers", m);
        return "manufacturers";
    }

    @PostMapping("/add_price")
    public String addPrice(@RequestParam String type, @RequestParam String name,
                           @RequestParam double price, Model model){
        Price p = new Price();
       return verification(name, type, price, p,0, model);
        }
    @GetMapping("/price_delete/{id}")
    public String deleteWood(@PathVariable("id") int id) {
        List<Wood> woods = woodRepository.findByPriceId(id);
        if(woods.size()>=0){
            woodRepository.deleteAll(woods);}
        priceRepository.deleteById(id);
        return "redirect:/price";
    }
    @GetMapping("/price_edit/{id}")
    public String priceEdit(@PathVariable("id") int id, Model model) {
        Optional<Price> price = priceRepository.findById(id);
        if (price.isEmpty()) {
            model.addAttribute("massage", "Позиция с id " + id + " не найдена");
            return "error";
        } else {
            model.addAttribute("el", price.get());
            return "price_edit";
        }
    }
    @PostMapping("/price_update/{id}")
    public String updateWood(@PathVariable("id") int id,
                             @RequestParam String type, @RequestParam String name,
                             @RequestParam double price, Model model) {
        Optional<Price> p = priceRepository.findById(id);
        Price pr = p.get();
        return verification(name,type,price,pr, id, model);
    }
    private String verification (String name, String type,double price, Price p, int id, Model model){
        if(price<=0){
            model.addAttribute("massage", "Цена не может быть отрицательной!");
            return "error";
        }
        List<WoodType> woodType = woodTypeRepository.findByType(type);
        List<Manufacturer> manufacturer = manufacturerRepository.findByName(name);
        if(manufacturer.isEmpty()||woodType.isEmpty()){
            model.addAttribute("massage", "Заданных параметров не найдено!");
            return "error";
        }
        List<Price> prices = priceRepository
                .findAll()
                .stream()
                .filter(x->x.getManufacturer().getName().equalsIgnoreCase(name))
                .filter(x->x.getType().getType().equalsIgnoreCase(type))
                .filter(x->x.getId()!=id)
                .toList();
        if (prices.size()>0){
            model.addAttribute("massage", "Указаная позиция уже есть в прайсе!");
            return "error";
        }
        else {
            p.setType(woodType.get(0));
            p.setManufacturer(manufacturer.get(0));
            p.setPrice(price);
            priceRepository.save(p);
            return "redirect:/price";
        }
    }}


