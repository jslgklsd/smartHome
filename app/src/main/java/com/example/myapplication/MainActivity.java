package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    List<Fragment> fragments;
    FragmentPagerAdapter fragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);   // 容器

        // 数据源
        fragments = new ArrayList<>();
        fragments.add(new DeviceFragment());
        fragments.add(new QingjingFragment());
        fragments.add(new SoundFragment());

        // 适配器
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(fragmentPagerAdapter); // 适配器放到容器当中

        // fragment与选项卡联动
        RadioGroup rg = findViewById(R.id.rg);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rg.check(R.id.rb_device);
                        break;
                    case 1:
                        rg.check(R.id.rb_qingjing);
                        break;
                    case 2:
                        rg.check(R.id.rb_sound);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 单击radiobutton切换对应的fragment
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_device)
                    viewPager.setCurrentItem(0, false);
                else if(checkedId == R.id.rb_qingjing)
                    viewPager.setCurrentItem(1,false);
                else if(checkedId == R.id.rb_sound)
                    viewPager.setCurrentItem(2,false);
            }
        });
    }
}