/*
 * Copyright (c) 2017. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.parkingwang.vehiclekeyboard.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.parkingwang.vehiclekeyboard.KeyboardInputController;
import com.parkingwang.vehiclekeyboard.PopupKeyboard;
import com.parkingwang.vehiclekeyboard.view.InputView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private InputView mInputView;

    private final List<String> mTestNumber = new ArrayList<>();

    private final Random mRandom = new Random();

    private PopupKeyboard mPopupKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputView = findViewById(R.id.input_view);
        final Button lockTypeButton = findViewById(R.id.lock_type);

        mTestNumber.add("粤A12345");
        mTestNumber.add("粤BD12345");
        mTestNumber.add("粤C0");
        mTestNumber.add("粤");
        mTestNumber.add("WJ粤12345");
        mTestNumber.add("WJ粤12");

        // 创建弹出键盘
        mPopupKeyboard = new PopupKeyboard(this);
        // 弹出键盘内部包含一个KeyboardView，在此绑定输入两者关联。
        mPopupKeyboard.attach(mInputView, this);

        // KeyboardInputController提供一个默认实现的新能源车牌锁定按钮
        mPopupKeyboard.getController()
                .setDebugEnabled(true)
                .bindLockTypeProxy(new KeyboardInputController.ButtonProxyImpl(lockTypeButton) {
                    @Override
                    public void onNumberTypeChanged(boolean isNewEnergyType) {
                        super.onNumberTypeChanged(isNewEnergyType);
                        if (isNewEnergyType) {
                            lockTypeButton.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        } else {
                            lockTypeButton.setTextColor(getResources().getColor(android.R.color.black));
                        }
                    }
                });

    }

    public void onClick(View view) {
        int id = view.getId();
        // 切换键盘类型
        switch (id) {
            case R.id.test_number:
                final int idx = mRandom.nextInt(mTestNumber.size());
                if (idx == 3) {
                    mPopupKeyboard.getController()
                            .updateNumberLockType(mTestNumber.get(idx), true);
                } else {
                    mPopupKeyboard.getController()
                            .updateNumber(mTestNumber.get(idx));
                }
                break;
            case R.id.clear_number:
                mPopupKeyboard.getController()
                        .updateNumber("");
                break;
            case R.id.popup_keyboard:
                if (mPopupKeyboard.isShown()) {
                    findViewById(R.id.edit_text).setEnabled(true);
                    mPopupKeyboard.dismiss(MainActivity.this);
                }else{
                    findViewById(R.id.edit_text).setEnabled(false);
                    mPopupKeyboard.show(MainActivity.this);
                }
                break;
        }
    }
}
