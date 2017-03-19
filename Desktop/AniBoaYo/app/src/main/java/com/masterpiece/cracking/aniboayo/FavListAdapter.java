package com.masterpiece.cracking.aniboayo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Cracking on 3/17/2017.
 */

public class FavListAdapter extends RecyclerView.Adapter<FavListAdapter.MyViewholder> {

    private Context mContext;
    private List<Manga> mangaList;
    private PreferencesUtil preferencesUtil;

    public class MyViewholder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView thumbnail;

        public MyViewholder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.fav_title);
            thumbnail = (ImageView)itemView.findViewById(R.id.fav_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Manga manga = mangaList.get(pos);
                    PreferencesUtil.setPreferences(mContext,"href",manga.getManga_url());
                    PreferencesUtil.setPreferences(mContext,"manga_name",manga.getName());
                    Intent intent = new Intent(mContext,EpisodesOfMangaActivity.class);
                    mContext.startActivity(intent);
                }
            });

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Manga manga = mangaList.get(pos);
                    PreferencesUtil.setPreferences(mContext,"href",manga.getManga_url());
                    PreferencesUtil.setPreferences(mContext,"manga_name",manga.getName());
                    Intent intent = new Intent(mContext,EpisodesOfMangaActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public FavListAdapter(Context mContext, List<Manga> mangaList){
        this.mContext = mContext;
        this.mangaList = mangaList;
        preferencesUtil = new PreferencesUtil();
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav,parent,false);
        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        Manga manga = mangaList.get(position);
        holder.title.setText(manga.getName());
        holder.itemView.setTag(holder);

        Glide.with(mContext).load(manga.getImgUrl()).override(600, 160).thumbnail(0.1f).error(R.drawable.not_found).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }
}
