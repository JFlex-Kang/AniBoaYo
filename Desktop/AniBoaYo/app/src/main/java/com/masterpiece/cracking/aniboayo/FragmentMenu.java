package com.masterpiece.cracking.aniboayo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Cracking on 3/16/2017.
 */

public class FragmentMenu extends Fragment{

    private Source source;
    public static final String CONNECTION_TEST_URL = "http://google.com";
    private String today;
    private String[] week = {"일", "월", "화", "수", "목", "금", "토"};
    private String[] today_mangas;
    private String[] href_mangas;
    private String url = "http://zangsisi.net";
    private String isOnline;
    private Boolean isNetworkConnected = false;
    private HeaderItem mHeader;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<CardItem> listItems;
    private CustomProgressDialog dialog;

    public FragmentMenu(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_menu,container,false);

        isNetworkConnected = isNetworkConnected();
        if(isNetworkConnected == true){
            isOnline(CONNECTION_TEST_URL,1000);
            isOnline = PreferencesUtil.getPreferences(getContext(),"isOnline");
            if(isOnline.equals("true")){

                AdView mAdView = (AdView) view.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);

                dialog = new CustomProgressDialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Calendar c = Calendar.getInstance();
                today = String.valueOf(c.get(Calendar.MONTH) + 1) + "월";
                today += String.valueOf(c.get(Calendar.DATE)) + "일";
                today += week[c.get(Calendar.DAY_OF_WEEK) - 1] + "요일";

                listItems = new ArrayList<CardItem>();
                recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);

                loadDailyAni();
            }else{
                errorAlert();
            }
        }else{
            errorAlert();
        }

        return view;
    }

    private void loadDailyAni() {
        class jerichoParsingTask extends AsyncTask<Void, Void, Void> {

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

                    Element recent_manga = source.getElementById("recent-manga");
                    Element today_manga = recent_manga.getAllElements(HTMLElementName.DIV).get(2);

                    List<Element> manga_list = today_manga.getAllElements(HTMLElementName.A);

                    today_mangas = new String[manga_list.size()];
                    href_mangas = new String[manga_list.size()];

                    for (int i = 0; i < manga_list.size(); i++) {
                        Element aTag = manga_list.get(i).getFirstElement(HTMLElementName.A);

                        today_mangas[i] = aTag.getContent().getTextExtractor().toString();
                        href_mangas[i] = aTag.getAttributeValue("href");

                    }

                    listItems.add(new CardItem("http://wallpapercave.com/wp/j0HybY4.jpg", "애니보기"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                String[] img = new String[today_mangas.length];
                for (int i = 0; i < today_mangas.length; i++) {

                    if (today_mangas[i].contains("바키도")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnatalie.mu%2Fmedia%2Fcomic%2F1404%2Fextra%2Fnews_thumb_bakidou1.jpg";
                    } else if (today_mangas[i].contains("새벽의 연화")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2Fimages-na.ssl-images-amazon.com%2Fimages%2FI%2F91M3%252BjLDJiL._SL1500_.jpg";
                    } else if (today_mangas[i].contains("파이어 펀치")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/b4/b4fcdbc2bb5d80022ee853c7eb7aeb2b217604911dd47f372802f4e3f5d842b7.jpg?e=1495090392&k=_smwEG1m2pN3ncFkz3mGKQ";
                    } else if (today_mangas[i].contains("던전에서 만남을")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/7f/7fcd4acabea7da540d72323ebbe12bc5eed9baaa3983e4d169ee4aec7ab9ac6e.jpg?e=1494414432&k=xCA2l-KWTY1NOoIpS-scUw";
                    } else if (today_mangas[i].contains("노라가미")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fwww.moetron.com%2Fuploads%2F20130615_noragami02_resize.jpg";
                    } else if (today_mangas[i].contains("드래곤볼 슈퍼")) {
                        img[i] = "http://images.techtimes.com/data/images/full/99020/dragon-ball-super-promo-art.jpg";
                    } else if (today_mangas[i].contains("메이저")) {
                        img[i] = "http://farm6.static.flickr.com/5009/5268162951_79e747be89.jpg";
                    } else if (today_mangas[i].contains("죠죠리온")) {
                        img[i] = "http://www.araki-jojo.com/941/img/1.jpg";
                    } else if (today_mangas[i].contains("킹덤")) {
                        img[i] = "http://www.toride.com/~digxwa/xwablogimage/c_kingdom17.jpg";
                    } else if (today_mangas[i].contains("흑집사")) {
                        img[i] = "http://ecx.images-amazon.com/images/I/51JSPRajY1L.jpg";
                    } else if (today_mangas[i].contains("아이앰어히어로")) {
                        img[i] = "http://marumaru.in/quickimage/a776150647ca32eab222e93465681b14.jpeg";
                    } else if (today_mangas[i].contains("도쿄구울 re")) {
                        img[i] = "http://marumaru.in/imgr/Hk0gCl4.jpg";
                    } else if (today_mangas[i].contains("페어리테일")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F3.bp.blogspot.com%2F_zEaioqElN80%2FSpZbm74jvhI%2FAAAAAAAAAKI%2FioP_HgdTJv0%2Fs400%2FFairyTail04.jpg";
                    } else if (today_mangas[i].contains("마기")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/99/99464f46e9d33c7e241b7277b810d917574bbf3e2526fd0168a35eb4067c0409.jpg?e=1491949492&k=sbEe8X2sh3Y2ko_xHgNQzg";
                    } else if (today_mangas[i].contains("하이큐")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fj-books.shueisha.co.jp%2Ficon%2Fhyoshi_haikyu7.jpg";
                    } else if (today_mangas[i].contains("은혼")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/b7/b7fc3c15bc2be43eaaf8c73c6dc7d054ba36e83835a515d85f55fa2c8c298fbd.png?e=1492975136&k=27fmv1S6B_SSphsPh4e-cQ";
                    } else if (today_mangas[i].contains("도박마")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/33/333428423086396c6971cbf8a152c96bc151d248c6973b002351124a23161737.jpg?e=1495180864&k=UJRQCV9Cw-ab8h7-liePAQ";
                    } else if (today_mangas[i].contains("원피스")) {
                        img[i] = "https://myanimelist.cdn-dena.com/s/common/uploaded_files/1447745856-d1263c53574b445d10257a1e0ae4b89c.jpeg";
                    } else if (today_mangas[i].contains("나의 히어로")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fs.kaskus.id%2Fimages%2F2015%2F07%2F30%2F5387846_20150730114906.jpg";
                    } else if (today_mangas[i].contains("식극의")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/a9/a91263c232984cc378d03456423cfce4bdb31e53c36d7816fb6419480c2a79ca.png?e=1496730682&k=qnQdklSdH32H5CyiCi7y9g";
                    } else if (today_mangas[i].contains("블랙 클로버")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/b2/b2945b78792fad7db3ab737d78a51d732d67544c42710c24438f17ef213c0e1d.jpg?e=1496703450&k=QYnsWSWIVBuxo_hG6lJf9g";
                    } else if (today_mangas[i].contains("블랙클로버")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/b2/b2945b78792fad7db3ab737d78a51d732d67544c42710c24438f17ef213c0e1d.jpg?e=1496703450&k=QYnsWSWIVBuxo_hG6lJf9g";
                    } else if (today_mangas[i].contains("보루토")) {
                        img[i] = "http://i3.ruliweb.com/img/5/5/9/2/5592A13B4A590C001E";
                    }  else if (today_mangas[i].contains("ACMA")) {
                        img[i] = "http://marumaru.in/quickimage/3830be316a65ba19ad4b4477a7de2ed4.jpg";
                    } else if (today_mangas[i].contains("3월의")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Ffile.thisisgame.com%2Fupload%2Ftboard%2Fuser%2F2010%2F11%2F23%2F20101123000836_9814.jpg";
                    } else if (today_mangas[i].contains("겁쟁이")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fgaagle.jp%2Fgagazine%2Fkiji_img%2F1717%2Fthum_m%2Fcdb7a1909fa0196ddb61b110.L.jpg";
                    } else if (today_mangas[i].contains("나는 마리")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/25/2517f80994bd1248eb463e68287e91c790bbef004c47562f9d7c76819eb30fb5.jpg?e=1493113860&k=5qi6tGHnwM-TneVkALQ7Mw";
                    } else if (today_mangas[i].contains("나는 친구가")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/67/67abdcaadc921e1a0fcb9a80653b03cad37f3ce4639b6a60255a2e8e4853eb26.jpg?e=1495421810&k=2PhIAkJzP4yOntsryNBXGg";
                    } else if (today_mangas[i].contains("나루토")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2F67.media.tumblr.com%2Fd92dc6e0fec15335bbf734855a6c1e01%2Ftumblr_oezj5iof1F1tltr42o1_500.png";
                    } else if (today_mangas[i].contains("너의 이름은")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2Fpbs.twimg.com%2Fmedia%2FCutC2k1VMAEHbQr.jpg";
                    } else if (today_mangas[i].contains("노예구")) {
                        img[i] = "http://3.bp.blogspot.com/-P74_sXNCZDE/VkiBKYMdkbI/AAAAAAAEICs/pFdBN6-Kk1k/s1600/pic_001.png";
                    } else if (today_mangas[i].contains("니세코이")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fimage.kyobobook.co.kr%2Fimages%2Fbook%2Flarge%2F089%2Fl9788968310089.jpg";
                    } else if (today_mangas[i].contains("다이아몬드 에이스")) {
                        img[i] = "http://marumaru.in/quickimage/4e6c9a6b1923bcacd118b8b5c249e41e.jpg";
                    } else if (today_mangas[i].contains("더 파이팅")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/f0/f0f19cb529865332cf679de0c85541d3bbbb32cbc6f89416d12674ef93b6cb6d.jpg?e=1494179446&k=MWZNAyV7ljUxIY2l5OF5oQ";
                    } else if (today_mangas[i].contains("도로헤도로")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/27/2786718a1e491c5c48b29afbf797028c96a4722daed0af12fa94cb0fab1d7685.jpg?e=1488211872&k=EM9Nzw96uyJXh-HSp16hfg";
                    } else if (today_mangas[i].contains("도박묵시록 카이지")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnavicon.jp%2Fnimage2%2F001%2F994%2F0212_kaiji.jpg";
                    } else if (today_mangas[i].contains("도쿄구울")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/b3/b319cde166a6e36eaf7a277e01b65813f4c8980b69fce8990b0c2333b79a56e6.jpg?e=1491050816&k=Z8-q7kJejmIjdUvvm-6yVA";
                    } else if (today_mangas[i].contains("드래곤볼")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/a1/a140388bfd9148bffb22074ded0e2c5c47b21722426e2b32259c54a9795b3971.jpg?e=1494768816&k=EYtNeX1l_pn-xmibLs0f4w";
                    } else if (today_mangas[i].contains("디그레이맨")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/https%3A%2F%2Fcdn.mirror.wiki%2Fhttps%3A%2F%2Fattachment.namu.wiki%2F3_71.jpg";
                    } else if (today_mangas[i].contains("디플래그")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/95/95ac189dfb2f4f1cf1dec0ef49521df484e012a48dd2d3ba199465c8c04badd6.jpg?e=1490917008&k=VC79gkzEqcYL0E37gYzfdA";
                    } else if (today_mangas[i].contains("리얼")) {
                        img[i] = "https://thumbnail.image.rakuten.co.jp/@0_mall/mangazenkan/cabinet/m-comic/comic0041/ri-01.jpg";
                    } else if (today_mangas[i].contains("리쿠도")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fdwci.co.kr%2Fzupload%2Fbook%2Fbook_23654.jpg";
                    } else if (today_mangas[i].contains("모브사이코")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fecx.images-amazon.com%2Fimages%2FI%2F617SXLRa0gL._SX353_BO1%2C204%2C203%2C200_.jpg";
                    } else if (today_mangas[i].contains("바키")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnatalie.mu%2Fmedia%2Fcomic%2F1404%2Fextra%2Fnews_thumb_bakidou1.jpg";
                    } else if (today_mangas[i].contains("배가본드")) {
                        img[i] = "http://3.bp.blogspot.com/-q8lJKVa7j9E/VhgCpZq_-gI/AAAAAAAENAU/zcYbYUPnlsE/s1600/001.jpg";
                    } else if (today_mangas[i].contains("베르세르크")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fimg1.ak.crunchyroll.com%2Fi%2Fspire3%2F307aa586b4c3b9fc88133c5a5989b7001392078010_full.jpg";
                    } else if (today_mangas[i].contains("블리치")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F66.media.tumblr.com%2F0cca6a580770cf0ee1b8dd6caa688ce9%2Ftumblr_ofj58qmfnB1ust6ggo1_500.jpg";
                    } else if (today_mangas[i].contains("빈란드사가")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fen%2Fthumb%2F5%2F51%2FVinland_Saga_volume_01_cover.jpg%2F230px-Vinland_Saga_volume_01_cover.jpg";
                    } else if (today_mangas[i].contains("사남")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fnatalie.mu%2Fimages%2Fcomic%2Fja%2Fsp-shaman%2Fphoto02.jpg";
                    } else if (today_mangas[i].contains("사채꾼 우시지마")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F1.bp.blogspot.com%2F_LM8HL3MnkQE%2FTAtIkkz5_tI%2FAAAAAAAADL4%2F1hV9Vdrvea8%2Fs1600%2F%E9%97%87%E9%87%91%E3%82%A6%E3%82%B7%E3%82%B8%E3%83%9E%E3%81%8F%E3%82%93.jpg";
                    } else if (today_mangas[i].contains("사카모토입니다만")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fec2.images-amazon.com%2Fimages%2FI%2F51wRd7OZK3L._SS500_.jpg";
                    } else if (today_mangas[i].contains("소드 아트 온라인")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/7a/7a3fbf2686732c918aed46683ba45f818b3caeda85acd9173c304a4b4ea4d890.jpg?e=1488471338&k=WqcH_hCd9JlKUoJBtpClkw";
                    } else if (today_mangas[i].contains("아오오니")) {
                        img[i] = "http://i.imgur.com/Jq0gIkZ.jpg";
                    } else if (today_mangas[i].contains("아이엠 어 히어로")) {
                        img[i] = "http://marumaru.in/quickimage/a776150647ca32eab222e93465681b14.jpeg";
                    } else if (today_mangas[i].contains("아인")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/0b/0b7a290e31eeaebea089c9f047460989fbfbb7d40a1b77d553a52baf6eaed302.jpg?e=1488505676&k=TK90GZ6mQl4RzBdcywREXw";
                    } else if (today_mangas[i].contains("아카메가 벤다")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/e5/e58c3d0a3bc6ecf68830695bc1521aaf24fe52a1451a7f1d3cdde69cc14a1e68.jpg?e=1496601150&k=xfwTk_Y-Kgexq1xIHMMx0g";
                    } else if (today_mangas[i].contains("암살교실")) {
                        img[i] = "http://i.imgur.com/ey1qoix.jpg";
                    } else if (today_mangas[i].contains("오늘부터 신령님")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/b1/b18da033a627714df2492331dd4e7fb465bb05043fffc8faa7eaf0d75d1e488e.jpg?e=1492302609&k=UVMupwj5YM0W1UiAEhOIZg";
                    } else if (today_mangas[i].contains("요츠바랑!")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/ce/cea448a080cc9eb9bfa247ab02ed1a0302fc43c26feb89d3bbc529682efae681.png?e=1494356920&k=ogfOZW4-dbUqVqMSlTfdCg";
                    } else if (today_mangas[i].contains("원펀맨")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/40/406aa8ba29b04d0da597ec6a31bbe0c0e82ec04e3addb3ba56ca8b9252ada033.jpg?e=1490361768&k=7eCRAc6iARzCRIkmaqndnQ";
                    } else if (today_mangas[i].contains("월간순정 노자키 군")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fimage.aladin.co.kr%2Fproduct%2F4115%2F57%2Fletslook%2F6000732089_f.jpg";
                    } else if (today_mangas[i].contains("월드 트리거")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/ac/ac6d313e0f7a5d3e7b9d84bc932e74c91a766411f9d8949caaafd669c4524244.jpg?e=1496019233&k=opQ6gbYQ3A3TcKHBaT01ZQ";
                    } else if (today_mangas[i].contains("은수저")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fwww.mygenerationweb.it%2Fmedia%2Fk2%2Fitems%2Fcache%2F884bd078062ee6bd157b294c752d7970_XL.jpg";
                    } else if (today_mangas[i].contains("일곱개의 대죄")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/9c/9caa7b5c9f3e4b799fcf7c90854d6cd6c32c89f4108552225e0bcbccdfc921c9.jpg?e=1488812563&k=LMCUVThVMYJCV-OYduVErg";
                    } else if (today_mangas[i].contains("자살도")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/1d/1d62de09a4667f9e69a9426a28bc4d458f4ed7ed9074bc9b4b942913e65e3242.jpg?e=1495425620&k=RvCZjaL6ARNQ_LgZ6lAREg";
                    } else if (today_mangas[i].contains("자이언트 킬링")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/1b/1b44715f09683f7686a4099543d779a41c55a0babaaa9235775f659844e5acb5.jpg?e=1488963761&k=DYHfcLQf6_I_Mdo9kEqMng";
                    } else if (today_mangas[i].contains("장난을 잘치는 타카기양") || today_mangas[i].contains("장난을 잘치는 타카기 양")) {
                        img[i] = "http://i.imgur.com/9fOQW0N.jpg";
                    } else if (today_mangas[i].contains("죠죠의 기묘한 모험")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/2b/2b2b4408a4d521737ac0226c92ce90b49fff1c9f475d371fe3dda1859ee798f9.jpg?e=1490787711&k=FLTs0QHPOTcsV23jSqS2pw";
                    } else if (today_mangas[i].contains("진격의 거인")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/36/36afd5efcc0c62ac958496f4962193b665576ed89927f1a6b0c52b5134a3c4cc.jpg?e=1495854997&k=dlbT4w3iMycUZkzQbEHgog";
                    } else if (today_mangas[i].contains("청의 엑소시스트")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/f0/f0c9cf7589bcbe2e9915269c8bdfa242d882eecec16ca6148a40c010e07c2ea1.jpg?e=1489305840&k=zdtcNrYfkYVJiBs06SZ4rQ";
                    } else if (today_mangas[i].contains("쿠로코의 농구")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Flivedoor.blogimg.jp%2Fnijimen%2Fimgs%2F5%2F2%2F52ecc3a0.jpg";
                    } else if (today_mangas[i].contains("테니스의 왕자")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/1c/1cea22f112882bf1b33e89e720bf4e1a2b7572fe5272e5fb19ea4d86a721b12f.jpg?e=1489452375&k=G6gLGic-vDD6GByBrSemtA";
                    } else if (today_mangas[i].contains("테라포마스")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/ac/ac596758356fb58b909dae402a7c51ad08262300863e95f530823055cf347c29.jpg?e=1493383294&k=ZB4r6TScabb6U_KoTRIoqA";
                    } else if (today_mangas[i].contains("토리코")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2Fa.mhcdn.net%2Fstore%2Fmanga%2F3660%2F326.0%2Fcompressed%2Fh002.jpg%3Fv%3D1433418725%2F.jpg";
                    } else if (today_mangas[i].contains("페어리 테일")) {
                        img[i] = "https://image-proxy.namuwikiusercontent.com/r/http%3A%2F%2F3.bp.blogspot.com%2F_zEaioqElN80%2FSpZbm74jvhI%2FAAAAAAAAAKI%2FioP_HgdTJv0%2Fs400%2FFairyTail04.jpg";
                    } else if (today_mangas[i].contains("플래티넘 엔드")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/57/5743715db64f7079b88ebee041df3c6fc1c927c01265f8204f5a9f388de401ec.jpg?e=1496985694&k=-QP_hnwvh-bPC7LGnICm-A";
                    } else if (today_mangas[i].contains("피아노의 숲")) {
                        img[i] = "http://marumaru.in/quickimage/61bc57f02c056e594082f2d65afaa899.jpg";
                    } else if (today_mangas[i].contains("피안도")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/96/96dc2b16450601dbf77281cc719d5b884c28d996294e741f97638c587ba881d4.jpg?e=1491225120&k=irywVApzwahsxkV2Ompnow";
                    } else if (today_mangas[i].contains("해피니스")) {
                        img[i] = "http://i.imgur.com/bmLKIcc.jpg";
                    } else if (today_mangas[i].contains("헌터")) {
                        img[i] = "http://marumaru.in/quickimage/cb42aed5d12793287cb221e562f58967.jpeg";
                    } else if (today_mangas[i].contains("화봉요원")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/de/de65800704d56f75f49d23593541cb9af250383643fa082e623825844495f71a.jpg?e=1490594032&k=C_BpFkFCfyem6RV6_JOLrA";
                    } else if (today_mangas[i].contains("히스토리에")) {
                        img[i] = "https://cdn.namuwikiusercontent.com/54/54650b5225c8d406d4f1efef06b42b1d9d38216464ca50a490d25c65e8095ecc.jpg?e=1495237032&k=57F07cG-6Wmz1qAElrXYcw";
                    } else {
                        img[i] = "http://pm1.narvii.com/5913/85985c94c77ada6ac0e137f58e25c13931f8b90d_hq.jpg";
                    }
                }

                mHeader = new HeaderItem();
                mHeader.setDate(today);

                RecyclerAdapter adapter = new RecyclerAdapter(getContext(), mHeader, listItems, today_mangas, img, href_mangas);
                recyclerView.setAdapter(adapter);

                dialog.dismiss();
            }
        }
        jerichoParsingTask task = new jerichoParsingTask();
        task.execute();
    }

    //네트워크 상태 체크
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    //인터넷 상태 체크
    private void isOnline(final String url, final int timeout){

        class connectionTask extends AsyncTask<Void,Void,Void>{

            boolean isOnline = false;

            @Override
            protected Void doInBackground(Void... params) {
                try{
                    URL myUrl = new URL(url);
                    URLConnection conn = myUrl.openConnection();
                    conn.setConnectTimeout(timeout);
                    conn.connect();
                    isOnline = true;
                }catch (Exception e){
                    e.printStackTrace();
                    isOnline = false;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                PreferencesUtil.setPreferences(getContext(),"isOnline", String.valueOf(isOnline));
            }
        }
        connectionTask task = new connectionTask();
        task.execute();
    }

    public void errorAlert(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.dlg_not_connect,null);

        TextView txtTitle = (TextView)view.findViewById(R.id.title);
        txtTitle.setTextSize(20);
        txtTitle.setTextColor(Color.BLUE);
        txtTitle.setText("네트워크에 연결할 수 없음");

        TextView txtMsg = (TextView)view.findViewById(R.id.message);
        txtMsg.setTextSize(15);
        txtMsg.setText("네트워크 연결 후 다시 실행해보세요.");

        AlertDialog.Builder alt = new AlertDialog.Builder(getContext());
        alt.setView(view).setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });

        AlertDialog alert = alt.create();
        alert.show();

    }

}
