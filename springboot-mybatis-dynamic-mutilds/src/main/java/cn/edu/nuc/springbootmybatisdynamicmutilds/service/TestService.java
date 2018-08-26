package cn.edu.nuc.springbootmybatisdynamicmutilds.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds1.TestDao;
import cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds1.config.DatabaseContextHolder;
import cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds2.DemoDao;
import cn.edu.nuc.springbootmybatisdynamicmutilds.dao.mysql.ds2.config.Ds2DatabaseContextHolder;
import cn.edu.nuc.springbootmybatisdynamicmutilds.entity.Demo;
import cn.edu.nuc.springbootmybatisdynamicmutilds.entity.Test;

@Service
public class TestService{
	private static final Logger logger = LoggerFactory.getLogger(TestService.class); 
	
	@Autowired  
	private TestDao testDao;  
	@Autowired  
	private DemoDao demoDao;  
	
	public Test findByName(String name) throws Exception{
		List<Demo> demoList = demoDao.getDemoList();
		logger.info(demoList.toString());
		return testDao.findByName(name);
	}
	
	public void saveTest(String name) throws Exception{
		logger.info("saveTest...");
		DatabaseContextHolder.setDBKey("ds1_w");
		Test t = new Test();
		t.setName(name);
		testDao.saveTest(t);
		
		
		Ds2DatabaseContextHolder.setDBKey("ds2_w");
		Demo demo = new Demo();
		demo.setTitle(name);
		demo.setDescs(name+",desc");
		demo.setId(2L);
		demoDao.updateDeomo(demo);
	}
}