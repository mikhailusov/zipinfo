package com.cayuse.zipinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ZipCode {
    private String code;
    private String countryCode;
}
