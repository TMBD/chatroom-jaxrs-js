package chatroom;

public class Message {
	private String expeditor;
	private String content;
	
	public Message() {
	}
	
	public Message(String expeditor, String content) {
		this.expeditor = expeditor;
		this.content = content;
	}

	public String getExpeditor() {
		return expeditor;
	}

	public void setExpeditor(String expeditor) {
		this.expeditor = expeditor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
