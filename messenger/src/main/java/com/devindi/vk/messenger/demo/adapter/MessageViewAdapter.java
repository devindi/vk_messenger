package com.devindi.vk.messenger.demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.vk.sdk.api.model.VKApiMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageViewAdapter extends BaseAdapter {

    private List<VKApiMessage> messages;
    private final LayoutInflater inflater;
    private final Context context;

    public MessageViewAdapter(Activity context) {
        inflater = context.getLayoutInflater();
        this.context = context;
        messages = new ArrayList<VKApiMessage>();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public VKApiMessage getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("ADAPTER", "Q!");
        return null;
    }

    public void add(List<VKApiMessage> newMessages) {
        messages.addAll(newMessages);
        Log.e("MSG@", newMessages.toString());
        notifyDataSetChanged();
    }
}
