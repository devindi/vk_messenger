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
import com.devindi.vk.messenger.demo.facade.MessagesFacade;
import com.devindi.vk.messenger.demo.tools.StringHelper;
import com.devindi.vk.messenger.demo.view.AvatarView;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageViewAdapter extends BaseAdapter {

    private List<VKApiMessage> messages;
    private final LayoutInflater inflater;
    private final Context context;
    private MessagesFacade facade;

    public MessageViewAdapter(Activity context, MessagesFacade facade) {
        this.facade = facade;
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
        ViewHolder viewHolder;

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.view_conversation, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.avatar = (AvatarView) convertView.findViewById(R.id.avatar);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        VKApiMessage item = getItem(position);

        viewHolder.date.setText(StringHelper.format(item.date));
        viewHolder.body.setText(item.body);
        viewHolder.avatar.getImageView(0).setImageResource(R.drawable.ic_launcher);

        facade.loadAvatarUrls(item.user_id, new AvatarManager(viewHolder.avatar, item));

        return convertView;

    }

    private class ViewHolder
    {
        AvatarView avatar;
        TextView body;
        TextView date;
    }

    public void add(List<VKApiMessage> newMessages) {
        messages.addAll(newMessages);
        notifyDataSetChanged();
    }

    private class AvatarManager extends VKRequest.VKRequestListener {

        private AvatarView avatarView;
        private VKApiMessage item;

        public AvatarManager(AvatarView avatarView, VKApiMessage item) {
            this.avatarView = avatarView;
            this.item = item;
        }

        @Override
        public void onComplete(VKResponse response) {
            super.onComplete(response);
            String url = response.json.optJSONArray("response").optJSONObject(0).optString("photo_100");
            Glide.with(context).load(url).into(avatarView.getImageView(0));
            avatarView.setCount(1);
        }
    }
}
