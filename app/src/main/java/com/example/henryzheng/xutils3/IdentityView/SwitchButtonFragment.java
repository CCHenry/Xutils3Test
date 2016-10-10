package com.example.henryzheng.xutils3.IdentityView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.henryzheng.xutils3.BaseFragment;
import com.example.henryzheng.xutils3.R;
import com.example.henryzheng.xutils3.common.CCLog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.fragment_switch_button)
public class SwitchButtonFragment extends BaseFragment {
    @ViewInject(R.id.btn)
    private Button btn;
    Boolean select = false;
   static OnSwitchClickListner _switchOnClickListner;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Event(value = R.id.rl,type = View.OnClickListener.class)
    private void onclick(View view) {
        CCLog.print("change");

        _switchOnClickListner.onClick();
    }

    public static void setOnSwitchClickListener(OnSwitchClickListner switchOnClickListner) {
        _switchOnClickListner = switchOnClickListner;
    }

    public interface OnSwitchClickListner {
        public void onClick();
    }
}
