package org.gdp.contrast.server.core.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gdp.contrast.server.core.model.CountryEntity;

import java.util.List;

@Mapper
public interface CountryMapper {
    CountryEntity selectByPrimaryKey(Long id);

    /**
     * 根据国家明只能查出一个
     * @param name
     * @return
     */
    CountryEntity selectByCountryName(String name);

    /**
     * 根据国家名称匹配，有可能查出多个
     * @param name
     * @return
     */
    List<CountryEntity> selectLikeCountryName(String name);


}