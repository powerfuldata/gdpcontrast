package org.gdp.contrast.server.core.model;

import lombok.Data;

import java.util.Date;

@Data
public class CountryEntity {
    private Long id;

    private String cnName;

    private String country;

    private String slug;

    private String iso2;

    private Short recordCount;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Boolean isDelete;

}