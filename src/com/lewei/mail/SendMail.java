package com.lewei.mail;

public class SendMail {

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.exmail.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("rd@happydo.net");
		mailInfo.setPassword("leWei123");// 您的邮箱密码
		mailInfo.setFromAddress("rd@happydo.net");
		// mailInfo.setToAddress("89402393@qq.com");
		mailInfo.setToAddress("894559192@qq.com");
		mailInfo.setSubject("来自djzhao");
		mailInfo.setContent("This is a test email for java mail~ !");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);//发送html格式
	}

	public void sendWarningEmail(String mail,String content) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.exmail.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("rd@happydo.net"); // 发送邮箱账号
		mailInfo.setPassword("leWei123");// 您的邮箱密码
		mailInfo.setFromAddress("rd@happydo.net"); // 来自
		mailInfo.setToAddress(mail); // 目的邮箱
		mailInfo.setSubject("预警邮件");// 主题
		mailInfo.setContent(content);// 内容
		// 这个类主要来发邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
	}
}
