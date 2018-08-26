package cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds1;

import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.springbootmybatisdynamicmutilds.entity.Test;

public interface TestDao {
    Test findByName(@Param("name") String n);
    void saveTest(Test t) throws Exception;
}
