package com.devindi.vk.messenger.demo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.devindi.vk.messenger.demo.R;
import com.devindi.vk.messenger.demo.model.Conversation;
import com.devindi.vk.messenger.demo.tools.StringHelper;
import com.devindi.vk.messenger.demo.view.AvatarView;

import java.util.ArrayList;
import java.util.List;

public class ConversationViewAdapter extends BaseAdapter {

    private List<Conversation> data;
    private final LayoutInflater inflater;

    public ConversationViewAdapter(Activity activity) {
        inflater = activity.getLayoutInflater();
        data = new ArrayList<Conversation>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Conversation getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.view_conversation, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.avatar = (AvatarView) convertView.findViewById(R.id.avatar);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        Conversation item = getItem(position);

        viewHolder.title.setText(item.getTitle());
        viewHolder.date.setText(StringHelper.format(item.getDate()));
        viewHolder.body.setText(item.getBody());

        return convertView;
    }

    public void add(List<Conversation> newConversations) {
        data.addAll(newConversations);
        notifyDataSetChanged();
    }

    private class ViewHolder
    {
        AvatarView avatar;
        TextView title;
        TextView body;
        TextView date;
    }
}
