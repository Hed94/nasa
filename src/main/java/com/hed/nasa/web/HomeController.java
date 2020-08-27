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
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    @Autowired // Spring inicializuje za mě
    private MarsRoverAPIService roverService;
    @Autowired
    private ManifestAPIService manifestService;

    @GetMapping("/")
    public String getHomeView(ModelMap model,Long userId, Boolean createUser) throws InvocationTargetException, IllegalAccessException {
        HomeDto homeDto = createDefaultHomeDto(userId);

        if(userId == null && Boolean.TRUE.equals(createUser))
        {
            homeDto = roverService.save(homeDto);
        }
        else
        {
            homeDto = roverService.findByUserId(userId);
            if(homeDto == null)
            {
                homeDto = createDefaultHomeDto(userId);
            }
        }

        RoverManifest manifestData = manifestService.getManifest(homeDto.getMarsApiRoverData());
        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        model.put("maxSol",manifestData.getMaxSol());
        model.put("roverData",roverData);
        model.put("homeDto",homeDto);
        model.put("validCameras",roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));

        if(!homeDto.getRememberPreferences() && userId != null)
        {
            HomeDto defaultHomeDto = createDefaultHomeDto(userId);
            roverService.save(defaultHomeDto);
        }

        return "index";
    }

    private HomeDto createDefaultHomeDto(Long userId) {
        HomeDto homeDto = new HomeDto();
        homeDto.setUserId(userId);
        if (StringUtils.isEmpty(homeDto.getMarsApiRoverData())) {
            homeDto.setMarsApiRoverData("Opportunity");
        }
        return homeDto;
    }

    @PostMapping("/")
    public String postHomeView(HomeDto homeDto) {
        homeDto = roverService.save(homeDto);
        return "redirect:/?userId="+homeDto.getUserId();
    }
}
