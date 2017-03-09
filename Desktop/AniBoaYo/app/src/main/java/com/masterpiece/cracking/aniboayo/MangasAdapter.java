package com.masterpiece.cracking.aniboayo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Bitch on 2017-02-24.
 */

public class MangasAdapter extends RecyclerView.Adapter<MangasAdapter.MyViewholder> {

    private String[] hrefs;
    private String[] manga_names;
    private Context mContext;
    private List<Manga> mangaList;

    public class MyViewholder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewholder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    PreferencesUtil.setPreferences(mContext,"href",hrefs[pos]);
                    PreferencesUtil.setPreferences(mContext,"manga_name",manga_names[pos]);
                    Intent intent = new Intent(mContext,EpisodesOfMangaActivity.class);
                    mContext.startActivity(intent);
                }
            });

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    PreferencesUtil.setPreferences(mContext,"href",hrefs[pos]);
                    PreferencesUtil.setPreferences(mContext,"manga_name",manga_names[pos]);
                    Intent intent = new Intent(mContext,EpisodesOfMangaActivity.class);
                    mContext.startActivity(intent);
                }
            });

            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    PreferencesUtil.setPreferences(mContext,"href",hrefs[pos]);
                    PreferencesUtil.setPreferences(mContext,"manga_name",manga_names[pos]);

                }
            });
        }
    }

    public MangasAdapter(Context mContext, List<Manga> mangaList, String[] mangas_hrefs, String[] manga_names) {
        this.mContext = mContext;
        this.mangaList = mangaList;
        this.hrefs = mangas_hrefs;
        this.manga_names = manga_names;
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
        Glide.with(mContext).load(manga.getImgUrl()).override(600, 160).thumbnail(0.1f).error(R.drawable.not_found).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);
        PreferencesUtil.setIntPreferences(mContext,"overflow_index",position);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = PreferencesUtil.getIntPreferences(mContext,"overflow_index");
                PreferencesUtil.setPreferences(mContext,"href",hrefs[pos]);
                PreferencesUtil.setPreferences(mContext,"manga_name",manga_names[pos]);
                showPopupMenu(holder.overflow);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_manga, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popupMenu.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.action_add_favorite:
                    Toast.makeText(mContext, PreferencesUtil.getPreferences(mContext,"manga_name")+" added to favorite!",Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }
}
