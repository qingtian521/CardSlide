package com.stone.card;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stone.card.library.CardAdapter;
import com.stone.card.library.CardSlidePanel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟"}; // 12个人名

    private List<CardDataItem> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        final CardSlidePanel slidePanel = (CardSlidePanel) findViewById(R.id.image_slide_panel);

        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.i("Card", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.i("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);
        // 2. 绑定Adapter
        slidePanel.setAdapter(new MyCardAdapter(getApplicationContext(),dataList));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        }, 500);


        // 3. notifyDataSetChanged调用
        findViewById(R.id.notify_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void prepareDataList() {
       long start = System.currentTimeMillis();
        for (int j= 0 ;j<100;j++){
            for (int i = 0; i < 6; i++) {
                CardDataItem dataItem = new CardDataItem();
                dataItem.userName = names[i];
                dataItem.imagePath = imagePaths[i];
                dataItem.likeNum = (int) (Math.random() * 10);
                dataItem.imageNum = (int) (Math.random() * 6);
                dataList.add(dataItem);
            }
        }
        Log.i("prepareDataList", "data size"+dataList.size()+"prepareDataList: time="+(System.currentTimeMillis() -start));
    }

    private void appendDataList() {
        for (int i = 0; i < 6; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = "From Append";
            dataItem.imagePath = imagePaths[8];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }


}
