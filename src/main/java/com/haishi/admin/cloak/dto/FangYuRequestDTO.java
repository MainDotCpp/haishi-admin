package com.haishi.admin.cloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FangYuRequestDTO {

    /**
     * userAgent
     */
    @JsonProperty("userAgent")
    private String userAgent;
    /**
     * visitUrl
     */
    @JsonProperty("visitUrl")
    private String visitUrl;
    /**
     * clientIp
     */
    @JsonProperty("clientIp")
    private String clientIp;
    /**
     * clientLanguage
     */
    @JsonProperty("clientLanguage")
    private String clientLanguage;
    /**
     * referer
     */
    @JsonProperty("referer")
    private String referer;
    /**
     * timestamp
     */
    @JsonProperty("timestamp")
    private String timestamp;
    /**
     * sign
     */
    @JsonProperty("sign")
    private String sign;
}
