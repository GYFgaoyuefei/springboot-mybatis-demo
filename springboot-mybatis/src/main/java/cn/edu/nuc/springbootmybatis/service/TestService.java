package cn.edu.nuc.springbootmybatis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nuc.springbootmybatis.dao.mysql.TestDao;
import cn.edu.nuc.springbootmybatis.entity.Test;

@Service
public class TestService{
	private static final Logger logger = LoggerFactory.getLogger(TestService.class); 
	
	@Autowired  
	private TestDao testDao;  
	
	public Test findByName(String name) throws Exception{
		logger.info("findByName.................");
		return testDao.findByName(name);
	}
}