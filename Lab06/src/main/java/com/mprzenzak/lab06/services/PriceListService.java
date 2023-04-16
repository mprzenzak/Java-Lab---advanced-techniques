package com.mprzenzak.lab06.services;

import com.mprzenzak.lab06.enums.ServiceType;
import com.mprzenzak.lab06.models.PriceList;
import com.mprzenzak.lab06.repository.PriceListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    public Optional<PriceList> findByServiceType(ServiceType serviceType) {
        return priceListRepository.findByServiceType(serviceType);
    }

    public PriceList setServiceType(PriceList priceList, ServiceType serviceType) {
        priceList.setServiceType(serviceType);
        return priceList;
    }

    public PriceList setAmount(PriceList priceList, double amount) {
        priceList.setAmount(amount);
        return priceList;
    }

    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    public List<PriceList> findAll() {
        return priceListRepository.findAll();
    }

    public List<PriceList> saveAll(List<PriceList> priceLists) {
        return priceListRepository.saveAll(priceLists);
    }
}
