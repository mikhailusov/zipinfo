package com.cayuse.zipinfo.service;

import com.cayuse.zipinfo.domain.ZipCode;
import com.cayuse.zipinfo.domain.ZipInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;

@Service
public class ZipInfoService {

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @Autowired
    private GoogleTimeZoneService googleTimeZoneService;

    @Autowired
    private GoogleElevationService googleElevationService;

    public ZipInfo getInfo(ZipCode zipCode) throws NotFoundException {
        ZipInfo zipInfo = ZipInfo.builder()
                .zipCode(zipCode)
                .build();

        OpenWeatherMapService.OWMResponse owmResponse = openWeatherMapService.getByZip(zipCode);

        String timeZone = googleTimeZoneService.getTimeZoneName(owmResponse.getCoord().getLat(), owmResponse.getCoord().getLon());

        double elevation = googleElevationService.getElevation(owmResponse.getCoord().getLat(), owmResponse.getCoord().getLon());

        zipInfo.setCityName(owmResponse.getName());
        zipInfo.setTemperature(owmResponse.getMain().getTemp());
        zipInfo.setTimeZone(timeZone);
        zipInfo.setElevation(elevation);

        return zipInfo;
    }
}
