package com.masterpiece.cracking.aniboayo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Bitch on 2017-02-24.
 */

public class EpisodesOfMangaActivity extends AppCompatActivity {

    private String[] episode_names;
    private String[] episode_hrefs;
    private String manga_href;
    private String manga_name;
    private Source source;

    private ListView listview;
    private EpisodeListAdapter adapter;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_manga);

        dialog = new CustomProgressDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        manga_href = PreferencesUtil.getPreferences(this, "href");
        manga_name = PreferencesUtil.getPreferences(this, "manga_name");

        Log.e("hr",manga_href);
        Log.e("hr",manga_name);

        getSupportActionBar().setTitle(manga_name);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        adapter = new EpisodeListAdapter();

        loadEpisodes();

        listview = (ListView) findViewById(R.id.episode_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferencesUtil.setPreferences(EpisodesOfMangaActivity.this, "episode_url", episode_hrefs[position]);
                Intent intent = new Intent(EpisodesOfMangaActivity.this, ViewMangaActivity.class);
                startActivity(intent);
            }
        });

    }

    public void loadEpisodes() {
        class jerichoParsingTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    source = new Source(new URL(manga_href));
                    source.fullSequentialParse();

                    List<Element> divTags = source.getAllElementsByClass("contents");

                    Element con_el = divTags.get(5);
                    List<Element> aTags = con_el.getAllElements(HTMLElementName.A);

                    episode_hrefs = new String[aTags.size()];
                    episode_names = new String[aTags.size()];

                    for (int i = 0; i < aTags.size(); i++) {

                        episode_names[i] = aTags.get(i).getContent().getTextExtractor().toString();
                        episode_hrefs[i] = aTags.get(i).getAttributeValue("href");

                        Log.e("episodes data : ",episode_names[i]+episode_hrefs[i]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                for (int i = 0; i < episode_names.length; i++) {
                    adapter.addItem(episode_names[i]);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }
        jerichoParsingTask task = new jerichoParsingTask();
        task.execute();
    }
}
