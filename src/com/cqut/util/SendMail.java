package com.cqut.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.cqut.domain.Customer;


public class SendMail extends Thread{

	private Customer c;

	public SendMail(Customer c) {
		this.c = c;
	}
	//���ͼ����ʼ�����
	public void run() {
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");//ָ���ʼ����͵�Э�飬�����ǹ淶�涨��
			props.setProperty("mail.host", "smtp.163.com");//ָ�������������ĵ�ַ�������ǹ淶�涨��
//		props.setProperty("mail.debug", "true");//�ʼ����͵ĵ���ģʽ�������ǹ淶�涨��
			props.setProperty("mail.smtp.auth", "true");//������������������֤������������JavaMailʵ���й�
			Session session = Session.getInstance(props);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("itheimacloud@163.com"));
			message.setRecipients(Message.RecipientType.TO, c.getEmail());
			message.setSubject("����XX��վ�ļ����ʼ�");
			
			message.setContent("�װ���"+c.getUsername()+"<br/>��л��ע���Ϊ���ǵĻ�Ա�����ʹ����漤�������˻���<br/><a href='http://localhost:8080/day23_00_netstore/servlet/ClientServlet?op=active&code="+c.getCode()+"'>������</a><br/>���ʼ���ϵͳ�Զ��������벻Ҫֱ�ӻظ���", "text/html;charset=UTF-8");
			message.saveChanges();
			
			Transport ts = session.getTransport();
			ts.connect("itheimacloud", "iamsorry");
			ts.sendMessage(message, message.getAllRecipients());
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
