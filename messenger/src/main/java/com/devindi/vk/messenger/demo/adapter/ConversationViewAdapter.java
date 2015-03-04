package com.devindi.vk.messenger.demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.devindi.vk.messenger.demo.R;
import com.devindi.vk.messenger.demo.facade.ConversationsFacade;
import com.devindi.vk.messenger.demo.model.Conversation;
import com.devindi.vk.messenger.demo.tools.StringHelper;
import com.devindi.vk.messenger.demo.view.AvatarView;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ConversationViewAdapter extends BaseAdapter {

    private List<Conversation> data;
    private final LayoutInflater inflater;
    private final ConversationsFacade facade;
    private final Context context;

    public ConversationViewAdapter(Activity activity, ConversationsFacade facade) {
        this.facade = facade;
        inflater = activity.getLayoutInflater();
        data = new ArrayList<Conversation>();
        this.context = activity;
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

        if(!item.hasUrls())
            facade.loadAvatarUrls(item.getUsers(), new AvatarManager(viewHolder.avatar));

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


    private class AvatarManager extends VKRequest.VKRequestListener {

        private AvatarView avatarView;

        public AvatarManager(AvatarView avatarView) {
            this.avatarView = avatarView;
        }

        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            JSONArray users = response.json.optJSONArray("response");
            for(int i = 0; i < Math.min(users.length(), 4); i++)
            {
                avatarView.setCount(i+1);
                Glide.with(context).load(users.optJSONObject(i).optString("photo_100")).into(avatarView.getImageView(i));
            }
        }
    }
}
