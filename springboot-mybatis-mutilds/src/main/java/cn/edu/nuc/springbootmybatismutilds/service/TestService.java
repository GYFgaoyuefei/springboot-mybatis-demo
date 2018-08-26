package cn.edu.nuc.springbootmybatismutilds.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nuc.springbootmybatismutilds.dao.mysql.ds1.TestDao;
import cn.edu.nuc.springbootmybatismutilds.dao.mysql.ds2.DemoDao;
import cn.edu.nuc.springbootmybatismutilds.entity.Demo;
import cn.edu.nuc.springbootmybatismutilds.entity.Test;

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
	
	
}