package cn.edu.nuc.springbootmybatisdynamicwr.dao.mysql;

import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.springbootmybatisdynamicwr.entity.Test;

public interface TestDao {
    Test findByName(@Param("name") String n) throws Exception;
    void saveTest(Test t) throws Exception;
}
