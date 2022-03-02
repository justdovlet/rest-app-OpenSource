package com.example.rest;

public class Greeting{
    private Long id;
    private String content;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Greeting(Long id, String content) {
        this.id = id;
        this.content = content;
    }

}
