package com.haishi.admin.resource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GoddyDomainDTO {

    /**
     * createdAt
     */
    private String createdAt;
    /**
     * domain
     */
    private String domain;
    /**
     * domainId
     */
    private Integer domainId;
    /**
     * expirationProtected
     */
    private Boolean expirationProtected;
    /**
     * expires
     */
    private String expires;
    /**
     * exposeWhois
     */
    private Boolean exposeWhois;
    /**
     * holdRegistrar
     */
    private Boolean holdRegistrar;
    /**
     * locked
     */
    private Boolean locked;
    /**
     * nameServers
     */
    private Object nameServers;
    /**
     * privacy
     */
    private Boolean privacy;
    /**
     * registrarCreatedAt
     */
    private String registrarCreatedAt;
    /**
     * renewAuto
     */
    private Boolean renewAuto;
    /**
     * renewDeadline
     */
    private String renewDeadline;
    /**
     * renewable
     */
    private Boolean renewable;
    /**
     * status
     */
    private String status;
    /**
     * transferProtected
     */
    private Boolean transferProtected;
}
