package com.masterpiece.cracking.aniboayo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitch on 2017-02-26.
 */

public class ViewMangaActivity extends AppCompatActivity {
    private Source source;
    private String[] image_hrefs;
    private String url;

    private ListView listview;
    private ArrayList<ViewItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_manga);

        listview = (ListView) findViewById(R.id.listview);
        data = new ArrayList<>();

        url = PreferencesUtil.getPreferences(this, "episode_url");

        loadMangaImages();
    }

    private void loadMangaImages() {
        class JSoupParseTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    source = new Source(new URL(url));
                    source.fullSequentialParse();

                    Element manga_el = source.getElementById("post");

                    List<Element> imgTags = manga_el.getAllElements(HTMLElementName.IMG);
                    image_hrefs = new String[imgTags.size()];
                    for(int i=0; i<imgTags.size(); i++){
                        image_hrefs[i] = imgTags.get(i).getAttributeValue("src");
                        System.out.println(image_hrefs[i]);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                for (int i = 0; i < image_hrefs.length; i++) {
                    ViewItem item = new ViewItem(image_hrefs[i]);
                    data.add(item);
                }

                ViewListAdapter adapter = new ViewListAdapter(ViewMangaActivity.this, R.layout.item_views, data);
                listview.setAdapter(adapter);
            }
        }
        JSoupParseTask task = new JSoupParseTask();
        task.execute();
    }

}
