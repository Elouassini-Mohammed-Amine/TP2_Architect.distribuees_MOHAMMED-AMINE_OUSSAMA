package com.tp2.demo.service;
import com.tp2.demo.dto.EtudiantDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EtudiantClient {

    private final RestTemplate rest = new RestTemplate();

    @Value("${etudiants.service.url}")
    private String baseUrl;

    public EtudiantDto getEtudiantById(int id) {

        String url = baseUrl + "/etudiants/" + id;

        return rest.getForObject(
                url,
                EtudiantDto.class
        );
    }
}
