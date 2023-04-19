package com.mprzenzak.lab07.repository;

import com.mprzenzak.lab07.models.PriceList;
import com.mprzenzak.lab07.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    Optional<PriceList> findByServiceType(ServiceType serviceType);
}
