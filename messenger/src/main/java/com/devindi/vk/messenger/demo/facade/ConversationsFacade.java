package com.devindi.vk.messenger.demo.facade;

import android.os.AsyncTask;
import android.util.Log;
import com.vk.sdk.api.*;
import com.vk.sdk.api.model.VKApiChat;

import java.util.List;

public class ConversationsFacade {

    public void loadConversations(int page)
    {
        new LoadConversationsTask().execute(page);
    }




    private class LoadConversationsTask extends AsyncTask<Integer, Void, List<VKApiChat>>
    {

        /**
         *
         * @param params number of page.
         * @return
         */
        @Override
        protected List<VKApiChat> doInBackground(Integer... params) {
            Log.e("TAG", "DO IT");
            //Load for page number 'params[0]'
            //Page is 200 dialogs.
            //Look to server
//            VKRequest request = new VKRequest("execute.getChats", VKParameters.from(VKApiConst.OFFSET, params[0]));
            //Load chats from first 200 dialogs
            VKRequest request = new VKRequest("execute.getChats");
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    //Do complete stuff
                    Log.e("TAG", response.json.toString());
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
            return null;
        }
    }
}
