package tools;

import java.util.Date;
import java.util.Properties;
 /*
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
 */
public class EmailSend {
	/*
  public static void main(String args[]) {
    sendMail();
  }
  
  
  public static void sendMail() {
    // ���� ���ڵ�
    final String bodyEncoding = "UTF-8"; //������ ���ڵ�
    
    String subject = "���� �߼� �׽�Ʈ";
    String fromEmail = "nesiteam@gmail.com";
    String fromUsername = "SYSTEM MANAGER";
    String toEmail = "ny_nesi@naver.com"; // �޸�(,)�� ������ ����
    
    final String username = "nesiteam@gmail.com";         
    final String password = "tazulgzvrsqpdyzk";
    
    // ���Ͽ� ����� �ؽ�Ʈ
    StringBuffer sb = new StringBuffer();
    sb.append("<h3>�ȳ��ϼ���</h3>\n");
    sb.append("<h4>�����ϴ� ��ġ�Դϴ�.</h4>\n");    
    String html = sb.toString();
    
    // ���� �ɼ� ����
    Properties props = new Properties();    
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.auth", "true");
 
    props.put("mail.smtp.quitwait", "false");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.socketFactory.fallback", "false");
    
    try {
      // ���� ����  ���� ���� ����
      Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(username, password);
        }
      };
      
      // ���� ���� ����
      Session session = Session.getInstance(props, auth);
      
      // ���� ��/���� �ɼ� ����
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(fromEmail, fromUsername));
      message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail, false));
      message.setSubject(subject);
      message.setSentDate(new Date());
      
      // ���� ������ ����
      Multipart mParts = new MimeMultipart();
      MimeBodyPart mTextPart = new MimeBodyPart();
      MimeBodyPart mFilePart = null;
 
      // ���� ������ - ����
      mTextPart.setText(html, bodyEncoding, "html");
      mParts.addBodyPart(mTextPart);
            
      // ���� ������ ����
      message.setContent(mParts);
      
      // MIME Ÿ�� ����
      MailcapCommandMap MailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
      MailcapCmdMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
      MailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
      MailcapCmdMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
      MailcapCmdMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
      MailcapCmdMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
      CommandMap.setDefaultCommandMap(MailcapCmdMap);
 
      // ���� �߼�
      Transport.send( message );
      
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
*/
}