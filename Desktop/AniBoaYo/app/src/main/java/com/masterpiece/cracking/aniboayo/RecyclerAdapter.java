package com.masterpiece.cracking.aniboayo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 2017-02-17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private int page = 0;
    private Context context;

    HeaderItem header;
    List<CardItem> listItems;
    String[] mangas;
    String[] hrefs;
    String[] images;

    public RecyclerAdapter(Context context, HeaderItem header, List<CardItem> listItems, String[] manga, String[] imgs, String[] href_mangas) {
        this.context = context;
        this.header = header;
        this.listItems = listItems;
        this.mangas = manga;
        this.images = imgs;
        this.hrefs = href_mangas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new VHHeader(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MangaListActivity.class);
                    context.startActivity(intent);
                }
            });
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHHeader) {
            final VHHeader VHheader = (VHHeader) holder;
            VHheader.mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            VHheader.mDemoSlider.setCustomIndicator(VHheader.indicator);
            VHheader.mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            VHheader.mDemoSlider.setDuration(5000);
            VHheader.mDemoSlider.addOnPageChangeListener(this);

            for(int i=0; i<mangas.length; i++){
                TextSliderView textSliderView = new TextSliderView(context);
                textSliderView.description(mangas[i]).error(R.drawable.not_found).image(images[i]).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra",mangas[i]);

                VHheader.mDemoSlider.addSlider(textSliderView);
            }

            VHheader.date.setText(header.getDate());
        } else if (holder instanceof VHItem) {
            CardItem currentItem = getItem(position - 1);
            VHItem VHitem = (VHItem) holder;
            Glide.with(context).load(currentItem.getImageUrl()).override(600, 200).fitCenter().error(R.drawable.not_found).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(((VHItem) holder).Image);
            VHitem.name.setText(currentItem.getName());
        }
    }

    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int postition) {
        return postition == 0;
    }

    private CardItem getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size() + 1;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        page = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        PreferencesUtil.setPreferences(context, "episode_url", hrefs[page]);
        Intent intent = new Intent(context, ViewMangaActivity.class);
        context.startActivity(intent);
    }

    class VHHeader extends RecyclerView.ViewHolder {

        TextView date;
        SliderLayout mDemoSlider;
        PagerIndicator indicator;

        public VHHeader(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.text_Date);
            this.mDemoSlider = (SliderLayout) itemView.findViewById(R.id.slider);
            this.indicator = (PagerIndicator) itemView.findViewById(R.id.custom_indicator);
        }
    }

    class VHItem extends RecyclerView.ViewHolder {
        TextView name;
        ImageView Image;

        public VHItem(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.card_name);
            this.Image = (ImageView) itemView.findViewById(R.id.bg_card);
        }
    }

}
