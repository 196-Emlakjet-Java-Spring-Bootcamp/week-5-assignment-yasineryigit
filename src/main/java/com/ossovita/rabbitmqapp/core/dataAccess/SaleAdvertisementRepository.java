package com.ossovita.rabbitmqapp.core.dataAccess;

import com.ossovita.rabbitmqapp.core.entities.SaleAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleAdvertisementRepository extends JpaRepository<SaleAdvertisement, Long> {


    List<SaleAdvertisement> findByPriceBetween(int lb, int ub);

    List<SaleAdvertisement> findByTitleContains(String word);

    List<SaleAdvertisement> findByDescriptionContains(String word);

    List<SaleAdvertisement> findByUserUserPk(long userPk);


}
