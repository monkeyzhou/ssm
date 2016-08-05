package com.mvc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mybatis.bean.User;
import mybatis.mapping.UserMapperI;
import mybatis.util.MyBatisUtil;

@Controller
public class RestConstroller {
//	@RequestMapping(value = "/login/{user}", method = RequestMethod.GET)
//	public ModelAndView myMethod(HttpServletRequest request,HttpServletResponse response,
//			@PathVariable("user") String user, ModelMap modelMap) throws Exception {
//		modelMap.put("loginUser", user);
//		return new ModelAndView("/login/hello", modelMap);
//	}
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String registPost() {
		return "/welcome";
	}
	@RequestMapping(value = "get_users.do")
	@ResponseBody
	public Map<String, Object> getAll() {
		SqlSession sqlSession = MyBatisUtil.getSqlSession();
		// 得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
		UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
		// 执行查询操作，将查询结果自动封装成List<User>返回
		List<User> lstUsers = mapper.getAll();
		// 使用SqlSession执行完SQL之后需要关闭SqlSession
		sqlSession.close();
		System.out.println(lstUsers);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", lstUsers);
		map.put("total", 20);
		map.put("success",true);
		return map;
	}
	@RequestMapping(value = "save_user.do")
	@ResponseBody
	public Map<String, Object> save(User user,HttpServletRequest request,
 			HttpServletResponse response,String str) {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		// 得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
		UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
		int add = mapper.add(user);
		// 使用SqlSession执行完SQL之后需要关闭SqlSession
		sqlSession.close();
		Map<String, Object> map = new HashMap<String, Object>();
		if(add==1){
			map.put("success",true);
		}else {
			map.put("success",false);
			map.put("msg","大爷，出错啦");
		}
		return map;
	}
	@RequestMapping(value = "remove_user.do")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,HttpServletResponse response) {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		// 得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
		UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
		String id = request.getParameter("id");
		int result = mapper.deleteById(Integer.parseInt(id));
		// 使用SqlSession执行完SQL之后需要关闭SqlSession
		sqlSession.close();
		Map<String, Object> map = new HashMap<String, Object>();
		if(result==1){
			map.put("success",true);
		}else {
			map.put("success",false);
			map.put("msg","大爷，出错啦");
		}
		return map;
	}
	@RequestMapping(value = "update_user.do")
	@ResponseBody
	public Map<String, Object> update(User user,HttpServletRequest request,HttpServletResponse response) {
		SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
		// 得到UserMapperI接口的实现类对象，UserMapperI接口的实现类对象由sqlSession.getMapper(UserMapperI.class)动态构建出来
		try {
			request.setCharacterEncoding("UTF-8");
			String name = request.getParameter("user");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		UserMapperI mapper = sqlSession.getMapper(UserMapperI.class);
		int result = mapper.update(user);
		// 使用SqlSession执行完SQL之后需要关闭SqlSession
		sqlSession.close();
		Map<String, Object> map = new HashMap<String, Object>();
		if(result==1){
			map.put("success",true);
		}else {
			map.put("success",false);
			map.put("msg","大爷，出错啦");
		}
		return map;
	}
	@RequestMapping(value = "table.do")
	public String table() {
		return "/table";
	}
	@RequestMapping(value = "table1.do")
	public String table1() {
		return "../NewFile.jsp";
	}
}
