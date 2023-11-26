package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends Fragment {
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_device, container, false);

        List<Item> itemList = new ArrayList<>();
        String[] titles = {"彩灯1", "彩灯2", "彩灯3", "彩灯4", "彩灯5", "彩灯6", "开关1", "开关2", "开关3"};
        for (int i = 0; i < 9; i++) {
            if(i <= 5)
                itemList.add(new Item(R.drawable.led, titles[i]));
            else
                itemList.add(new Item(R.drawable.kaiguan, titles[i]));
        }

        ListView lv = rootView.findViewById(R.id.lv);
        MyListAdapter adapter = new MyListAdapter(getContext(), itemList);
        lv.setAdapter(adapter);

        return rootView;
    }
}

class Item {
    private int icon;
    private String title;

    public Item(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}

class MyListAdapter extends ArrayAdapter<Item> {
    public MyListAdapter(Context context, List<Item> itemList) {
        super(context, R.layout.list_item, itemList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Item item = getItem(position);

        ImageView iv = convertView.findViewById(R.id.iv);
        iv.setImageResource(item.getIcon());

        TextView tv = convertView.findViewById(R.id.tv);
        tv.setText(item.getTitle());

        Button btn = convertView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentIcon = btn.getBackground();
                Drawable btnIcon = getContext().getResources().getDrawable(R.drawable.off);

                if (currentIcon.getConstantState().equals(btnIcon.getConstantState()))
                    btn.setBackgroundResource(R.drawable.on);
                else {
                    btn.setBackgroundResource(R.drawable.off);
                }
            }
        });

        return convertView;
    }
}