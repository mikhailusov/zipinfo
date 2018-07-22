package com.cayuse.zipinfo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZipInfo {
    private ZipCode zipCode;
    private String cityName;
    private double temperature;
    private String timeZone;
    private double elevation;
}
