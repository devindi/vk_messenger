package com.devindi.vk.messenger.demo.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.devindi.vk.messenger.demo.R;
import com.devindi.vk.messenger.demo.adapter.ConversationViewAdapter;
import com.devindi.vk.messenger.demo.facade.ConversationsFacade;
import com.devindi.vk.messenger.demo.model.Conversation;
import com.vk.sdk.*;
import com.vk.sdk.api.VKError;
import com.vk.sdk.dialogs.VKCaptchaDialog;

import java.util.List;

public class ConversationsActivity extends BaseVKActivity {

    private static final String[] SCOPE = new String[] {
            VKScope.NOHTTPS,
            VKScope.MESSAGES
    };
    private static final String APP_ID = "4792768";
    private ConversationsFacade facade;

    private ListView conversationsList;
    private ConversationViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        facade = new ConversationsFacade(this);
        VKSdk.initialize(sdkListener, APP_ID);
        if (VKSdk.wakeUpSession())
            loadConversations();
        else
        {
            VKSdk.authorize(SCOPE, true, false);
        }
        adapter = new ConversationViewAdapter(this, facade);
        conversationsList = (ListView) findViewById(R.id.list_conversations);
        conversationsList.setAdapter(adapter);
        conversationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ConversationsActivity.this, ConversationActivity.class);
                Conversation item = adapter.getItem(position);
                intent.putExtra("chat_id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("count", item.getUsersCount());
                intent.putExtra("urls", item.getUrls());
                startActivity(intent);
            }
        });
    }

    public void onLoadConversations(List<Conversation> newConversations)
    {
        adapter.add(newConversations);
    }

    private void loadConversations()
    {
        facade.loadConversations(0);
    }

    private final VKSdkListener sdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(SCOPE);
        }

        @Override
        public void onAccessDenied(final VKError authorizationError) {
            new AlertDialog.Builder(VKUIHelper.getTopActivity())
                    .setMessage(authorizationError.toString())
                    .show();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            loadConversations();
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            loadConversations();
        }
    };
}
