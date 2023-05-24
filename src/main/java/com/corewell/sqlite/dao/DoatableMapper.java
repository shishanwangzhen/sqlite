package com.corewell.sqlite.dao;

import com.corewell.sqlite.domain.Doatable;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 863586395
 */
@Mapper
public interface DoatableMapper {
    /**
     * 查询最新一条数据
     * @return
     *
     *
    * */
    Doatable findOne();
}
