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
	    request.setCharacterEncoding("utf-8");//���ò����������ͣ������ҳ����һ��
	    String fileName = request.getParameter("filename");
	    String fileType = request.getParameter("filetype");
	    String userName = request.getParameter("username");
	    
	    InputStream stream = request.getInputStream();
            String currentFilePath = "D:\\test\\" + userName + "\\" + fileType;
            File file =new File(currentFilePath);    
          //����ļ��в������򴴽�    
          if  (!file .exists()  && !file .isDirectory())      
          {       
              System.out.println("//������");  
              file.mkdir();    
          } else   
          {  
              System.out.println("//Ŀ¼����");  
          }
            FileOutputStream  fileStream = new FileOutputStream(new File(currentFilePath + "\\" + fileName));
            //ÿ�ζ�ȡ��1024���ֽ�
            byte[] bytes = new byte[1024];
            int numReadByte = 0;
            while ((numReadByte = stream.read(bytes, 0, 1024)) != -1){ 
                fileStream.write(bytes, 0,numReadByte);
            }
            //�ر���
            stream.close();
            fileStream.close();
	}
}
