package com.hed.nasa.web;

import com.hed.nasa.dto.HomeDto;
import com.hed.nasa.response.MarsRoverApiResponse;
import com.hed.nasa.response.RoverManifest;
import com.hed.nasa.service.ManifestAPIService;
import com.hed.nasa.service.MarsRoverAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    @Autowired // Spring inicializuje za mě
    private MarsRoverAPIService roverService;
    @Autowired
    private ManifestAPIService manifestService;

    @GetMapping("/")
    public String postHomeView(ModelMap model, HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        if(StringUtils.isEmpty(homeDto.getMarsApiRoverData()))
        {
            homeDto.setMarsApiRoverData("Opportunity");
        }
        RoverManifest manifestData = manifestService.getManifest(homeDto.getMarsApiRoverData());
        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        model.put("maxSol",manifestData.getMaxSol());
        model.put("roverData",roverData);
        model.put("homeDto",homeDto);
        model.put("validCameras",roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));
        return "index";
    }
}
