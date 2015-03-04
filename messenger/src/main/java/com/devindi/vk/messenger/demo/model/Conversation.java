package com.devindi.vk.messenger.demo.model;

import com.devindi.vk.messenger.demo.tools.StringHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private int id;
    private String title;
    private String body;
    private long date;
    private List<Integer> users;
    private List<String> userAvatars;

    private Conversation()
    {
        users = new ArrayList<Integer>(0);
        userAvatars = new ArrayList<String>(0);
    }

    public static Conversation parse(JSONObject object)
    {
        Conversation conversation = new Conversation();
        conversation.body = object.optString("body");
        conversation.title = object.optString("title");
        conversation.id = object.optInt("chat_id");
        conversation.date = object.optLong("date");
        JSONArray users = object.optJSONArray("chat_active");
        if(users == null) return conversation;
        for(int i = 0; i<users.length(); i++)
        {
            conversation.users.add(users.optInt(i));
        }
        return conversation;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public long getDate() {
        return date;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public boolean hasUrls()
    {
        return !userAvatars.isEmpty();
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", users=" + users +
                '}';
    }
}
