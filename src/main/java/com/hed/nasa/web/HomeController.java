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
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

/*
Hlavní kontroler mapující jednotlivé end pointy této aplikace
Existují zde tři
První end point - @GetMapping("/") nastavuje HTML elementy modelem, který aplikace vytvoří
Druhý end point - @GetMapping("/savedPreferences") neslouží prozatím ničemu, ale vrací JSON hodnoty uložených preferencí pro AJAX
Třetí end point - @PostMapping("/") slouží pro změnu nastavení toho co chce uživatel vidět a redirectuje zpět na get end point
 */

@Controller
public class HomeController {

    @Autowired // Spring inicializuje za mě
    private MarsRoverAPIService roverService;
    @Autowired
    private ManifestAPIService manifestService;

    /*
    End point mapující HTML elementy modelem který aplikace vytvoří. Využívá atributy userId a createUser
    Tyto atributy slouží k naplnění modelu daty podle jednotlivého uživatele a createUser je příznak pro to že uživatel neexistuje vůbec
     */
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

        // Služby žerou data z Apíček
        RoverManifest manifestData = manifestService.getManifest(homeDto.getMarsApiRoverData());
        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        // Nastavení jednotlivých důležitých věcí v modelu
        // O nastavení se pak stará Thymeleaf v kombinaci s Javascriptem
        model.put("maxSol",manifestData.getMaxSol());
        model.put("roverData",roverData);
        model.put("homeDto",homeDto);
        model.put("validCameras",roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));

        // V případě že uživatel nechce mít uloženou preferenci toho co si naklikal
        if(!homeDto.getRememberPreferences() && userId != null)
        {
            HomeDto defaultHomeDto = createDefaultHomeDto(userId);
            roverService.save(defaultHomeDto);
        }

        return "index";
    }

    // End point pro Ajax, který by měl brát odsud data, aby na prvotním end pointu nastavil data uživateli
    // Který na stránku přišel klikem na index a ne skrze své userID ( není v URL jako parametr )
    // Zatím k ničemu, protože mnohem jednoduší řešení je refreshovat v tom případě celé okno
    @GetMapping("/savedPreferences")
    @ResponseBody
    public HomeDto getSavedPreferences (Long userId){
        if(userId != null)
        {
            return roverService.findByUserId(userId);
        }
        else
        {
            return createDefaultHomeDto(userId);
        }
    }

    // End point, kterej upravuje data uživatele podle toho co si naklikal a redirektne ho na get s jeho userId
    @PostMapping("/")
    public String postHomeView(HomeDto homeDto) {
        homeDto = roverService.save(homeDto);
        return "redirect:/?userId="+homeDto.getUserId();
    }

    //Metoda, která vytváří defaultní HomeDto v případě, že uživatel neexistuje
    private HomeDto createDefaultHomeDto(Long userId) {
        HomeDto homeDto = new HomeDto();
        homeDto.setUserId(userId);
        if (StringUtils.isEmpty(homeDto.getMarsApiRoverData())) {
            homeDto.setMarsApiRoverData("Opportunity");
        }
        return homeDto;
    }
}
