package com.hed.nasa.service;

import com.hed.nasa.dto.HomeDto;
import com.hed.nasa.response.MarsPhoto;
import com.hed.nasa.response.MarsRoverApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class MarsRoverAPIService {

    private static final String API_KEY = "0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni";
    private Map<String,List<String>> validCameras = new HashMap<>();

    public MarsRoverAPIService()
    {
        validCameras.put("Curiosity", Arrays.asList("FHAZ","RHAZ","MAST","CHEMCAM","MAHLI","MARDI","NAVCAM"));
        validCameras.put("Opportunity", Arrays.asList("FHAZ","RHAZ","NAVCAM","PANCAM","MINITES"));
        validCameras.put("Spirit", Arrays.asList("FHAZ","RHAZ","NAVCAM","PANCAM","MINITES"));
    }

    public MarsRoverApiResponse getRoverData(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<MarsRoverApiResponse> response = null;
        List<MarsPhoto> photos = new ArrayList<>();
        List<String> cameraNames = getNamesOfCameras(homeDto);

        for(String name : cameraNames)
        {
            response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key="+API_KEY+"&camera="+name, MarsRoverApiResponse.class);
            photos.addAll(response.getBody().getPhotos());
        }
        response.getBody().setPhotos(photos);
        return response.getBody();
    }

    public List<String> getNamesOfCameras(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        List<String> cameraNames = new ArrayList<>();
        Method[] methods = HomeDto.class.getMethods();
        for(Method method : methods)
        {
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
}
