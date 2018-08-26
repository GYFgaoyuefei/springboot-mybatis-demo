package cn.edu.nuc.springbootmybatismutilds.dao.mysql.ds1;

import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.springbootmybatismutilds.entity.Test;

public interface TestDao {
	/**
     * 根据名称，查询test信息
     *
     * @param name 名
     */
    Test findByName(@Param("name") String n);
}
