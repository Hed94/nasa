package com.hed.nasa.service;

import com.hed.nasa.response.RoverManifest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ManifestAPIService {
    public RoverManifest getManifest(String roverType) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<RoverManifest> response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/manifests/"+ roverType +"/?api_key=0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni",RoverManifest.class);
        return response.getBody();
    }
}
