package com.masterpiece.cracking.aniboayo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cracking on 3/16/2017.
 */

public class FragmentFavorite extends Fragment{

    private PreferencesUtil sharedPreference;
    private RecyclerView recyclerView;
    private FavListAdapter favListAdapter;
    private List<Manga> mangaList;

    public FragmentFavorite(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_favorite,container,false);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) view.findViewById(R.id.fav_recycler_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        sharedPreference = new PreferencesUtil();
        mangaList = new ArrayList<Manga>();
        mangaList = sharedPreference.getFavorites(getActivity());

        if(mangaList==null){
            showAlert("존재하지 않음","애니 옆의 하트모양을 롱클릭하여 관심 애니를 추가하세요!");
        }else{
            if(mangaList.size()==0){
                showAlert("존재하지 않음","애니 옆의 하트모양을 롱클릭하여 관심 애니를 추가하세요!");
            }

            if(mangaList!=null){
                for(int i=0; i<mangaList.size(); i++){
                    Log.e("manga", String.valueOf(mangaList.get(i)));
                }
                favListAdapter = new FavListAdapter(getContext(),mangaList);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new MangaListActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(favListAdapter);

            }
        }

    }

    public void showAlert(String title, String message) {

        Activity activity = getActivity();

        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(),MainMenuActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
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
