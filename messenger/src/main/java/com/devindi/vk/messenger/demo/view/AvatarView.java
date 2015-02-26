package com.devindi.vk.messenger.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AvatarView extends ImageView {
    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void AddImage(Bitmap bitmap)
    {

    }

    //TODO implement
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
