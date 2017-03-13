package com.example.application.travelue;

import java.util.Date;

/**
 * Created by Adri on 03/03/2017.
 */

public class ChatMessage {
    private String messageText;
    private String recibidor;
    private String mandador;
    private long messageTime;

    public ChatMessage(String messageText, String mandador, String recibidor) {
        this.messageText = messageText;
        this.mandador = mandador;
        this.recibidor = recibidor;

        messageTime = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getRecibidor() {
        return recibidor;
    }

    public void setRecibidor(String recibidor) {
        this.recibidor = recibidor;
    }

    public String getMandador() {
        return mandador;
    }

    public void setMandador(String mandador) {
        this.mandador = mandador;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}