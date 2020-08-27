package com.hed.nasa.service;

import com.hed.nasa.response.RoverManifest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
ManifestApiService je služba, volající API manifestu a vrací odpověď jako objekt RoverManifest
Tato služba je klíčová proto, aby se slideru přidal maximální počet dní pro jednotlivé vozítka
 */

@Service
public class ManifestAPIService {
    private static final String API_KEY = "0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni";

    public RoverManifest getManifest(String roverType) {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<RoverManifest> response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/manifests/"+ roverType +"/?api_key="+API_KEY,RoverManifest.class);
        return response.getBody();
    }
}
