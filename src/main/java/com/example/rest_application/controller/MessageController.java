package com.example.rest_application.controller;


import com.example.rest_application.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {

    private int counter = 4;

    private final List<Map<String, String>> messages = new ArrayList<>() {{ // database
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "first message");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "second message");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "third message");
        }});
    }};

    @GetMapping // get all items
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}") // to get one item
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping // post method
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);

        return message;
    }

    @PutMapping("{id}") // put method
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(message.get("id"));

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}") // delete method
    public void delete(@PathVariable String id) {

        Map<String, String> message = getMessage(id);

        messages.remove(message);
    }

}
