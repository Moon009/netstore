package com.cqut.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.cqut.commons.Page;
import com.cqut.domain.Book;
import com.cqut.domain.Category;
import com.cqut.service.BusinessService;
import com.cqut.service.impl.BusinessServiceImpl;
import com.cqut.util.FillBeanUtil;




public class ControlServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("addCategory".equals(op)){
			addCategory(request,response);
		}else if("showAllCategories".equals(op)){
			showAllCategories(request,response);
		}else if("showAddBookUI".equals(op)){
			showAddBookUI(request,response);
		}else if("addBook".equals(op)){
			try {
				addBook(request,response);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else if("showAllBooks".equals(op)){
			showAllBooks(request,response);
		}
	}
	//分页显示所有书籍
	private void showAllBooks(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String num = request.getParameter("num");
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/manage/ControlServlet?op=showAllBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
	}
	//添加一本图书
	private void addBook(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			request.setAttribute("msg","哥们，你的表单有误，请检查");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items = sfu.parseRequest(request);
		
		Book book = new Book();
		for(FileItem item:items){
			//封装基本数据到Book中
			if(item.isFormField()){
				String fieldName = item.getFieldName();
				String fieldValue = item.getString(request.getCharacterEncoding());
				BeanUtils.setProperty(book,fieldName, fieldValue);
			}else{
				//文件上传
				String fileName = item.getName();
				if(fileName!=null&&!fileName.trim().equals("")){
					//改文件名：唯一的文件名
					fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
					//计算存储路径
					String storeDirectory = getServletContext().getRealPath("/images");
					String path = makeDirs(storeDirectory, fileName);//   /dir1/dir2
					
					book.setPath(path);
					book.setPhotoFileName(fileName);
					
					//上传
					item.write(new File(storeDirectory+path+"/"+fileName));
				}
			}
		}
		//保存书籍信息到数据库中
		s.addBook(book);
		request.setAttribute("msg","书籍保存成功！");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
	//显示添加书籍的界面
	private void showAddBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}
	//查询所有分类
	private void showAllCategories(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/listCategory.jsp").forward(request, response);
	}
	//添加一个分类
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException  {
		Category category = FillBeanUtil.fillBean(request, Category.class);
		s.addCategory(category);
		
		request.setAttribute("msg", "保存成功！");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	//storeDirecotry:   images的真实路径
	//filename:UUID的文件名
	public String makeDirs(String storeDirecotry,String filename){
		int hashCode = filename.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0)>>4;
		
		String newPath = "/"+dir1+"/"+dir2;
		File file = new File(storeDirecotry, newPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return newPath;
		
	}
}
