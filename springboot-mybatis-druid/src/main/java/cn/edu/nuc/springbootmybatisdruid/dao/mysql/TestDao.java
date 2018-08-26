package cn.edu.nuc.springbootmybatisdruid.dao.mysql;

import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.springbootmybatisdruid.entity.Test;

public interface TestDao {
    Test findByName(@Param("name") String n);
}
