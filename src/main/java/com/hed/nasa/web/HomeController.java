package com.hed.nasa.web;

import com.hed.nasa.response.MarsRoverApiResponse;
import com.hed.nasa.response.RoverManifest;
import com.hed.nasa.service.ManifestAPIService;
import com.hed.nasa.service.MarsRoverAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
public class HomeController {

    @Autowired // Spring inicializuje za mě
    private MarsRoverAPIService roverService;
    @Autowired
    private ManifestAPIService manifestService;

    @GetMapping("/")
    public String postHomeView(ModelMap model, @RequestParam(required = false) String marsApiRoverData)
    {
        if(StringUtils.isEmpty(marsApiRoverData))
        {
            marsApiRoverData ="opportunity";
        }
        RoverManifest manifestData = manifestService.getManifest(marsApiRoverData);
        MarsRoverApiResponse roverData = roverService.getRoverData(marsApiRoverData);
        model.put("maxSol",manifestData.getMaxSol());
        model.put("roverData",roverData);
        return "index";
    }
}
