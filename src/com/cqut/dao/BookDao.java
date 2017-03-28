package com.cqut.dao;

import java.util.List;

import com.cqut.domain.Book;


public interface BookDao {

	void save(Book book);
	/**
	 * �鼮��������
	 * @return
	 */
	int findAllBooksNumber();
	/**
	 * ��ѯ�����鼮�ķ�ҳ����
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	List<Book> findPageBooks(int startIndex,int offset);

	Book findById(String bookId);
	/**
	 * ĳ���鼮��������
	 * @return
	 */
	int findCategoryBooksNumber(String categoryId);
	/**
	 * ���շ����ѯ�鼮�ķ�ҳ����
	 * @param startIndex
	 * @param offset
	 * @return
	 */
	List findPageBooks(int startIndex, int pageSize, String categoryId);

}
