package com.devindi.vk.messenger.demo.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.bumptech.glide.Glide;
import com.devindi.vk.messenger.demo.R;
import com.devindi.vk.messenger.demo.adapter.MessageViewAdapter;
import com.devindi.vk.messenger.demo.facade.MessagesFacade;
import com.devindi.vk.messenger.demo.view.AvatarView;
import com.vk.sdk.api.model.VKApiMessage;

import java.util.List;

public class ConversationActivity extends BaseVKActivity {

    private MessagesFacade facade;
    private ListView messagesList;
    private MessageViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facade = new MessagesFacade(this);
        setContentView(R.layout.activity_list);
        adapter = new MessageViewAdapter(this);
        messagesList = (ListView) findViewById(R.id.list_conversations);
        messagesList.setAdapter(adapter);
        ActionBar actionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        loadConversation(extras.getInt("chat_id"));
        actionBar.setTitle(extras.getString("title"));
        actionBar.setSubtitle(extras.getInt("count") + " участников");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        AvatarView avatarView = new AvatarView(this);
        TypedValue tv = new TypedValue();
        int actionBarHeight = 90;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

        }
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                actionBarHeight,
                actionBarHeight, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 10;
        avatarView.setLayoutParams(layoutParams);
        actionBar.setCustomView(avatarView);
        String[] urls = extras.getStringArray("urls");
        int i = 0;
        for (String url : urls) {
            Glide.with(this).load(url).into(avatarView.getImageView(i));
            i++;
            avatarView.setCount(i);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }

    private void loadConversation(int chatId)
    {
        facade.loadMessages(chatId);
    }

    public void onLoadMessage(List<VKApiMessage> newMessages)
    {
        adapter.add(newMessages);
    }
}
