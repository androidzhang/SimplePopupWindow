package com.zlx.popupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by zlx on 2017/7/12.
 */

public class PopupWindowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);


    }

    public void start(View view) {

        final CustomPopupWindow popupWindow = new CustomPopupWindow.Builder().setContext(this).setContentView(R.layout.bottom_pop)
                .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setFouse(true)
                .setOutSideCancel(true)
                .setAnimationStyle(R.style.popup_anim_style)
                .builder()
                .showAtLocation(R.layout.activity_popup, Gravity.BOTTOM, 0, 0);
        TextView tv_1 = (TextView) popupWindow.getItemView(R.id.tv_1);
        TextView tv_2 = (TextView) popupWindow.getItemView(R.id.tv_2);


        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopupWindowActivity.this, "取消被点击了", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }
}
