package cn.edu.nuc.springbootmybatismutilds.dao.mysql.ds2;

import java.util.List;

import cn.edu.nuc.springbootmybatismutilds.entity.Demo;

public interface DemoDao {
	
	List<Demo> getDemoList() throws Exception;

}
