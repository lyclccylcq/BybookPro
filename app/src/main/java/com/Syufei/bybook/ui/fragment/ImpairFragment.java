package com.Syufei.bybook.ui.fragment;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Syufei.bybook.R;
import com.Syufei.bybook.databinding.ImpairFragmentBinding;
import com.Syufei.bybook.ui.ImpairActivity;
import com.Syufei.bybook.ui.ImpairOrderActivity;
import com.Syufei.bybook.util.CommonTools;
import com.Syufei.bybook.util.UITools;

import java.util.List;

public class ImpairFragment extends Fragment {
    private ImpairFragmentBinding impairFragmentBinding;
    private int curIndex = 0;//o:维修  1：保养
    private float posX;
    private float posY;
    private float curPosX;
    private float curPosY;
    private Vibrator vibrator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        impairFragmentBinding = ImpairFragmentBinding.inflate(inflater);
        List<Integer> res = UITools.getWidthAndHeight(getContext());
        vibrator = (Vibrator)getContext().getSystemService(getContext().VIBRATOR_SERVICE);

        impairFragmentBinding.impairFragBtn.setOnClickListener(view -> {
            Bundle bundle=new Bundle();
            bundle.putInt("impair_type",curIndex);
            if (curIndex==0){
                CommonTools.startActivity(getContext(),ImpairActivity.class,"impair_bundle",bundle);
            }else if (curIndex==1){
                CommonTools.startActivity(getContext(),ImpairActivity.class,"impair_bundle",bundle);
            }
        });
        int height= (int) (getContext().getResources().getDisplayMetrics().heightPixels*0.7);
        ViewGroup.LayoutParams params = impairFragmentBinding.impairFragTopImg.getLayoutParams();
        params.height=height;
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) impairFragmentBinding.impairFragBtn.getLayoutParams();
        lp.topMargin=height-160;
        impairFragmentBinding.impairFragTopImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return touch(view,motionEvent);
            }
        });
        for(int i=0;i<5;i++){
            View view=LayoutInflater.from(getContext()).inflate(R.layout.order_list_card,impairFragmentBinding.impairFragHistoryContainer,true);
        }
        impairFragmentBinding.impairFragHistoryMore.setOnClickListener(view->{
            CommonTools.startActivity(getContext(),ImpairOrderActivity.class);
        });
        return impairFragmentBinding.getRoot();
    }



    private boolean touch(View view,MotionEvent event){
        if (view.getId() != R.id.impair_frag_btn ) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    posX = event.getX();
                    posY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    curPosX = event.getX();
                    curPosY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:

                    if ((curPosX - posX > 0) && (Math.abs(curPosX - posX) > 25)) {
//                    Log.v(TAG,"向左滑动");
                        curIndex = curIndex == 0 ? 1 : 0;
                        impairFragmentBinding.impairFragTopImg.setImageDrawable(curIndex==0? getResources().getDrawable(R.drawable.impair_bg_1)
                                : getResources().getDrawable(R.drawable.caring_bg_1));
                        impairFragmentBinding.impairFragBtn.setText(curIndex==0? "维修预约" :"保养预约");
                        impairFragmentBinding.impairFragTopImpair.setTextSize(curIndex==0? 18:14);
                        impairFragmentBinding.impairFragTopCaring.setTextSize(curIndex==1? 18:14);
                        vibrator.vibrate(100);
                    } else if ((curPosX - posX < 0) && (Math.abs(curPosX - posX) > 25)) {
//                    Log.v(TAG,"向右滑动");
                        curIndex = curIndex == 0 ? 1 : 0;
                        impairFragmentBinding.impairFragTopImg.setImageDrawable(curIndex==0? getResources().getDrawable(R.drawable.impair_bg_1)
                                : getResources().getDrawable(R.drawable.caring_bg_1));
                        impairFragmentBinding.impairFragBtn.setText(curIndex==0? "维修预约" :"保养预约");
                        impairFragmentBinding.impairFragTopImpair.setTextSize(curIndex==0? 18:14);
                        impairFragmentBinding.impairFragTopCaring.setTextSize(curIndex==1? 18:14);
                        vibrator.vibrate(100);
                    }
                    break;
            }
            return true;
        }
        return false;
    }
}
