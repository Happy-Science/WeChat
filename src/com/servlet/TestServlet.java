package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8955682064741721488L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // TODO Auto-generated method stub
	    response.setContentType("text/html;charset=utf-8");
	    request.setCharacterEncoding("utf-8");//设置参数解码类型，必须和页面中一致
	    String sName=request.getParameter("name");
	    String sPassword=request.getParameter("psw");
	    
	    PrintWriter out = response.getWriter();
	    if(sName.equals("admin")&&sPassword.equals("admin")){
	                out.print(sName+",login success!");
	    }else{
	            out.print(sName+",name or psw error!");
	    }
	   }
	  
	  public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	      //this.doGet(request, response);
		  System.out.println("do Post");
	  }

}
