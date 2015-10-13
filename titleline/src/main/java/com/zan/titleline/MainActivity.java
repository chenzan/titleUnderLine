package com.zan.titleline;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RadioGroup radioGroup;
    private ViewPager viewPager;
    private ImageView line;
    private Matrix matrix;
    private int left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        initListener();
        initDate();
    }
    private int screenWidth;
    private void init() {
        DisplayMetrics matrix = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(matrix);
        screenWidth = matrix.widthPixels;
    }

    private void initDate() {
        final List<View> views = new ArrayList<View>();
        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(this);
            textView.setText("第" + i + "个");
            views.add(textView);
        }

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        radioGroup.setOnCheckedChangeListener(new MyCheckedChangeListener());
    }

    private class MyCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

        }
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            System.out.println(screenWidth / 3 * positionOffset);

            int dx = (int)(left+position*(screenWidth/3)+(screenWidth/3)*positionOffset);
            matrix.setTranslate(dx, 0);
            line.setImageMatrix(matrix);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 初始化VIew
     */
    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        viewPager = (ViewPager) findViewById(R.id.vp);
        line = (ImageView) findViewById(R.id.line);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.id_category_selector);
        int bitmapWidth=bitmap.getWidth();
        left = (screenWidth/3-bitmapWidth)/2;
        matrix = new Matrix();
        matrix.setTranslate(left, 0);
        line.setImageMatrix(matrix);

    }


}
