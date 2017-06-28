package com.lei.qian;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;



public class FileUploadServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
//		System.out.println(req.getContentLength()+"---"+req.getContentType()+"---"+req.getContextPath());
		String uploadpath = req.getServletContext().getRealPath("/WEB-INF/upload/");
		File file = new File(uploadpath);
		if(!file.exists() && !file.isDirectory()){
			file.mkdir();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(fac);
		if(ServletFileUpload.isMultipartContent(req)){
			System.out.println("yes,it is by form multiple-data");
			try {
				List<FileItem> list = sfu.parseRequest(new ServletRequestContext(req));
				for(FileItem item : list){
					System.out.println(item.getName()+"\n"+item.getSize()+"\n"+item.getContentType());
					System.out.println(file.canWrite());
					System.out.println(item.getString("UTF-8"));
					Files.write(Paths.get(Paths.get(uploadpath)+"/"+item.getName()),item.get());					
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		InputStream in = new FileInputStream(uploadpath+"test.txt");
		res.setContentType("text/plain");
		res.setHeader("Content-Disposition", "attachment;filename=qianlei.txt");
		res.setHeader("Content-Length", "20");
		byte[] data = new byte[1024];
		while(in.read(data) != -1){
			res.getOutputStream().write(data);
		}
		in.close();
	}

}



//public class UploadHandleServlet extends HttpServlet {
// 
//public void doGet(HttpServletRequest request, HttpServletResponse response)
//throws ServletException, IOException {
////�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
//String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
//File file = new File(savePath);
////�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
//if (!file.exists() && !file.isDirectory()) {
//System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
////����Ŀ¼
//file.mkdir();
//}
////��Ϣ��ʾ
//String message = "";
//try{
////ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
////1������һ��DiskFileItemFactory����
//DiskFileItemFactory factory = new DiskFileItemFactory();
////2������һ���ļ��ϴ�������
//ServletFileUpload upload = new ServletFileUpload(factory);
////����ϴ��ļ�������������
//upload.setHeaderEncoding("UTF-8"); 
////3���ж��ύ�����������Ƿ����ϴ���������
//if(!ServletFileUpload.isMultipartContent(request)){
////���մ�ͳ��ʽ��ȡ����
//return;
//}
////4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
//List<FileItem> list = upload.parseRequest(request);
//for(FileItem item : list){
////���fileitem�з�װ������ͨ�����������
//if(item.isFormField()){
//String name = item.getFieldName();
////�����ͨ����������ݵ�������������
//String value = item.getString("UTF-8");
////value = new String(value.getBytes("iso8859-1"),"UTF-8");
//System.out.println(name + "=" + value);
//}else{//���fileitem�з�װ�����ϴ��ļ�
////�õ��ϴ����ļ����ƣ�
//String filename = item.getName();
//System.out.println(filename);
//if(filename==null || filename.trim().equals("")){
//continue;
//}
////ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺 c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
////�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
//filename = filename.substring(filename.lastIndexOf("\\")+1);
////��ȡitem�е��ϴ��ļ���������
//InputStream in = item.getInputStream();
////����һ���ļ������
//FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
////����һ��������
//byte buffer[] = new byte[1024];
////�ж��������е������Ƿ��Ѿ�����ı�ʶ
//int len = 0;
////ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
//while((len=in.read(buffer))>0){
////ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
//out.write(buffer, 0, len);
//}
////�ر�������
//in.close();
////�ر������
//out.close();
////ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
//item.delete();
//message = "�ļ��ϴ��ɹ���";
//}
//}
//}catch (Exception e) {
//message= "�ļ��ϴ�ʧ�ܣ�";
//e.printStackTrace();
// 
//}
//request.setAttribute("message",message);
//request.getRequestDispatcher("/message.jsp").forward(request, response);
//}
// 
//public void doPost(HttpServletRequest request, HttpServletResponse response)
//throws ServletException, IOException {
// 
//doGet(request, response);
//}
//}