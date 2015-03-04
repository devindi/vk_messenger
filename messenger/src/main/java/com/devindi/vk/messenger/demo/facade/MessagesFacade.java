package com.devindi.vk.messenger.demo.facade;

import android.util.Log;
import com.devindi.vk.messenger.demo.activity.ConversationActivity;
import com.vk.sdk.api.*;

public class MessagesFacade {

    private final ConversationActivity activity;

    public MessagesFacade(ConversationActivity activity) {
        this.activity = activity;
    }

    public void loadMessages(int chatId)
    {
        VKRequest request = new VKRequest("messages.getHistory", VKParameters.from("chat_id", chatId));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.e("MSG", response.json.toString());
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.e("MSG", error.errorMessage + " ");
            }
        });
    }
}
