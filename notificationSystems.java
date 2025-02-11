import java.util.*;

enum NotificationType{
    EMAIL, SMS, PUSH
}

// Notification Interface
interface Notification{
    void send(String recipent, String message);
}


// Email Notifications
class EmailNotification implements Notification {
    @Override
    public void send(String recipient, String message){
        System.out.println("Sending EMAIL to" + recipient + ": " + message);
    }
}

// SMS Notifications
class SMSNotification implements Notification{
    @Override
    public void send(String recipient, String message){
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}

// Push Notification
class PushNotification implements Notification{
    @Override
    public void send(String recipient, String message){
        System.out.println("Sending PUSH Notification to " + recipient + ":" + message);
    }
}

// Notification Factory
class NotificationFactory{
    public static Notification getNotification(NotificationType type){
        return switch(type){
            case EMAIL -> new EmailNotification();
            case SMS -> new SMSNotification();
            case PUSH -> new PushNotification();
        };
    }
}

// Notification Service
class NotificationService{
    private final Map<NotificationType, Notification> notificationMap;

    public NotificationService(){
        notificationMap = new HashMap<>();
        for(NotificationType type : NotificationType.values()){
            notificationMap.put(type, NotificationFactory.getNotification(type));
        }
    }

    public void sendNotification(NotificationType type, String recipient, String message){
        Notification notification = notificationMap.get(type);
        if(notification != null){
            notification.send(recipient, message);
        } else {
            System.out.println("Invalid Notification Type!!");
        }
    }
}

public class NotificationServiceTest{
    public static void main(String[] args){
        NotificationService service = new NotificationService();

        service.sendNotification(NotificationType.EMAIL, "user@example.com", "Hello via Email!");
        service.sendNotification(NotificationType.SMS, "123457894", "Hello via SMS!");
        service.sendNotification(NotificationType.PUSH, "userDeviceId", "Hello via Push Notifications");
    }
}

