package com.devindi.vk.messenger.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.devindi.vk.messenger.demo.R;

public class AvatarView extends FrameLayout {
    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        inflate(context, R.layout.view_avatar, this);
    }
}
