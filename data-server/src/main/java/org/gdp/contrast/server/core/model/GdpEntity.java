package org.gdp.contrast.server.core.model;

import lombok.Data;

import java.util.Date;

@Data
public class GdpEntity {
    private Long id;

    private String countryEn;

    private String countryCn;

    private Long gdp;

    private String continentEn;

    private String continentCn;

    private Integer year;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Boolean isDelete;

}