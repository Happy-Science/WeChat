package com.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.JDOMException;

import AnalysisWechatData.PushManage;

public class WeChat extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * 微信公众平台 所有接口入口 
	 *  
	 * @param request 
	 *            the request send by the client to the server 
	 * @param response 
	 *            the response send by the server to the client 
	 * @throws ServletException 
	 *             if an error occurred 
	 * @throws IOException 
	 *             if an error occurred 
	 */  
	 public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
	    response.setCharacterEncoding("UTF-8");  
	    request.setCharacterEncoding("UTF-8");  
	    PrintWriter out = response.getWriter();  
	    try {  
	        InputStream is = request.getInputStream();  
	        PushManage push = new PushManage();  
	        String getXml = push.PushManageXml(is);  
	        out.print(getXml);  
	    } catch (JDOMException e) {  
	        e.printStackTrace();  
	        out.print("");  
	    } catch (Exception e) {  
	        out.print("");  
	    } finally {   
	        if(out!=null) {   
	            out.flush();  
	            out.close();  
	        }  
	    }  
	} 
}
