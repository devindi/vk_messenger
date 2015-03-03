package com.devindi.vk.messenger.demo.activity;


import android.app.AlertDialog;
import android.os.Bundle;
import com.devindi.vk.messenger.demo.R;
import com.vk.sdk.*;
import com.vk.sdk.api.VKError;
import com.vk.sdk.dialogs.VKCaptchaDialog;

public class ConversationsActivity extends BaseVKActivity {

    private static final String[] sMyScope = new String[] {
            VKScope.NOHTTPS,
            VKScope.MESSAGES
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_list);
        VKSdk.initialize(sdkListener, "4792768");
        if (VKSdk.wakeUpSession())
            loadConversations();
        else
        {
            VKSdk.authorize(sMyScope, true, false);
        }
    }

    private void loadConversations()
    {

    }

    private final VKSdkListener sdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(sMyScope);
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
