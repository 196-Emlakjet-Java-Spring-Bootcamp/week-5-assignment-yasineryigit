package com.ossovita.rabbitmqapp.controllers;

import com.ossovita.rabbitmqapp.business.abstracts.SaleAdvertisementService;
import com.ossovita.rabbitmqapp.core.entities.SaleAdvertisement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/1.0")
public class SaleAdvertisementController {

    private SaleAdvertisementService saleAdvertisementService;


    public SaleAdvertisementController(SaleAdvertisementService saleAdvertisementService) {
        this.saleAdvertisementService = saleAdvertisementService;

    }

    @GetMapping("/sale-advertisement/create-dummy-sale-advertisement-request")
    public ResponseEntity<String> createDummySaleAdvertisementRequest(@RequestParam int piece) {
        saleAdvertisementService.createDummySaleAdvertisementRequest(piece);
        return ResponseEntity.ok("Your request has been added to queue");
    }

    @GetMapping("/sale-advertisement/by-price")
    public List<SaleAdvertisement> getSaleAdvertisementByPrice(@RequestParam int lb, @RequestParam int ub) {//upper bound lower bound parameterssa
        return saleAdvertisementService.getSaleAdvertisementByPrice(lb, ub);
    }

    @GetMapping("/sale-advertisement/by-word")
    public List<SaleAdvertisement> getByWord(@RequestParam String word) {
        return saleAdvertisementService.getByWord(word);
    }

    @GetMapping("/sale-advertisement/find-latest-by-userpk-and-piece")
    public List<SaleAdvertisement> findLatestByUserPkAndPiece(@RequestParam long userPk, @RequestParam int piece) {
        return saleAdvertisementService.findLatestByUserPkAndPiece(userPk, piece);

    }

}
