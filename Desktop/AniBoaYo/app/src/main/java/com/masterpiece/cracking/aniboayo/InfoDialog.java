package com.masterpiece.cracking.aniboayo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Bitch on 2017-02-23.
 */

public class InfoDialog extends Dialog {

    private String contact = "9929kmj@naver.com";
    private ImageView app_icon;
    private TextView text_email;
    private Button exit;

    private Context mContext;
    private View.OnClickListener exitClickListener;
    private View.OnClickListener contactClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_info_dialog);

        text_email = (TextView) findViewById(R.id.txt_email);
        exit = (Button) findViewById(R.id.btn_close);
        app_icon = (ImageView)findViewById(R.id.app_icon);

        Animation startRotateAnimation = AnimationUtils.loadAnimation(mContext,R.anim.rotate_animaiton);
        app_icon.startAnimation(startRotateAnimation);
        if (exitClickListener != null && contactClickListener != null) {
            exit.setOnClickListener(exitClickListener);
            text_email.setOnClickListener(contactClickListener);
        } else if (exitClickListener != null && contactClickListener == null) {
            exit.setOnClickListener(exitClickListener);
        } else {

        }
    }

    public InfoDialog(Context context, View.OnClickListener exitClickListener, View.OnClickListener contactClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.exitClickListener = exitClickListener;
        this.contactClickListener = contactClickListener;
    }
}
