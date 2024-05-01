package com.haishi.admin.cloak.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FangYuResponseDTO {

    /**
     * code
     */
    @JsonProperty("code")
    private Integer code;
    /**
     * message
     */
    @JsonProperty("message")
    private String message;
    /**
     * data
     */
    @JsonProperty("data")
    private DataDTO data;
    /**
     * success
     */
    @JsonProperty("success")
    private Boolean success;

    /**
     * DataDTO
     */
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * status
         */
        @JsonProperty("status")
        private Boolean status;
        /**
         * message
         */
        @JsonProperty("message")
        private String message;
        /**
         * jump
         */
        @JsonProperty("jump")
        private String jump;
        /**
         * errorCode
         */
        @JsonProperty("errorCode")
        private String errorCode;
    }
}
