package com.haishi.admin.cloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IpLocationDTO {

    /**
     * ip
     */
    @JsonProperty("ip")
    private String ip;
    /**
     * countryCode
     */
    @JsonProperty("country_code")
    private String countryCode;
    /**
     * countryName
     */
    @JsonProperty("country_name")
    private String countryName;
    /**
     * regionName
     */
    @JsonProperty("region_name")
    private String regionName;
    /**
     * cityName
     */
    @JsonProperty("city_name")
    private String cityName;
    /**
     * latitude
     */
    @JsonProperty("latitude")
    private Double latitude;
    /**
     * longitude
     */
    @JsonProperty("longitude")
    private Double longitude;
    /**
     * zipCode
     */
    @JsonProperty("zip_code")
    private String zipCode;
    /**
     * timeZone
     */
    @JsonProperty("time_zone")
    private String timeZone;
    /**
     * asn
     */
    @JsonProperty("asn")
    private String asn;
    /**
     * as
     */
    @JsonProperty("as")
    private String as;
    /**
     * isProxy
     */
    @JsonProperty("is_proxy")
    private Boolean isProxy;
}
