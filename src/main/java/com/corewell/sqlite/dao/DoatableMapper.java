package com.corewell.sqlite.dao;

import com.corewell.sqlite.domain.Doatable;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 863586395
 */
@Mapper
public interface DoatableMapper {
    /**
     * @return
     *
     *查询最新一条数据
     *
     *
    * */
    Doatable findOne();
}
