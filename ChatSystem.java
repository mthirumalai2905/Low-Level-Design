import java.util.*;

class User{
    private String name;
    private List<Message> messages = new ArrayList<>();

    public User(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void receiveMessage(Message message){
        messages.add(message);
        System.out.println(name + "receieved: " + message.getContent());
    }
}

class Message{
    private String content;

    public Message(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}

class ChatRoom{
    private List<User> users = new ArrayList<>();

    public void join(User user){
        users.add(user);
    }

    public void sendMessage(User sender, String content){
        Message message = new Message(content);
        for(User user : users){
            if(!user.equals(sender)){
                user.receiveMessage(message);
            }
        }
    }
}

public class ChatApp{
    public static void main(String[] args){
        User alice = new User("Alice");
        User bob = new User("Bob");

        ChatRoom room = new ChatRoom();
        room.join(alice);
        room.join(bob);

        room.sendMessage(alice, "Hello, Bob!");
        room.sendMessage(bob, "Hi, Alice!");
    }
}
