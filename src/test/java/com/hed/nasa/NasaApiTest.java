package com.hed.nasa;

import com.hed.nasa.response.MarsRoverApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class NasaApiTest {
    @Test
    void smallTest() {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<MarsRoverApiResponse> response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni",MarsRoverApiResponse.class);
        System.out.println(response.getBody());
    }
}
