package org.example.advancedrealestate_be.controller.api.chat;

import com.nimbusds.jose.shaded.gson.JsonObject;
import net.minidev.json.JSONObject;
import org.example.advancedrealestate_be.model.Chat;
import org.example.advancedrealestate_be.service.Task.ScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Controller
@CrossOrigin(origins = "https://localhost:3000")
public class ChatApiController {

//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public Chat sendMessage(@Payload Chat chat){
//        chat.setTimeStamp(new Date());
//        return chat;
//    }

    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledTask scheduledTask;

    @Autowired
    public ChatApiController(SimpMessagingTemplate messagingTemplate, ScheduledTask scheduledTask) {
        this.messagingTemplate = messagingTemplate;
        this.scheduledTask = scheduledTask;
    }

    private final Map<String, Set<String>> roomUsers = new HashMap<>();
    private String bot = "Bot: ";

    public static String generateRandomMessageId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return "Mid"+sb;
    }


    private void awaitSend(JSONObject messageObject, String room, String message){
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(500);
                messageObject.put("content", null);
                messageObject.put("bot", message);
                messagingTemplate.convertAndSend("/topic/room/" + room, messageObject.toString());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @MessageMapping("/sendMessageToRoom/{room}")
    public void sendMessageToRoom(@DestinationVariable("room") String room, Chat message) {
        System.out.println("Message: " + message);
        System.out.println("Room: " + room);
        String messageId = generateRandomMessageId(9);
        JSONObject messageObject = new JSONObject();
        messageObject.put("id", messageId);
        messageObject.put("sender", message.getEmail());
        messageObject.put("content", message.getContent());

        if(Objects.equals(message.getContent(), "a")){
            awaitSend(messageObject, room, "hahahaha");
        }
        if(Objects.equals(message.getContent(), "hello")){
            awaitSend(messageObject, room, "Hello " + message.getSender());
        }
        messagingTemplate.convertAndSend("/topic/room/" + room, messageObject.toString());
    }

    @MessageMapping("/addUser/{room}")
    public void addUser(@DestinationVariable("room") String room, Chat message, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("User joined room: " + room);

        System.out.println("Message: " + message);
        JSONObject messageObject = new JSONObject();
        Set<String> usersInRoom = roomUsers.getOrDefault(room, new HashSet<>());
        usersInRoom.add(message.getEmail());
        roomUsers.put(room, usersInRoom);
        System.out.println(usersInRoom);
        messageObject.put("count", usersInRoom.size());
        messageObject.put("email", message.getEmail());
        messageObject.put("sender", message.getEmail());
        messageObject.put("bot", "Chào mừng " + message.getEmail() + " đã vào phòng " + room);
        messageObject.put("content", message.getContent());

        headerAccessor.getSessionAttributes().put("username", message.getSender());

        if(Objects.equals(room, "phòng đấu giá bất động sản cao cấp")){
            awaitSend(messageObject, room, "Chào " + message.getEmail() + " bạn cần tôi giúp gì không?");
        }

        messagingTemplate.convertAndSend("/topic/room/" + room, messageObject.toString());
    }


    @MessageMapping("/leaveRoom/{room}")
    public void userLeaveRoom(@DestinationVariable("room") String room, Chat message, SimpMessageHeaderAccessor headerAccessor) {

        System.out.println("Message: " + message);
        JSONObject messageObject = new JSONObject();
        Set<String> usersInRoom = roomUsers.getOrDefault(room, new HashSet<>());
        usersInRoom.remove(message.getEmail());
        messageObject.put("bot", message.getEmail() + " đã rời phòng " + room + "!");
        messageObject.put("count", usersInRoom.size());

        messagingTemplate.convertAndSend("/topic/room/" + room, messageObject.toString());
    }
}
