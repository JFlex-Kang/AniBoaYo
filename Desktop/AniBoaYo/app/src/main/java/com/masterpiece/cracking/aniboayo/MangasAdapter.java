package com.masterpiece.cracking.aniboayo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitch on 2017-02-24.
 */

public class MangasAdapter extends RecyclerView.Adapter<MangasAdapter.MyViewholder> {

    private String[] manga_names;
    private Context mContext;
    private List<Manga> mangaList;
    private PreferencesUtil preferencesUtil;

    public class MyViewholder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView thumbnail,fav;

        public MyViewholder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            fav = (ImageView)itemView.findViewById(R.id.img_fav);

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

    public MangasAdapter(Context mContext, List<Manga> mangaList, String[] manga_names) {
        this.mContext = mContext;
        this.mangaList = mangaList;
        this.manga_names = manga_names;
        preferencesUtil = new PreferencesUtil();
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mangas, parent, false);

        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewholder holder, final int position) {

        Manga manga = mangaList.get(position);
        holder.title.setText(manga.getName());
        holder.itemView.setTag(holder);

        holder.fav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int pos = position;
                String tag = holder.fav.getTag().toString();
                if(tag.equalsIgnoreCase("fav_not")){
                    preferencesUtil.addFavorite(mContext,mangaList.get(pos));
                    Toast.makeText(mContext, mangaList.get(pos).getName()+"를 관심애니에 추가했습니다.", Toast.LENGTH_SHORT).show();
                    holder.fav.setTag("fav");
                    holder.fav.setImageResource(R.drawable.heart_filled);
                }else{
                    preferencesUtil.removeFavorite(mContext,mangaList.get(pos));
                    holder.fav.setTag("fav_not");
                    holder.fav.setImageResource(R.drawable.heart_not_filled);
                    Toast.makeText(mContext, mangaList.get(pos).getName()+"를 관심애니에서 제거했습니다.", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        Glide.with(mContext).load(manga.getImgUrl()).override(600, 160).thumbnail(0.1f).error(R.drawable.not_found).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);

        if(checkFavoriteItem(manga)){
            holder.fav.setImageResource(R.drawable.heart_filled);
            holder.fav.setTag("fav");
        }else{
            holder.fav.setImageResource(R.drawable.heart_not_filled);
            holder.fav.setTag("fav_not");
        }

    }

    public boolean checkFavoriteItem(Manga checkManga) {
        boolean check = false;
        ArrayList<Manga> favorites = preferencesUtil.getFavorites(mContext);

        if (favorites != null) {

            for(Manga manga : favorites){
                if(checkManga.equals(manga)){
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


    @Override
    public int getItemCount() {
        return mangaList.size();
    }
}
