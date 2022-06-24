package com.ossovita.rabbitmqapp.business.concretes;

import com.ossovita.rabbitmqapp.business.abstracts.SaleAdvertisementService;
import com.ossovita.rabbitmqapp.core.dataAccess.SaleAdvertisementRepository;
import com.ossovita.rabbitmqapp.core.entities.SaleAdvertisement;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SaleAdvertisementManager implements SaleAdvertisementService {
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;
    private SaleAdvertisementRepository saleAdvertisementRepository;
    private UserManager userManager;
    Random random;

    public SaleAdvertisementManager(RabbitTemplate rabbitTemplate, DirectExchange directExchange, SaleAdvertisementRepository saleAdvertisementRepository, UserManager userManager, Random random) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.saleAdvertisementRepository = saleAdvertisementRepository;
        this.userManager = userManager;
        this.random = random;
    }

    @Override
    public void createDummySaleAdvertisementRequest(int piece) {
        rabbitTemplate.convertAndSend(directExchange.getName(), "saleAdvertisementRouting", piece);
    }

    @Override
    public void createDummySaleAdvertisement(int piece) {

        for (int i = 0; i < piece; i++) {
            String title = getRandomFirstWordForTitle() + " " + getRandomSecondWordForTitle();
            String description = getRandomDescription();
            long userPk = userManager.getRandomUserPk();
            SaleAdvertisement saleAdvertisement = SaleAdvertisement.builder()
                    .title(title)
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .price(random.nextInt(300000))
                    .userFk(userPk)
                    .build();
            saleAdvertisementRepository.save(saleAdvertisement);
        }
    }

    @Override
    public List<SaleAdvertisement> getSaleAdvertisementByPrice(int lb, int ub) {
        return saleAdvertisementRepository.findByPriceBetween(lb, ub);

    }

    @Override
    public List<SaleAdvertisement> getByWord(String word) {
        List<SaleAdvertisement> saleAdvertisementList = new ArrayList<>();
        saleAdvertisementList.addAll(saleAdvertisementRepository.findByTitleContains(word));
        saleAdvertisementList.addAll(saleAdvertisementRepository.findByDescriptionContains(word));
        //TODO unique result will be implemented.
        return saleAdvertisementList;
    }

    @Override
    public List<SaleAdvertisement> findLatestByUserPkAndPiece(long userPk, int piece) {
        List<SaleAdvertisement> saleAdvertisementList = saleAdvertisementRepository.findByUserUserPk(userPk);
        //TODO refactor

        saleAdvertisementList.stream().sorted(Comparator.comparing(SaleAdvertisement::getCreatedAt)).collect(Collectors.toList()).stream().limit(piece);
        return saleAdvertisementList;

    }

    private String getRandomFirstWordForTitle() {
        List<String> firstWords = List.of("Kiralık", "Temiz", "Doktordan", "İhtiyaçtan satılık");
        return firstWords.get(random.nextInt(firstWords.size()));
    }

    private String getRandomSecondWordForTitle() {
        List<String> secondWords = List.of("Ev", "Araba", "Villa", "Arsa");
        return secondWords.get(random.nextInt(secondWords.size()));
    }

    private String getRandomDescription() {
        List<String> descriptions = List.of("İhtiyaçtan satılık", "Asansörlü", "Kombili", "Fiber altyapısı var", "Güven Emlak", "Aktaş Emlak", "Kalite güven bizim işimiz");
        List<String> selectedDescriptionList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {//daha önce seçilmeyen description'lardan 3 tane seç
            String selected = descriptions.get(random.nextInt(descriptions.size()));
            if (!selectedDescriptionList.contains(selected)) {
                selectedDescriptionList.add(selected);
            }
        }
        return String.join("...", selectedDescriptionList);
    }

}
