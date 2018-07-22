package com.cayuse.zipinfo.service;

import com.cayuse.zipinfo.domain.ZipCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Service;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@Service
public class OpenWeatherMapService {

    public static final String API_URL = "http://api.openweathermap.org/data/2.5";
    public static final String APP_ID = "de1004808b7c4ba8e0400efb155f2c85";

    private Client client = ClientBuilder.newClient();

    public OWMResponse getByZip(ZipCode zipCode) {
        return client
                .target(API_URL)
                .path("weather")
                .queryParam("zip", zipCode.getCode() + "," + zipCode.getCountryCode())
                .queryParam("units", "imperial")
                .queryParam("APPID", APP_ID)
                .request(MediaType.APPLICATION_JSON)
                .get(OWMResponse.class);
    }

    @Data
    @XmlRootElement
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OWMResponse {
        private Coord coord;
        private String name;
        private Main main;
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Main {
            private double temp;
        }
        @Data
        public class Coord {
            private double lon;
            private double lat;
        }
    }
}
