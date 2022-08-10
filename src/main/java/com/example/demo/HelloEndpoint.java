package com.example.demo;

import java.io.*;
import java.util.ArrayList;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/logger")
public class HelloEndpoint{
    ArrayList<String> logs = new ArrayList<String>();

    @OnOpen
    public void onOpen(Session session){
        System.out.printf("Session opened, id: %s\n",session.getId());
        try {
            session.getBasicRemote().sendText("Successfully connection");
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.printf("Message received, Session id: %s Message: %s\n",
                session.getId(),message);


        int startInd = message.indexOf("{");
        int endInd = message.indexOf("}");

        MessageModel received = new MessageModel();
        message.replaceAll(" ","");
        message = message.substring(startInd+1,endInd);
        String[] arrOfStr = message.split(",");

        for (String word : arrOfStr) {
            int splitter = word.indexOf(":");
            String first = word.substring(0,splitter).trim();
            String second = word.substring(splitter+1,word.length()).trim();

            if (first.equals("\"command\"") || first.equals("command")){
                received.setCommand(second);
            } else if (first.equals("\"content\"") || first.equals("content")){
                received.setContent(second);
            } else {
                Error e = new Error("Not expected in structure of data");
                onError(e,session);
            }
        }

        if (received.getCommand().equals("\"logs\"") || received.getCommand().equals("logs")){
            try {
                for (String log : logs) {
                    session.getBasicRemote().sendText(log+"\n");
                }
                System.out.println("Log list sent");
            }catch (IOException ex){
                ex.printStackTrace();
            }
        } else if (received.getCommand().equals("\"addLog\"") || received.getCommand().equals("addLog")) {
            logs.add(received.getContent());
            try {
                session.getBasicRemote().sendText("Log "+received.getContent()+" added\n");
                System.out.println("Log registration");
            } catch (IOException ex){
                ex.printStackTrace();
            }
        } else{
                Error e = new Error("Not expected function\nThere only command \"addLog\" and \"logs\"");
                onError(e,session);
        }
    }

    @OnError
    public void onError(Throwable e, Session session){
        try {
            session.getBasicRemote().sendText(e.getMessage());
        } catch (IOException ex){
            e.printStackTrace();
            ex.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        System.out.printf("Session %s closed\n", session.getId());
        try {
            session.getBasicRemote().sendText("Session "+session.getId()+" closed\n");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }


}
