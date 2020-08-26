package com.hed.nasa.service;

import com.hed.nasa.dto.HomeDto;
import com.hed.nasa.response.MarsPhoto;
import com.hed.nasa.response.MarsRoverApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarsRoverAPIService {

    private static final String API_KEY = "0rIHBZwB4gTtdMSrlMpfFaEqniVCFXSofHCQ8kni";

    public MarsRoverApiResponse getRoverData(HomeDto homeDto) {
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

    public List<String> getNamesOfCameras(HomeDto homeDto)
    {
        List<String> cameraNames = new ArrayList<>();
        Method[] methods = HomeDto.class.getMethods();
        for(Method method : methods)
        {
            if(method.getName().contains("getCamera"))
            {
                cameraNames.add(method.getName().substring(9));
            }
        }
        return cameraNames;
    }
}
