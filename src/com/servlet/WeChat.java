package com.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom2.JDOMException;
import AnalysisWechatData.PushManage;
import Utils.SHA1;

public class WeChat extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TOKEN = "WeChatServer"; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
		String signature = request.getParameter("signature");        // 随机字符串
        String echostr = request.getParameter("echostr");        // 时间戳
        String timestamp = request.getParameter("timestamp");        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = { TOKEN, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];        // SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();        
        // 确认请求来至微信
        if (digest.equals(signature)) {
            response.getWriter().print(echostr);
        }  
    }
	
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
