package com.masterpiece.cracking.aniboayo;

/**
 * Created by Bitch on 2017-02-26.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 2017-02-26.
 */

public class ViewListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ViewItem> data;
    private int layout;

    public ViewListAdapter(Context context, int layout, ArrayList<ViewItem> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getImgUri();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }

        ViewItem item = data.get(position);

        ImageView img = (ImageView)convertView.findViewById(R.id.img_manga);
        Glide.with(convertView.getContext()).load(item.getImgUri()).error(R.drawable.not_found).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
        Glide.get(convertView.getContext()).clearMemory();

        return convertView;
    }
}

