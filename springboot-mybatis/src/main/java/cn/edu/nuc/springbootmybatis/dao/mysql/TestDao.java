package cn.edu.nuc.springbootmybatis.dao.mysql;

import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.springbootmybatis.entity.Test;

public interface TestDao {
    Test findByName(@Param("name") String n);
}
