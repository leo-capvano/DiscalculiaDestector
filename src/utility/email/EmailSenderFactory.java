package utility.email;

import java.util.LinkedList;
import java.util.List;

public class EmailSenderFactory {

    private static List<EmailSender> emailSenderPool = new LinkedList<EmailSender>();
    
    public synchronized static EmailSender getEmailSender() {
    	EmailSender email;
    	
    	if(emailSenderPool.isEmpty())
    		email = new EmailSender();
    	else {
    		email = emailSenderPool.get(0);
    		emailSenderPool.remove(0);
    	}
    		
    	return email;
    }
    
    public synchronized static void releaseEmailSender(EmailSender emailSender) {
    	if(emailSender != null)
    		emailSenderPool.add(emailSender);
    }
}
