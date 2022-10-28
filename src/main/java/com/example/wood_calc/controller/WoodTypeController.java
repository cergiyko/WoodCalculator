package com.example.wood_calc.controller;

import com.example.wood_calc.models.Price;
import com.example.wood_calc.models.Wood;
import com.example.wood_calc.models.WoodType;
import com.example.wood_calc.repository.PriceRepository;
import com.example.wood_calc.repository.WoodRepository;
import com.example.wood_calc.repository.WoodTypeRepository;
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
public class WoodTypeController {

    private WoodTypeRepository woodTypeRepository;
    private PriceRepository priceRepository;
    private WoodRepository woodRepository;


    @GetMapping("/type")
    public String woodTypes(Model model){
        Iterable<WoodType> type = woodTypeRepository.findAll();
        model.addAttribute("type" , type);
        return "type";
    }

    @PostMapping("/add_type")
    public String addWoodType(@RequestParam String type, @RequestParam String info, Model model){
        WoodType woodType = new WoodType();
        return verification(type, info, 0, woodType, model);
    }
    @GetMapping("/type_delete/{id}")
    public String deleteWood(@PathVariable("id") int id) {
        List<Wood> woods = woodRepository.findByTypeId(id);
        if(woods.size()>0){
            woodRepository.deleteAll(woods);
        }
        List<Price> price = priceRepository.findByTypeId(id);
        if(price.size()>0){
            priceRepository.deleteAll(price);}
        woodTypeRepository.deleteById(id);
        return "redirect:/type";
    }
    @GetMapping("/type_edit/{id}")
    public String woodEdit(@PathVariable("id") int id, Model model) {
        Optional<WoodType> woodType = woodTypeRepository.findById(id);
        if (woodType.isEmpty()) {
            model.addAttribute("massage", "Позиция с id " + id + " не найдена");
            return "error";
        } else {
            model.addAttribute("el", woodType.get());
            return "type_edit";
        }
    }
    @PostMapping("/type_update/{id}")
    public String updateType(@PathVariable("id") int id, WoodType wt, Model model) {
        Optional<WoodType> w = woodTypeRepository.findById(id);
        WoodType woodType = w.get();
        return verification(wt.getType(), wt.getInfo(), id, woodType, model);
    }
    private String verification (String type, String info, int id, WoodType woodType, Model model)
    {if(type == "" ||info==""){
        model.addAttribute("massage", "Поля должны быть заполнены!");
        return "/error";
    }
        List<WoodType> wt = woodTypeRepository
                .findAll()
                .stream()
                .filter(x->x.getType().equalsIgnoreCase(type))
                .filter(x->x.getId()!=id)
                .toList();
        if(wt.size()>0){
            model.addAttribute("massage", "Такая порода уже задана!");
            return "/error";
        }
        else {
            woodType.setType(type);
            woodType.setInfo(info);
            woodTypeRepository.save(woodType);
            return "redirect:/type";}
    }
}
