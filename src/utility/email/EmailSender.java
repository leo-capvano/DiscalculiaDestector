/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility.email;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class EmailSender {
    
    private static Logger logger = Logger.getLogger("EmailSender");
    
    private static final String CONFIG_FILE = "/config.json";
    
    private Properties emailProperties;
    
    public void sendEmail(String to, String subject, String message) {
        try {
            InternetAddress[] mailAddress = InternetAddress.parse(to);
            sendEmail(mailAddress, subject, message);
        } catch (AddressException e) {
            logger.log(Level.SEVERE, "{0} is not a valid email address", to);
        }
    }
    
    public void sendEmail(InternetAddress[] address, String subject, String message) {
        
    	new Thread(() -> {
    		String email = this.emailProperties.getProperty("mail.smtp.user");
			String password = this.emailProperties.getProperty("mail.smtp.password");
			String smtpHost = this.emailProperties.getProperty("mail.smtp.host");
			
			if(email == null) {
			    logger.severe("Unable to send email. Missing 'mail.smtp.user' property in " + CONFIG_FILE + " file");
			    return;
			}
			
			if(password == null) {
			    logger.severe("Unable to send email. Missing 'mail.smtp.password' property in " + CONFIG_FILE + " file");
			    return;
			}
			
			if(smtpHost == null) {
			    logger.severe("Unable to send email. Missing 'mail.smtp.host' property in " + CONFIG_FILE + " file");
			    return;
			}
			
			Session session = Session.getInstance(this.emailProperties, new Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(email, password);
			    }
			});
			
			MimeMessage mimeMessage = new MimeMessage(session);
			try {
			    mimeMessage.setFrom(new InternetAddress(email));
			    mimeMessage.addRecipients(Message.RecipientType.TO, address);
			    mimeMessage.setSubject(subject);
			    mimeMessage.setText(message);
			
			    Transport transport = session.getTransport("smtp");
			    transport.connect(smtpHost, email, password);
			    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			
			} catch (MessagingException e) {
			    logger.log(Level.SEVERE, "Unable to send email. Cause:{0}", e.getMessage());
			}
    	}).start();
    }
    
    protected EmailSender() {
        this.emailProperties = new Properties();
        
        Map<String, String> properties = this.getPropertiesFromFile();
        if(properties == null) {
            logger.severe("CANNOT INIT EMAIL SENDER.");
            return;
        }
        
        Set<String> keySet = properties.keySet();
        for(String key : keySet)
            this.emailProperties.setProperty(key, properties.get(key));
    }
    
    private Map<String, String> getPropertiesFromFile() {
        Map<String, String> propertiesMap = new HashMap<String, String>();

        propertiesMap.put("mail.smtp.starttls.enable", "true");
        propertiesMap.put("mail.smtp.host", "smtp.gmail.com");
        propertiesMap.put("mail.smtp.user", "discalculia.capuano@gmail.com");
        propertiesMap.put("mail.smtp.password", "discalculia33");
        propertiesMap.put("mail.smtp.port", "587");
        propertiesMap.put("mail.smtp.auth", "true");
        
        /*
    	
    	Uncomment here and delete the code above to read from file
    	
        Gson gson = new Gson();
        FileReader fileReader;
        
        try {
        	URI uri = getClass().getResource(CONFIG_FILE).toURI();
        	File file = new File(uri);
            fileReader = new FileReader(file);
        } catch(FileNotFoundException e) {
            logger.severe(e.getMessage());
            return propertiesMap;
        } catch (URISyntaxException e) {
			logger.severe(e.getMessage());
			return propertiesMap;
		}
        
        JsonReader jsonReader = new JsonReader(fileReader);
        propertiesMap = gson.fromJson(jsonReader, HashMap.class);
        
        */
        
        return propertiesMap;
    }
    
}
