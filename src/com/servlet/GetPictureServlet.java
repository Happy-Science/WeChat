package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPictureServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6044071292457679588L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
	    request.setCharacterEncoding("utf-8");//设置参数解码类型，必须和页面中一致
	    String fileName = request.getParameter("filename");
	    String fileType = request.getParameter("filetype");
	    String userName = request.getParameter("username");
	    
	    InputStream stream = request.getInputStream();
            String currentFilePath = "D:\\test\\" + userName + "\\" + fileType;
            File file =new File(currentFilePath);    
          //如果文件夹不存在则创建    
          if  (!file .exists()  && !file .isDirectory())      
          {       
              System.out.println("//不存在");  
              file.mkdir();    
          } else   
          {  
              System.out.println("//目录存在");  
          }
            FileOutputStream  fileStream = new FileOutputStream(new File(currentFilePath + "\\" + fileName));
            //每次读取的1024个字节
            byte[] bytes = new byte[1024];
            int numReadByte = 0;
            while ((numReadByte = stream.read(bytes, 0, 1024)) != -1){ 
                fileStream.write(bytes, 0,numReadByte);
            }
            //关闭流
            stream.close();
            fileStream.close();
	}
}
