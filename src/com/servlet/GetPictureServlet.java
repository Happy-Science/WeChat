package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
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
	    request.setCharacterEncoding("utf-8");
	    String fileName = request.getParameter("filename");
	    String fileType = request.getParameter("filetype");
	    String userName = request.getParameter("username");
	    
	    InputStream stream = request.getInputStream();
            String currentFilePath = "D:\\test\\" + userName + "\\" + fileType;
            File file =new File(currentFilePath);    
          //   
          if  (!file .exists()  && !file .isDirectory())      
          {       
              System.out.println("//路径不存在");  
              file.mkdir();    
          } else   
          {  
              System.out.println("//路径存在");  
          }
            FileOutputStream  fileStream = new FileOutputStream(new File(currentFilePath + "\\" + fileName));
            //
            byte[] bytes = new byte[1024];
            int numReadByte = 0;
            while ((numReadByte = stream.read(bytes, 0, 1024)) != -1){ 
                fileStream.write(bytes, 0,numReadByte);
            }
            //
            stream.close();
            fileStream.close();
	}
}
