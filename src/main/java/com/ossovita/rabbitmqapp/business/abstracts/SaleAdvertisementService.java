package com.ossovita.rabbitmqapp.business.abstracts;


import com.ossovita.rabbitmqapp.core.entities.SaleAdvertisement;

import java.util.List;

public interface SaleAdvertisementService {

    void createDummySaleAdvertisementRequest(int piece);

    void createDummySaleAdvertisement(int piece);

    List<SaleAdvertisement> getSaleAdvertisementByPrice(int lb, int ub);


    List<SaleAdvertisement> getByWord(String word);

    List<SaleAdvertisement> findLatestByUserPkAndPiece(long userPk, int piece);


}
