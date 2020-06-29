package org.gdp.contrast.server.core.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gdp.contrast.server.core.model.GdpEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface GdpMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GdpEntity record);

    int insertSelective(GdpEntity record);

    GdpEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GdpEntity record);

    int updateByPrimaryKey(GdpEntity record);

    /**
     * 批量插入
     * @param gdpEntityList
     * @return
     */
    int batchInsertGdp(List<GdpEntity> gdpEntityList);

    /**
     * 查列表
     * @return
     */
    List<GdpEntity> selectList();

    /**
     * 批量更新
     * @param gdpEntityList
     * @return
     */
    int batchUpdate(List<GdpEntity> gdpEntityList);
}