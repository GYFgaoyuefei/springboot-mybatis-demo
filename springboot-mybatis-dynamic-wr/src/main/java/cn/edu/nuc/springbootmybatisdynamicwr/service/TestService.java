package cn.edu.nuc.springbootmybatisdynamicwr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nuc.springbootmybatisdynamicwr.dao.mysql.TestDao;
import cn.edu.nuc.springbootmybatisdynamicwr.dao.mysql.config.DatabaseContextHolder;
import cn.edu.nuc.springbootmybatisdynamicwr.entity.Test;

@Service
public class TestService{
	private static final Logger logger = LoggerFactory.getLogger(TestService.class); 
	
	@Autowired  
	private TestDao testDao;  
	
	public Test findByName(String name) throws Exception{
		logger.info("findByName...");
		return testDao.findByName(name);
	}
	
	public void saveTest(String name) throws Exception{
		logger.info("saveTest...");
		DatabaseContextHolder.setDBKey("ds1_w");
		Test t = new Test();
		t.setName(name);
		testDao.saveTest(t);
	}
}