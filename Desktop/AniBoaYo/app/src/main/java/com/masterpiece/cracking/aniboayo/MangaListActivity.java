package com.masterpiece.cracking.aniboayo;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Bitch on 2017-02-23.
 */

public class MangaListActivity extends AppCompatActivity {

    int i = 0;
    private String[] backdropImg = new String[]{"https://images2.alphacoders.com/718/71840.jpg", "http://wallpapercave.com/wp/7McwKb7.jpg", "http://cdn.wallpapersafari.com/21/79/K7jUt9.jpg", "https://images2.alphacoders.com/516/516664.jpg"};
    private Source source;
    private String url = "http://zangsisi.net";
    private String[] mangas;
    private String[] href_mangas;
    private RecyclerView recyclerView;
    private MangasAdapter mangasAdapter;
    private List<Manga> mangaList;
    private CustomProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_main);

        dialog = new CustomProgressDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF010139));

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mangaList = new ArrayList<>();

        prepareMangas();

        try {
            Random rand = new Random();
            i = rand.nextInt(3);
            Glide.with(this).load(backdropImg[i]).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareMangas() {

        class jerichoParseTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    source = new Source(new URL(url));
                    source.fullSequentialParse();

                    Element manga_el = source.getElementById("manga-list");

                    List<Element> manga_list = manga_el.getAllElements(HTMLElementName.A);

                    mangas = new String[manga_list.size()];
                    href_mangas = new String[manga_list.size()];

                    for (int i = 0; i < manga_list.size(); i++) {
                        Element aTag = manga_list.get(i).getFirstElement(HTMLElementName.A);

                        mangas[i] = aTag.getContent().getTextExtractor().toString();
                        href_mangas[i] = aTag.getAttributeValue("href");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Manga manga;

                for (int i = 0; i < mangas.length; i++) {

                    if (mangas[i].contains("바키도")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnatalie.mu%2Fmedia%2Fcomic%2F1404%2Fextra%2Fnews_thumb_bakidou1.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("새벽의 연화")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2Fimages-na.ssl-images-amazon.com%2Fimages%2FI%2F91M3%252BjLDJiL._SL1500_.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("파이어 펀치")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/b4/b4fcdbc2bb5d80022ee853c7eb7aeb2b217604911dd47f372802f4e3f5d842b7.jpg?e=1495090392&k=_smwEG1m2pN3ncFkz3mGKQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("던전에서 만남을")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/7f/7fcd4acabea7da540d72323ebbe12bc5eed9baaa3983e4d169ee4aec7ab9ac6e.jpg?e=1494414432&k=xCA2l-KWTY1NOoIpS-scUw");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("노라가미")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fwww.moetron.com%2Fuploads%2F20130615_noragami02_resize.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("드래곤볼 슈퍼")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://images.techtimes.com/data/images/full/99020/dragon-ball-super-promo-art.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("메이저")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://farm6.static.flickr.com/5009/5268162951_79e747be89.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("죠죠리온")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://www.araki-jojo.com/941/img/1.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("킹덤")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://www.toride.com/~digxwa/xwablogimage/c_kingdom17.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("흑집사")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://ecx.images-amazon.com/images/I/51JSPRajY1L.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("아이앰어히어로")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/quickimage/a776150647ca32eab222e93465681b14.jpeg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("도쿄구울 re")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/imgr/Hk0gCl4.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("페어리테일")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F3.bp.blogspot.com%2F_zEaioqElN80%2FSpZbm74jvhI%2FAAAAAAAAAKI%2FioP_HgdTJv0%2Fs400%2FFairyTail04.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("마기")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/99/99464f46e9d33c7e241b7277b810d917574bbf3e2526fd0168a35eb4067c0409.jpg?e=1491949492&k=sbEe8X2sh3Y2ko_xHgNQzg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("하이큐")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fj-books.shueisha.co.jp%2Ficon%2Fhyoshi_haikyu7.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("은혼")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/b7/b7fc3c15bc2be43eaaf8c73c6dc7d054ba36e83835a515d85f55fa2c8c298fbd.png?e=1492975136&k=27fmv1S6B_SSphsPh4e-cQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("도박마")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/33/333428423086396c6971cbf8a152c96bc151d248c6973b002351124a23161737.jpg?e=1495180864&k=UJRQCV9Cw-ab8h7-liePAQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("원피스")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://myanimelist.cdn-dena.com/s/common/uploaded_files/1447745856-d1263c53574b445d10257a1e0ae4b89c.jpeg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("나의 히어로")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fs.kaskus.id%2Fimages%2F2015%2F07%2F30%2F5387846_20150730114906.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("식극의")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/a9/a91263c232984cc378d03456423cfce4bdb31e53c36d7816fb6419480c2a79ca.png?e=1496730682&k=qnQdklSdH32H5CyiCi7y9g");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("블랙 클로버")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/b2/b2945b78792fad7db3ab737d78a51d732d67544c42710c24438f17ef213c0e1d.jpg?e=1496703450&k=QYnsWSWIVBuxo_hG6lJf9g");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("블랙클로버")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/b2/b2945b78792fad7db3ab737d78a51d732d67544c42710c24438f17ef213c0e1d.jpg?e=1496703450&k=QYnsWSWIVBuxo_hG6lJf9g");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("보루토")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://i3.ruliweb.com/img/5/5/9/2/5592A13B4A590C001E");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("ACMA")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/quickimage/3830be316a65ba19ad4b4477a7de2ed4.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("3월의")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Ffile.thisisgame.com%2Fupload%2Ftboard%2Fuser%2F2010%2F11%2F23%2F20101123000836_9814.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("겁쟁이")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fgaagle.jp%2Fgagazine%2Fkiji_img%2F1717%2Fthum_m%2Fcdb7a1909fa0196ddb61b110.L.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("나는 마리")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/25/2517f80994bd1248eb463e68287e91c790bbef004c47562f9d7c76819eb30fb5.jpg?e=1493113860&k=5qi6tGHnwM-TneVkALQ7Mw");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("나는 친구가")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/67/67abdcaadc921e1a0fcb9a80653b03cad37f3ce4639b6a60255a2e8e4853eb26.jpg?e=1495421810&k=2PhIAkJzP4yOntsryNBXGg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("나루토")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2F67.media.tumblr.com%2Fd92dc6e0fec15335bbf734855a6c1e01%2Ftumblr_oezj5iof1F1tltr42o1_500.png");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("너의 이름은")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2Fpbs.twimg.com%2Fmedia%2FCutC2k1VMAEHbQr.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("노예구")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://3.bp.blogspot.com/-P74_sXNCZDE/VkiBKYMdkbI/AAAAAAAEICs/pFdBN6-Kk1k/s1600/pic_001.png");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("니세코이")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fimage.kyobobook.co.kr%2Fimages%2Fbook%2Flarge%2F089%2Fl9788968310089.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("다이아몬드 에이스")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/quickimage/4e6c9a6b1923bcacd118b8b5c249e41e.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("더 파이팅")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/f0/f0f19cb529865332cf679de0c85541d3bbbb32cbc6f89416d12674ef93b6cb6d.jpg?e=1494179446&k=MWZNAyV7ljUxIY2l5OF5oQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("도로헤도로")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/27/2786718a1e491c5c48b29afbf797028c96a4722daed0af12fa94cb0fab1d7685.jpg?e=1488211872&k=EM9Nzw96uyJXh-HSp16hfg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("도박묵시록 카이지")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnavicon.jp%2Fnimage2%2F001%2F994%2F0212_kaiji.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("도쿄구울")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/b3/b319cde166a6e36eaf7a277e01b65813f4c8980b69fce8990b0c2333b79a56e6.jpg?e=1491050816&k=Z8-q7kJejmIjdUvvm-6yVA");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("드래곤볼")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/a1/a140388bfd9148bffb22074ded0e2c5c47b21722426e2b32259c54a9795b3971.jpg?e=1494768816&k=EYtNeX1l_pn-xmibLs0f4w");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("디그레이맨")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2Fcdn.mirror.wiki%2Fhttps%3A%2F%2Fattachment.namu.wiki%2F3_71.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("디플래그")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/95/95ac189dfb2f4f1cf1dec0ef49521df484e012a48dd2d3ba199465c8c04badd6.jpg?e=1490917008&k=VC79gkzEqcYL0E37gYzfdA");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("리얼")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://thumbnail.image.rakuten.co.jp/@0_mall/mangazenkan/cabinet/m-comic/comic0041/ri-01.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("리쿠도")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fdwci.co.kr%2Fzupload%2Fbook%2Fbook_23654.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("모브사이코")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fecx.images-amazon.com%2Fimages%2FI%2F617SXLRa0gL._SX353_BO1%2C204%2C203%2C200_.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("바키")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnatalie.mu%2Fmedia%2Fcomic%2F1404%2Fextra%2Fnews_thumb_bakidou1.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("배가본드")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://3.bp.blogspot.com/-q8lJKVa7j9E/VhgCpZq_-gI/AAAAAAAENAU/zcYbYUPnlsE/s1600/001.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("베르세르크")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fimg1.ak.crunchyroll.com%2Fi%2Fspire3%2F307aa586b4c3b9fc88133c5a5989b7001392078010_full.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("블리치")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F66.media.tumblr.com%2F0cca6a580770cf0ee1b8dd6caa688ce9%2Ftumblr_ofj58qmfnB1ust6ggo1_500.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("빈란드사가")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fen%2Fthumb%2F5%2F51%2FVinland_Saga_volume_01_cover.jpg%2F230px-Vinland_Saga_volume_01_cover.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("사남")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnatalie.mu%2Fimages%2Fcomic%2Fja%2Fsp-shaman%2Fphoto02.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("사채꾼 우시지마")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F1.bp.blogspot.com%2F_LM8HL3MnkQE%2FTAtIkkz5_tI%2FAAAAAAAADL4%2F1hV9Vdrvea8%2Fs1600%2F%E9%97%87%E9%87%91%E3%82%A6%E3%82%B7%E3%82%B8%E3%83%9E%E3%81%8F%E3%82%93.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("사카모토입니다만")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fec2.images-amazon.com%2Fimages%2FI%2F51wRd7OZK3L._SS500_.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("소드 아트 온라인")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/7a/7a3fbf2686732c918aed46683ba45f818b3caeda85acd9173c304a4b4ea4d890.jpg?e=1488471338&k=WqcH_hCd9JlKUoJBtpClkw");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("아오오니")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://i.imgur.com/Jq0gIkZ.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("아이엠 어 히어로")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/quickimage/a776150647ca32eab222e93465681b14.jpeg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("아인")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/0b/0b7a290e31eeaebea089c9f047460989fbfbb7d40a1b77d553a52baf6eaed302.jpg?e=1488505676&k=TK90GZ6mQl4RzBdcywREXw");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("아카메가 벤다")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/e5/e58c3d0a3bc6ecf68830695bc1521aaf24fe52a1451a7f1d3cdde69cc14a1e68.jpg?e=1496601150&k=xfwTk_Y-Kgexq1xIHMMx0g");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("암살교실")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://i.imgur.com/ey1qoix.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("오늘부터 신령님")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/b1/b18da033a627714df2492331dd4e7fb465bb05043fffc8faa7eaf0d75d1e488e.jpg?e=1492302609&k=UVMupwj5YM0W1UiAEhOIZg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("요츠바랑!")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/ce/cea448a080cc9eb9bfa247ab02ed1a0302fc43c26feb89d3bbc529682efae681.png?e=1494356920&k=ogfOZW4-dbUqVqMSlTfdCg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("원펀맨")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/40/406aa8ba29b04d0da597ec6a31bbe0c0e82ec04e3addb3ba56ca8b9252ada033.jpg?e=1490361768&k=7eCRAc6iARzCRIkmaqndnQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("월간순정 노자키 군")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fimage.aladin.co.kr%2Fproduct%2F4115%2F57%2Fletslook%2F6000732089_f.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("월드 트리거")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/ac/ac6d313e0f7a5d3e7b9d84bc932e74c91a766411f9d8949caaafd669c4524244.jpg?e=1496019233&k=opQ6gbYQ3A3TcKHBaT01ZQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("은수저")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fwww.mygenerationweb.it%2Fmedia%2Fk2%2Fitems%2Fcache%2F884bd078062ee6bd157b294c752d7970_XL.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("일곱개의 대죄")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/9c/9caa7b5c9f3e4b799fcf7c90854d6cd6c32c89f4108552225e0bcbccdfc921c9.jpg?e=1488812563&k=LMCUVThVMYJCV-OYduVErg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("자살도")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/1d/1d62de09a4667f9e69a9426a28bc4d458f4ed7ed9074bc9b4b942913e65e3242.jpg?e=1495425620&k=RvCZjaL6ARNQ_LgZ6lAREg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("자이언트 킬링")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/1b/1b44715f09683f7686a4099543d779a41c55a0babaaa9235775f659844e5acb5.jpg?e=1488963761&k=DYHfcLQf6_I_Mdo9kEqMng");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("장난을 잘치는 타카기양")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://i.imgur.com/9fOQW0N.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("죠죠의 기묘한 모험")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/2b/2b2b4408a4d521737ac0226c92ce90b49fff1c9f475d371fe3dda1859ee798f9.jpg?e=1490787711&k=FLTs0QHPOTcsV23jSqS2pw");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("진격의 거인")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/36/36afd5efcc0c62ac958496f4962193b665576ed89927f1a6b0c52b5134a3c4cc.jpg?e=1495854997&k=dlbT4w3iMycUZkzQbEHgog");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("청의 엑소시스트")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/f0/f0c9cf7589bcbe2e9915269c8bdfa242d882eecec16ca6148a40c010e07c2ea1.jpg?e=1489305840&k=zdtcNrYfkYVJiBs06SZ4rQ");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("쿠로코의 농구")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Flivedoor.blogimg.jp%2Fnijimen%2Fimgs%2F5%2F2%2F52ecc3a0.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("테니스의 왕자")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/1c/1cea22f112882bf1b33e89e720bf4e1a2b7572fe5272e5fb19ea4d86a721b12f.jpg?e=1489452375&k=G6gLGic-vDD6GByBrSemtA");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("테라포마스")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/ac/ac596758356fb58b909dae402a7c51ad08262300863e95f530823055cf347c29.jpg?e=1493383294&k=ZB4r6TScabb6U_KoTRIoqA");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("토리코")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fa.mhcdn.net%2Fstore%2Fmanga%2F3660%2F326.0%2Fcompressed%2Fh002.jpg%3Fv%3D1433418725%2F.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("페어리 테일")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F3.bp.blogspot.com%2F_zEaioqElN80%2FSpZbm74jvhI%2FAAAAAAAAAKI%2FioP_HgdTJv0%2Fs400%2FFairyTail04.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("플래티넘 엔드")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/57/5743715db64f7079b88ebee041df3c6fc1c927c01265f8204f5a9f388de401ec.jpg?e=1496985694&k=-QP_hnwvh-bPC7LGnICm-A");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("피아노의 숲")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/quickimage/61bc57f02c056e594082f2d65afaa899.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("피안도")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/96/96dc2b16450601dbf77281cc719d5b884c28d996294e741f97638c587ba881d4.jpg?e=1491225120&k=irywVApzwahsxkV2Ompnow");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("해피니스")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://i.imgur.com/bmLKIcc.jpg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("헌터")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://marumaru.in/quickimage/cb42aed5d12793287cb221e562f58967.jpeg");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("화봉요원")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/de/de65800704d56f75f49d23593541cb9af250383643fa082e623825844495f71a.jpg?e=1490594032&k=C_BpFkFCfyem6RV6_JOLrA");
                        mangaList.add(manga);
                    } else if (mangas[i].contains("히스토리에")) {
                        manga = new Manga(i, mangas[i],href_mangas[i], "https://cdn.namuwikiusercontent.com/54/54650b5225c8d406d4f1efef06b42b1d9d38216464ca50a490d25c65e8095ecc.jpg?e=1495237032&k=57F07cG-6Wmz1qAElrXYcw");
                        mangaList.add(manga);
                    } else {
                        manga = new Manga(i, mangas[i],href_mangas[i], "http://pm1.narvii.com/5913/85985c94c77ada6ac0e137f58e25c13931f8b90d_hq.jpg");
                        mangaList.add(manga);
                    }
                }
                mangasAdapter = new MangasAdapter(MangaListActivity.this, mangaList);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MangaListActivity.this, 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mangasAdapter);

                dialog.dismiss();
            }
        }
        jerichoParseTask task = new jerichoParseTask();
        task.execute();

    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
