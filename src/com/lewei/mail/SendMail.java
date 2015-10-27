package com.lewei.mail;

public class SendMail {

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("18896807726@163.com");
		mailInfo.setPassword("59741z*dj6278891");// 您的邮箱密码
		mailInfo.setFromAddress("18896807726@163.com");
		// mailInfo.setToAddress("89402393@qq.com");
		mailInfo.setToAddress("894559192@qq.com");
		mailInfo.setSubject("来自djzhao");
		mailInfo.setContent("This is a test email for java mail !");
		// 这个类主要来发�?邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发�?文体格式
		// sms.sendHtmlMail(mailInfo);//发�?html格式
	}

	public void sendWarningEmail(String mail) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("18896807726@163.com");
		mailInfo.setPassword("59741z*dj6278891");// 您的邮箱密码
		mailInfo.setFromAddress("18896807726@163.com");
		mailInfo.setToAddress(mail.split(";")[0]);
		mailInfo.setSubject("密码找回");
		mailInfo.setContent("如果该邮件是您本人申请发送，请打�?http://localhost:8080/admin/updatePwd.jsp?account="
				+ mail.split(";")[1] + " 来设置新密码");
		// 这个类主要来发�?邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发�?文体格式
	}
}
