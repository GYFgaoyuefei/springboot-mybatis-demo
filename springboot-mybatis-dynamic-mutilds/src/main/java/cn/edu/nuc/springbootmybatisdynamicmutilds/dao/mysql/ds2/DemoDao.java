package cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds2;

import java.util.List;

import cn.edu.nuc.springbootmybatisdynamicmutilds.entity.Demo;

public interface DemoDao {
	
	List<Demo> getDemoList() throws Exception;
	void updateDeomo(Demo demo) throws Exception;

}
