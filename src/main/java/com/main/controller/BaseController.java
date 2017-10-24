package com.main.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auto.db.AutoFixTable;
/**
 * 测试用例
 * @author 陈富强
 *
 */
@RestController
public class BaseController {
	@Autowired
	private AutoFixTable autoFixTable;
	
	/**
	 * 样例
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/autoFixTable")
	public String code(HttpServletRequest request,Model model){
		//根据注解自动修复表结构
		autoFixTable.run(null);
		return "Show";
	}
}
