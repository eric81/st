package com.eudemon.taurus.app.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class JasonUtils {
	private static Logger logger = Logger.getLogger(JasonUtils.class);
	
	public static void writeJasonP(HttpServletRequest request, HttpServletResponse response, Object data){
		try {
			String callback = request.getParameter("callback");
			String jsn = JSON.toJSONString(data);
			String jp = jsn;
			if (null != callback && !callback.equals("")) {
				jp = callback + "(" + jsn + ")";
			}
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(jp);
		} catch (IOException e) {
			logger.error("write jasonp exception, data : " + data, e);
		}
	}
}
