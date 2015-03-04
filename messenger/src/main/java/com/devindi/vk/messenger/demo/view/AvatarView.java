package com.devindi.vk.messenger.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.devindi.vk.messenger.demo.R;
import com.joooonho.SelectableRoundedImageView;

public class AvatarView extends FrameLayout {

    private SelectableRoundedImageView[] userAvatars;
    private View rightContainer;

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
        userAvatars = new SelectableRoundedImageView[4];
        userAvatars[0] = (SelectableRoundedImageView) findViewById(R.id.topL);
        userAvatars[1] = (SelectableRoundedImageView) findViewById(R.id.topR);
        userAvatars[2] = (SelectableRoundedImageView) findViewById(R.id.bottomR);
        userAvatars[3] = (SelectableRoundedImageView) findViewById(R.id.bottomL);
        rightContainer = findViewById(R.id.right_column);
        setCount(1);
    }

    public ImageView getImageView(int index)
    {
        if(index > 3)
            throw new IllegalArgumentException("Avatar view have 4 users");
        userAvatars[index].setVisibility(VISIBLE);
        return userAvatars[index];
    }

    public void setCount(int i) {
        switch (i)
        {
            case 1:
                userAvatars[1].setVisibility(GONE);
                userAvatars[2].setVisibility(GONE);
                userAvatars[3].setVisibility(GONE);
                rightContainer.setVisibility(GONE);
                userAvatars[0].setCornerRadiiDP(getWidth()/2, getWidth()/2,getWidth()/2,getWidth()/2);
                break;
            case 2:
                userAvatars[1].setVisibility(VISIBLE);
                userAvatars[2].setVisibility(GONE);
                userAvatars[3].setVisibility(GONE);
                rightContainer.setVisibility(VISIBLE);

                userAvatars[0].setCornerRadiiDP(getWidth(), 0, getWidth(), 0);
                userAvatars[1].setCornerRadiiDP(0, getWidth(), 0, getWidth());
                break;

            case 3:
                userAvatars[1].setVisibility(VISIBLE);
                userAvatars[2].setVisibility(VISIBLE);
                userAvatars[3].setVisibility(GONE);
                rightContainer.setVisibility(VISIBLE);

                userAvatars[0].setCornerRadiiDP(getWidth(), 0,getWidth(),0);
                userAvatars[1].setCornerRadiiDP(0, getWidth()/2, 0, 0);
                userAvatars[2].setCornerRadiiDP(0, 0, 0, getWidth() / 2);
                break;

            case 4:
                userAvatars[1].setVisibility(VISIBLE);
                userAvatars[2].setVisibility(VISIBLE);
                userAvatars[3].setVisibility(VISIBLE);
                rightContainer.setVisibility(VISIBLE);

                userAvatars[0].setCornerRadiiDP(getWidth()/2, 0,0,0);
                userAvatars[1].setCornerRadiiDP(0, getWidth()/2, 0, 0);
                userAvatars[2].setCornerRadiiDP(0, 0, 0, getWidth()/2);
                userAvatars[3].setCornerRadiiDP(0, 0, getWidth()/2, 0);
                break;


        }
    }
}
