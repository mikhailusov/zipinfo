package com.cayuse.zipinfo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Service;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@Service
public class GoogleElevationService {

    private final static String API_URL = "https://maps.googleapis.com/maps/api/elevation";
    private final static String API_KEY = "AIzaSyDjrRX0MEPxp4XvIkx4w-iD1emIEgHuvAE";

    private Client client = ClientBuilder.newClient();

    public double getElevation(double lat, double lon) {
        Response response = client
                .target(API_URL)
                .path("json")
                .queryParam("locations", lat + "," + lon)
                .queryParam("key", API_KEY)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        return response.getResults()[0].getElevation();
    }

    @Data
    @XmlRootElement
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Response {
        private Result[] results;
        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Result {
            private double elevation;
        }
    }
}
