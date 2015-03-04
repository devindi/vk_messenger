package com.devindi.vk.messenger.demo.facade;

import android.os.AsyncTask;
import android.util.Log;
import com.devindi.vk.messenger.demo.activity.ConversationsActivity;
import com.devindi.vk.messenger.demo.model.Conversation;
import com.vk.sdk.api.*;
import com.vk.sdk.api.model.VKApiChat;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ConversationsFacade {

    private final ConversationsActivity activity;

    public ConversationsFacade(ConversationsActivity activity) {
        this.activity = activity;
    }

    public void loadConversations(int page)
    {
//        new LoadConversationsTask().execute(page);

        Log.e("TAG", "DO IT");


        VKRequest request = new VKRequest("execute.getChats");
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //Do complete stuff
                Log.e("TAG", response.json.toString());
                final List<Conversation> chatList = new ArrayList<Conversation>();
                JSONArray chats = response.json.optJSONObject("response").optJSONArray("chats");
                chatList.add(Conversation.parse(chats.optJSONObject(0).optJSONObject("message")));
                Log.e("asd", chatList.toString());
                activity.onLoadConversations(chatList);
            }
            @Override
            public void onError(VKError error) {
                //Do error stuff
                Log.e("TAG", error.toString());
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                //I don't really believe in progress
                Log.e("TAG", attemptNumber + " / " + totalAttempts);
            }
        });
    }
}
