package util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import model.ShopVO;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.alibaba.fastjson.JSON;

public class MailUtil {
	
	private static JavaMailSenderImpl senderImpl;
	private static String mailTo;

	public void setMailTo(String mailTo) {
		MailUtil.mailTo = mailTo;
	}

	public void setSenderImpl(JavaMailSenderImpl senderImpl) {
		MailUtil.senderImpl = senderImpl;
	}


	/** 
     *  发送附件的邮件
     */  
    public static void sendMail(String filePath, String fileName) throws Exception  
    {  
        // 建立邮件消息,发送简单邮件和html邮件的区别  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，  
        // multipart模式 为true时发送附件 可以设置html格式  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,  
                true, "utf-8");  
  
        // 设置收件人，寄件人  
        messageHelper.setTo(mailTo);  
        messageHelper.setFrom(senderImpl.getUsername());  
        messageHelper.setSubject("114统计报告");  
        // true 表示启动HTML格式的邮件  
        messageHelper.setText(
                "<html><head></head><body><h1>你好：附件中有统计报告！</h1></body></html>",  
                true);  
  
        FileSystemResource file = new FileSystemResource(  
                new File(filePath));  
        // 这里的方法调用和插入图片是不同的。  
        messageHelper.addAttachment(fileName, file);  
  
        // 发送邮件  
        senderImpl.send(mailMessage);  
  
        System.out.println("邮件发送成功..");  
    }  
    
    
    public static void sendMailNotice(ShopVO shopVO, int i) throws Exception  
    {  
    	// 建立邮件消息,发送简单邮件和html邮件的区别  
    	MimeMessage mailMessage = senderImpl.createMimeMessage();  
    	// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，  
    	// multipart模式 为true时发送附件 可以设置html格式  
    	MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,  
    			true, "utf-8");  
    	
    	// 设置收件人，寄件人  
    	messageHelper.setTo(PropertieUtil.loadPropertie("mail_notice"));  
    	messageHelper.setFrom(senderImpl.getUsername());  
    	messageHelper.setSubject("114短信提醒");  
    	// true 表示启动HTML格式的邮件  
    	if(i == 0){
    		messageHelper.setText(  
    				"<html><head></head><body>城市：" + shopVO.getCityVO().getName() + "&nbsp;&nbsp;&nbsp;分类：" 
    						+ shopVO.getCatVO().getName() + "&nbsp;&nbsp;&nbsp;店铺：" + shopVO.getName() + "&nbsp;短信数已经用完！请通知续费，否则将暂停使用！</body></html>",  
    						true);  
    	}else{
    		messageHelper.setText(  
    				"<html><head></head><body>城市：" + shopVO.getCityVO().getName() + "&nbsp;&nbsp;&nbsp;分类：" 
    						+ shopVO.getCatVO().getName() + "&nbsp;&nbsp;&nbsp;店铺：" + shopVO.getName() + "&nbsp;短信数不足 "+i+" 条！请通知续费，否则将暂停使用！</body></html>",  
    						true);  
    	}
    	
    	// 发送邮件  
    	senderImpl.send(mailMessage);  
    	
    	System.out.println("邮件发送成功..");  
    }  
    
    public static Map<String, String>  packMail(ShopVO shopVO, int i){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("type", "text");
    	map.put("subject", "114短信提醒");
    	if(i == 0){
    		map.put(  "text",
    				"<html><head></head><body>城市：" + shopVO.getCityVO().getName() + "&nbsp;&nbsp;&nbsp;分类：" 
    						+ shopVO.getCatVO().getName() + "&nbsp;&nbsp;&nbsp;店铺：" + shopVO.getName() + "&nbsp;短信数已经用完！请通知续费，否则将暂停使用！</body></html>"  
    						);  
    	}else{
    		map.put(  "text",
    				"<html><head></head><body>城市：" + shopVO.getCityVO().getName() + "&nbsp;&nbsp;&nbsp;分类：" 
    						+ shopVO.getCatVO().getName() + "&nbsp;&nbsp;&nbsp;店铺：" + shopVO.getName() + "&nbsp;短信数不足 "+i+" 条！请通知续费，否则将暂停使用！</body></html>" 
    						);  
    	}
    	return map;
    }

	public static Map<String, String> packMail(String path, String name) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("type", "file");
    	map.put("subject", "114统计报告");
    	map.put("text", "<html><head></head><body><h1>你好：附件中有统计报告！</h1></body></html>");
    	map.put("name", name);
    	map.put("path", path);
		return map;
	}
}
