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

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class WoodController {
    private PriceRepository priceRepository;
    private WoodTypeRepository woodTypeRepository;
    private ManufacturerRepository manufacturerRepository;
    private WoodRepository woodRepository;



    @GetMapping("/wood_main")
    public String woodMain(Model model){
        List<Wood> wood = woodRepository.findAll();
        for (Wood w : wood) { w.setCost(w.getValue()*w.getPrice().getPrice());
        }
        model.addAttribute("wood",wood);
        double sum = wood.stream().mapToDouble(Wood::getCost).sum();
        model.addAttribute("sum", sum);
        return "wood_main";
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

    @PostMapping("/add_wood")
    public String addWood(@RequestParam String type, @RequestParam String name,
                          @RequestParam double length, @RequestParam double width,
                            @RequestParam double height,  @RequestParam double quantity, Model model){
        Wood wood = new Wood();
        return verification(type, name, length, width, height, quantity, 0,wood, model);

    }
    @GetMapping("/wood_delete/{id}")
    public String deleteWood(@PathVariable("id") int id) {
        woodRepository.deleteById(id);
        return "redirect:/wood_main";
    }
    @GetMapping("/wood_edit/{id}")
    public String woodEdit(@PathVariable("id") int id, Model model) {
        Optional<Wood> wood = woodRepository.findById(id);
        if (wood.isEmpty()) {
            model.addAttribute("massage", "Позиция с id " + id + " не найдена");
            return "error";
        } else {
            model.addAttribute("el", wood.get());
            return "wood_edit";
        }
    }
    @PostMapping("/wood_update/{id}")
    public String updateWood(@PathVariable("id") int id,
                             @RequestParam String type, @RequestParam String name,
                             @RequestParam double length, @RequestParam double width,
                             @RequestParam double height,  @RequestParam double quantity, Model model) {
        Optional<Wood> w = woodRepository.findById(id);
        Wood wood = w.get();
        return verification(type, name, length, width, height, quantity, id,wood, model);
    }
    private double formater (double namber) {
        return  Double.parseDouble(new DecimalFormat("#0.00")
                .format(namber)
                .replace(',', '.'));}
    private String verification (String type, String name,double length, double width,
                                 double height, double quantity, int id, Wood wood, Model model){
        if(length<=0||width<=0||height<=0||quantity<=0){
            model.addAttribute("massage", "Параметры должны быть положительными числами!");
            return "error";
        }
        List<WoodType> woodType = woodTypeRepository.findByType(type);
        List<Manufacturer> manufacturer = manufacturerRepository.findByName(name);
        List<Price> prices = priceRepository
                .findByTypeId(woodType
                        .stream()
                        .findFirst()
                        .get()
                        .getId())
                .stream()
                .filter(x->x.getManufacturer()
                        .getId()==manufacturer
                        .get(0)
                        .getId()).toList();
        if(prices.size()==0){
            model.addAttribute("massage",
                    "В прайсе отсутствует позиция с задаными породой и производителем!");
            return "error";
        }
        wood.setType(woodType.get(0));
        wood.setManufacturer(manufacturer.get(0));
        wood.setLength(length);
        wood.setWidth(width);
        wood.setHeight(height);
        wood.setQuantity(quantity);
        wood.setPrice(prices.get(0));
        wood.setValue( formater(length*(width/1000)*(height/1000)*wood.getQuantity()));//Длина задается в м, ширина и высота в мм
        wood.setCost(formater(wood.getValue()*wood.getPrice().getPrice()));
        wood.setQuantityVal( formater(1/(length*(width/1000)*(height/1000))));

        woodRepository.save(wood);
        return "redirect:/wood_main";
    }
}
