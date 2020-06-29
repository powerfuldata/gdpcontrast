package org.gdp.contrast.server.core;

import org.gdp.contrast.server.core.dao.CountryMapper;
import org.gdp.contrast.server.core.dao.GdpMapper;
import org.gdp.contrast.server.core.model.CountryEntity;
import org.gdp.contrast.server.core.model.GdpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 修正数据，添加国家的中文名
 */
@Component
public class FixDataRunner implements CommandLineRunner {

    @Autowired
    GdpMapper gdpMapper;
    @Autowired
    CountryMapper countryMapper;

    @Override
    public void run(String... args) throws Exception {
        // 查询gdp列表
        // for循环查询
        List<GdpEntity> gdpEntityList = gdpMapper.selectList();
        gdpEntityList.forEach(gdpEntity -> {
            String countryEn = gdpEntity.getCountryEn();
            List<CountryEntity> countryList = countryMapper.selectLikeCountryName(countryEn);
            CountryEntity aCountry = countryMapper.selectByCountryName(countryEn);
            if (null != countryList && countryList.size() > 1 && aCountry != null) {
                // 查出多个结果
                gdpEntity.setCountryCn(aCountry.getCnName());
            }else if (null != countryList && countryList.size() == 1){
                // 只有一个结果
                CountryEntity entity = countryList.get(0);
                gdpEntity.setCountryCn(entity.getCnName());
            }
        });
        gdpMapper.batchUpdate(gdpEntityList);
    }
}
