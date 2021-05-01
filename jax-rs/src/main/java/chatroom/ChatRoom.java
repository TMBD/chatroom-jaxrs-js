package chatroom;

import java.util.ArrayList;
import java.util.HashMap;


public class ChatRoom{

    private static HashMap<String, ArrayList<Message>> listUsersMessage = new HashMap<String, ArrayList<Message>>();

    public boolean subscribe(String pseudo) {
    	try {
    		listUsersMessage.put(pseudo, new ArrayList<Message>());
	        System.out.println("User Subcribed");
	        Message message =new Message("", pseudo+ " a rejoint le salon...");
	        for (String keyString : listUsersMessage.keySet()) {
	        	 if(!pseudo.equals(keyString)) listUsersMessage.get(keyString).add(message);
	        }
	        return true;
    	}catch (Exception e) {
    		System.err.println(e);
			return false;
		}
    }

    
    public boolean unsubscribe(String pseudo) {
    	try {
			listUsersMessage.remove(pseudo);
	        System.out.println("User Unsubcribed");
	        Message message =new Message("", pseudo+ " a quitté le salon...");
	        for (String keyString : listUsersMessage.keySet()) {
	        	 if(!pseudo.equals(keyString)) listUsersMessage.get(keyString).add(message);
	        }
	        return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
    }

    
    public ArrayList<Message> getMessage(String pseudo) {
        ArrayList<Message> messageArrayList = listUsersMessage.get(pseudo);
        listUsersMessage.put(pseudo, new ArrayList<Message>());
        return messageArrayList;
    }

    
    public boolean postMessage(String pseudo, String message) {
    	try {
			Message newMessage = new Message(pseudo, message);
	        for (String keyString : listUsersMessage.keySet()) {
	        	if(!pseudo.equals(keyString)) listUsersMessage.get(keyString).add(newMessage);
	        }
	        return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
    }
    
}
	