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
	//��ҳ��ʾ�����鼮
	private void showAllBooks(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		String num = request.getParameter("num");
		Page page = s.findAllBookPageRecords(num);
		page.setUrl("/manage/ControlServlet?op=showAllBooks");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
	}
	//���һ��ͼ��
	private void addBook(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(!isMultipart){
			request.setAttribute("msg","���ǣ���ı���������");
			request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
		List<FileItem> items = sfu.parseRequest(request);
		
		Book book = new Book();
		for(FileItem item:items){
			//��װ�������ݵ�Book��
			if(item.isFormField()){
				String fieldName = item.getFieldName();
				String fieldValue = item.getString(request.getCharacterEncoding());
				BeanUtils.setProperty(book,fieldName, fieldValue);
			}else{
				//�ļ��ϴ�
				String fileName = item.getName();
				if(fileName!=null&&!fileName.trim().equals("")){
					//���ļ�����Ψһ���ļ���
					fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(fileName);
					//����洢·��
					String storeDirectory = getServletContext().getRealPath("/images");
					String path = makeDirs(storeDirectory, fileName);//   /dir1/dir2
					
					book.setPath(path);
					book.setPhotoFileName(fileName);
					
					//�ϴ�
					item.write(new File(storeDirectory+path+"/"+fileName));
				}
			}
		}
		//�����鼮��Ϣ�����ݿ���
		s.addBook(book);
		request.setAttribute("msg","�鼮����ɹ���");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}
	//��ʾ����鼮�Ľ���
	private void showAddBookUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
	}
	//��ѯ���з���
	private void showAllCategories(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		List<Category> cs = s.findAllCategories();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manage/listCategory.jsp").forward(request, response);
	}
	//���һ������
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException  {
		Category category = FillBeanUtil.fillBean(request, Category.class);
		s.addCategory(category);
		
		request.setAttribute("msg", "����ɹ���");
		request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	//storeDirecotry:   images����ʵ·��
	//filename:UUID���ļ���
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
