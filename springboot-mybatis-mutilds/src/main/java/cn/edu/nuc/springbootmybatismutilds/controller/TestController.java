package cn.edu.nuc.springbootmybatismutilds.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.nuc.springbootmybatismutilds.entity.Test;
import cn.edu.nuc.springbootmybatismutilds.service.TestService;

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
	
	@RequestMapping("/testJsp")
    public String testJsp(HttpServletRequest request,Model model) {
		String name = request.getParameter("name");
		Test test;
		try {
			test = testService.findByName(name);
		} catch (Exception e) {
			test = new Test();
			logger.error("testJsp error.",e);
		}
		logger.info("testJsp....{}",test.toString());
		
		model.addAttribute("time", new Date());
		model.addAttribute("message", test.getName());
		
        return "index";
    }

}
