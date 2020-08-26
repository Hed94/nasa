package com.hed.nasa.service;

import com.hed.nasa.response.MarsRoverApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarsRoverAPIService {
    public MarsRoverApiResponse getRoverData(String roverType) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<MarsRoverApiResponse> response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/"+ roverType +"/photos?sol=1000&api_key=0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni",MarsRoverApiResponse.class);
        return response.getBody();
    }
}
