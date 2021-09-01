package utility;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Francesco Capriglione
 * @version 1.0
 * 
 * An utility class used for formatting json response
 */
public class JsonResponse {
	
	private boolean ok;
	private String message;
	private Map<String, Object> content;

	public JsonResponse() {
		this.content = new HashMap<String, Object>();
	}
	
	public JsonResponse(boolean status) {
		this.content = new HashMap<String, Object>();
		this.ok = status;
	}
	
	public JsonResponse(boolean status, String message) {
		this.content = new HashMap<String, Object>();
		this.ok = status;
		this.message = message;
	}
	
	public boolean getJsonResponseStatus() {
		return ok;
	}

	public void setJsonResponseStatus(boolean jsonResponseStatus) {
		ok = jsonResponseStatus;
	}

	public String getJsonResponseMessage() {
		return message;
	}

	public void setJsonResponseMessage(String jsonResponseMessage) {
		message = jsonResponseMessage;
	}

	public void addContent(String key, Object value) {
		content.put(key, value);
	}
	
	public Object getContent(String key) {
		return content.get(key);
	}
	
	public Object removeContent(String key) {
		return content.remove(key);
	}
}
