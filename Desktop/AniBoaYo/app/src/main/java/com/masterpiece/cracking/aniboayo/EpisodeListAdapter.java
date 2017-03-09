package com.masterpiece.cracking.aniboayo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Bitch on 2017-02-26.
 */

public class EpisodeListAdapter extends BaseAdapter {

    private ArrayList<EpisodeItem> itemList = new ArrayList<EpisodeItem>();

    public EpisodeListAdapter() {

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context mContext = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_episodes, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.episode_name);

        EpisodeItem episodeItem = itemList.get(position);

        title.setText(episodeItem.getTiteStr());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    public void addItem(String title) {
        EpisodeItem item = new EpisodeItem();

        item.setTiteStr(title);

        itemList.add(item);
    }
}
