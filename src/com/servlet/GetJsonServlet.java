package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class GetJsonServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6301769894157918858L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // TODO Auto-generated method stub
	    response.setContentType("text/html;charset=utf-8");
	    request.setCharacterEncoding("utf-8");//设置参数解码类型，必须和页面中一致
	    String sid = request.getParameter("id");
	    //编辑静态json数据
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("id", "2001");
	    map.put("name", "xiaoming");
	    map.put("age", "21");
	    map.put("phone", "123456-123");
	    map.put("address", "kunming");
	    JSONObject json = JSONObject.fromObject(map);
	    PrintWriter out = response.getWriter();
	    if(sid.equals("1")){
		    System.out.println("server_json:"+json);
	    	out.print(json);
	    }

	}
	  
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		this.doGet(request, response);
	}
}
