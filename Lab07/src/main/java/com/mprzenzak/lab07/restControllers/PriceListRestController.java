package com.mprzenzak.lab07.restControllers;

import com.mprzenzak.lab07.models.PriceList;
import com.mprzenzak.lab07.models.PriceListWrapper;
import com.mprzenzak.lab07.services.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricelist")
public class PriceListRestController {

    @Autowired
    private PriceListService priceListService;

    @GetMapping
    public ResponseEntity<List<PriceList>> showPriceList() {
        List<PriceList> prices = priceListService.findAll();
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> savePriceList(@RequestBody PriceListWrapper priceListWrapper) {
        List<PriceList> prices = priceListWrapper.getPrices();
        priceListService.saveAll(prices);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
