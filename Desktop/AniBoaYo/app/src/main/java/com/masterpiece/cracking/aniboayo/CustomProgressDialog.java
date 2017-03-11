package com.masterpiece.cracking.aniboayo;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by Cracking on 3/12/2017.
 */

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context mContext){
        super(mContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_loading);
    }
}
