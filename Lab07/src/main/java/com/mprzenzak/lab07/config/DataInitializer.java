package com.mprzenzak.lab07.config;

import com.mprzenzak.lab07.enums.ServiceType;
import com.mprzenzak.lab07.models.PriceList;
import com.mprzenzak.lab07.services.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer {

    private final PriceListService priceListService;

    @Autowired
    public DataInitializer(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initPriceList();
    }

    private void initPriceList() {
        for (ServiceType serviceType : ServiceType.values()) {
            Optional<PriceList> existingPriceList = priceListService.findByServiceType(serviceType);
            if (!existingPriceList.isPresent()) {
                PriceList priceList = new PriceList();
                priceList.setServiceType(serviceType);
                priceList.setAmount(0);
                priceListService.save(priceList);
            }
        }
    }

}
