package com.hed.nasa.service;

import com.hed.nasa.dto.HomeDto;
import com.hed.nasa.repository.PreferencesRepository;
import com.hed.nasa.response.MarsPhoto;
import com.hed.nasa.response.MarsRoverApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
MarsRoverAPIService je služba připojující se na API fotek jednotlivých vozítek v kombinaci s mars dny a kamerami
 */

@Service
public class MarsRoverAPIService {

    private static final String API_KEY = "0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni";
    // Pole validních kamer - dalo by se přenést do databáze
    private Map<String,List<String>> validCameras = new HashMap<>();

    // Připojená databáze s tabulkou preferencí
    @Autowired
    private PreferencesRepository preferencesRepo;

    // Konstruktor služby, který nastaví manuálně kamery jednotlivým vozítkům. Aktuální ke dni 27.8.2020
    // Lepší řešení by bylo to brát přímo z NASA API, jenže takovou bohužel nemají
    public MarsRoverAPIService()
    {
        validCameras.put("Curiosity", Arrays.asList("FHAZ","RHAZ","MAST","CHEMCAM","MAHLI","MARDI","NAVCAM"));
        validCameras.put("Opportunity", Arrays.asList("FHAZ","RHAZ","NAVCAM","PANCAM","MINITES"));
        validCameras.put("Spirit", Arrays.asList("FHAZ","RHAZ","NAVCAM","PANCAM","MINITES"));
    }

    // Metoda která vrací odpověď z Mars Rover API
    public MarsRoverApiResponse getRoverData(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<MarsRoverApiResponse> response = null;
        List<MarsPhoto> photos = new ArrayList<>();
        List<String> cameraNames = getNamesOfCameras(homeDto);

        // Protože nelze zadat všechny validní kamery jako parametr naráz v rámci jednoho requestu
        // Je nutné volat request vícekrát pro jednotlivé kamery a výsledky spojovat
        for(String name : cameraNames)
        {
            response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key="+API_KEY+"&camera="+name, MarsRoverApiResponse.class);
            photos.addAll(response.getBody().getPhotos());
        }
        // V případě že není vybraná žádna kamera se zavolá defaultní API volání bez specifikace kamer - všechny jsou vybrány
        if(response == null)
        {
            response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key="+API_KEY, MarsRoverApiResponse.class);
        }
        else
        {
            response.getBody().setPhotos(photos);
        }
        response.getBody().setPhotos(photos);
        return response.getBody();
    }

    /*
    Metoda, která získá jména validních kamer pro API volání z jména atributů, respektive metod třídy HomeDto
    Jména kamer jsou předána zpět jako List stringů
     */
    public List<String> getNamesOfCameras(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        List<String> cameraNames = new ArrayList<>();
        // Vytvoří se pole metod, které patří třídě HomeDto
        Method[] methods = HomeDto.class.getMethods();
        // For pro jednotlivé metody
        for(Method method : methods)
        {
            // Pokud název metody obsahuje getCamera - jedndá se o kameru - a pokud je kamera označená jako že ji chceme
            if(method.getName().contains("getCamera") && Boolean.TRUE.equals(method.invoke(homeDto)))
            {
                String cameraName = method.getName().substring(9);
                if(validCameras.get(homeDto.getMarsApiRoverData()).contains(cameraName.toUpperCase())) {
                    cameraNames.add(cameraName);
                }
            }
        }
        return cameraNames;
    }

    public Map<String, List<String>> getValidCameras() {
        return validCameras;
    }

    public HomeDto save(HomeDto homeDto) {
        return preferencesRepo.save(homeDto);
    }

    public HomeDto findByUserId(Long userId) {
        return preferencesRepo.findByUserId(userId);
    }
}
