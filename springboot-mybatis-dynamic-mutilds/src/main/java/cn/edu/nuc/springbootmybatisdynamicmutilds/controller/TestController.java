package cn.edu.nuc.springbootmybatisdynamicmutilds.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.nuc.springbootmybatisdynamicmutilds.entity.Test;
import cn.edu.nuc.springbootmybatisdynamicmutilds.service.TestService;

@Controller
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class); 
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
		String name = request.getParameter("name");
		Test test = null;
		try {
			test = testService.findByName(name);
		} catch (Exception e) {
			test = new Test();
			logger.error("test error.",e);
		}
		logger.info("test....{}",name);
        return test.toString();
    }
	
	@RequestMapping("/save")
    @ResponseBody
    public String saveTest(HttpServletRequest request) {
		String res = "0";
		String name = request.getParameter("name");
		try {
			testService.saveTest(name);
		} catch (Exception e) {
			res = "1";
			logger.error("save test error.",e);
		}
		logger.info("save test....{}",name);
        return res;
    }
}
