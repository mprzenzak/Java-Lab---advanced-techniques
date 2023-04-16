package com.mprzenzak.lab06.controllers;

import com.mprzenzak.lab06.models.PriceList;
import com.mprzenzak.lab06.models.PriceListWrapper;
import com.mprzenzak.lab06.services.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @GetMapping("/pricelist")
    public String showPriceList(Model model) {
        List<PriceList> prices = priceListService.findAll();
        model.addAttribute("prices", prices);
        return "pricelist";
    }

    @PostMapping("/pricelist/save")
    public String savePriceList(@ModelAttribute("prices") PriceListWrapper priceListWrapper) {
        List<PriceList> prices = priceListWrapper.getPrices();
        priceListService.saveAll(prices);
        return "redirect:/pricelist";
    }

}

