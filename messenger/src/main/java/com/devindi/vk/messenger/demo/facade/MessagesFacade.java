package com.devindi.vk.messenger.demo.facade;

import android.util.Log;
import com.devindi.vk.messenger.demo.activity.ConversationActivity;
import com.vk.sdk.api.*;
import com.vk.sdk.api.model.VKApiMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                JSONObject object = response.json.optJSONObject("response");
                JSONArray messages = object.optJSONArray("items");
                ArrayList<VKApiMessage> messagesList = new ArrayList<VKApiMessage>(messages.length());
                for(int i = messages.length() -1; i > -1; i--)
                {
                    try {
                        messagesList.add(new VKApiMessage().parse(messages.optJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                activity.onLoadMessage(messagesList);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.e("MSG", error.errorMessage + " ");
            }
        });
    }

    public void loadAvatarUrls(int user_id, VKRequest.VKRequestListener listener) {
        VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_100", VKApiConst.USER_ID, user_id));
        request.executeWithListener(listener);
    }
}
