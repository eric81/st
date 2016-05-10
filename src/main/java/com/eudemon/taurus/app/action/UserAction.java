package com.eudemon.taurus.app.action;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eudemon.taurus.app.common.Log;
import com.eudemon.taurus.app.entity.OperResult;
import com.eudemon.taurus.app.entity.PagedEntity;
import com.eudemon.taurus.app.entity.User;
import com.eudemon.taurus.app.service.UserService;
import com.eudemon.taurus.app.util.JasonUtils;
import com.eudemon.taurus.app.util.RequestUtils;

/**
 * 用户管理Action
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public String query(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String name = request.getParameter("name");
		Log.getDebugLogger().trace("UserAction.query.parameter[name=" + name + "]");
		
		User user = null;
		try {
			user = this.userService.getUserByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("user", user);
		model.addAttribute("userinfo", user == null ? "" : user.toString());
		return "index";
	}
	
	@RequestMapping(value = "detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		int id = RequestUtils.getParameterAsInt(request, "id", 0);
		Log.getDebugLogger().trace("UserAction.detail.parameter[id=" + id + "]");
		
		User user = null;
		try {
			user = this.userService.getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JasonUtils.writeJasonP(request, response, user);
	}
	
	@RequestMapping(value = "list")
	public void list(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		int page = RequestUtils.getParameterAsInt(request, "pg", 1);
		int pageSize = RequestUtils.getParameterAsInt(request, "ps", 10);
		Log.getDebugLogger().trace("UserAction.list.parameter[page=" + page + ",pageSize=" + pageSize + "]");
		if(page < 1){
			page = 1;
		}
		
		PagedEntity<User> rs = null;
		try {
			rs = this.userService.getPagedUserList(page, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JasonUtils.writeJasonP(request, response, rs);
	}
	
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		int id = RequestUtils.getParameterAsInt(request, "id", 0);
		Log.getDebugLogger().trace("UserAction.delete.parameter[id=" + id + "]");
		
		boolean rs = this.userService.delete(id);
		if(rs){
			return "success"; 
		}else{
			return "fail";
		}
	}
	
	@RequestMapping(value = "modify")
	public void modify(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		int id = RequestUtils.getParameterAsInt(request, "id", 0);
		String role = RequestUtils.getParameterAndTrimDecode(request, "role", "utf-8");
		Log.getDebugLogger().trace("UserAction.modify.parameter[id=" + id + " role=" + role + "]");
		
		OperResult or = new OperResult();
		boolean rs = this.userService.modify(id, role);
		if(!rs){
			or.setCode(OperResult.CODE_FAIL);
			or.setMessage("fail");
		}
		
		Log.getDebugLogger().trace("UserAction.modify.parameter[id=" + id + " role=" + role + "] rs=" + or);
		JasonUtils.writeJasonP(request, response, or);
	}
	
	@RequestMapping(value = "uploadPhoto")
	public String uploadPhoto(HttpServletRequest request, @RequestParam("userfile") MultipartFile file, ModelMap model)
			throws Exception {
		int id = RequestUtils.getParameterAsInt(request, "id", 0);
		String view = RequestUtils.getParameterAndTrim(request, "v");
		String name = RequestUtils.getParameterAndTrim(request, "name");
		
		Log.getDebugLogger().trace("UserAction.uploadPhoto.parameter[id=" + id + ",name=" + name + "]");
		
		userService.updatePhoto(id, file);
		
		if(null != view && !view.equals("")){
			return "redirect:" + view;
		}else{
			return "forward:/user/query?name=" + name;
		}
	}
	
	@RequestMapping(value = "viewPhoto")
	public void viewPhoto(HttpServletRequest request, OutputStream responseBodyOut)
			throws Exception {
		int id = RequestUtils.getParameterAsInt(request, "id", 0);
		Log.getDebugLogger().trace("UserAction.viewPhoto.parameter[id=" + id + "]");
		
		try {
			byte[] photo = userService.getPhoto(id);
			if(photo == null){
				photo = new byte[]{};
			}
			responseBodyOut.write(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
