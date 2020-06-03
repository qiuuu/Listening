package com.pangge.listenstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;


/**
 * Created by iuuu on 16/10/18.
 */
public class BookFragment extends Fragment{

    //private View bookView;
    private MainActivity mainActivity;
    @BindView(R.id.button1)
    ImageButton button1;
    @BindView(R.id.button2)
    ImageButton button2;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.adBtn)
    ImageButton adBtn;

    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        View bookView = inflater.inflate(R.layout.one_fragment,container,false);
        mainActivity.setTitle("change title!");
        ButterKnife.bind(this, bookView);


        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-5349410171010771/2836604185");




        RxView.clicks(button1)
                .flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        mainActivity.setBook("book1");
                        return Observable.just("hello");
                    }
                })
                .subscribe(o -> changeView());
        RxView.clicks(button2)
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        mainActivity.setBook("book2");
                        return Observable.just("hello");
                    }
                })
                .subscribe(o -> changeView());
        RxView.clicks(adBtn)
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }

                        return Observable.just("hah");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();

       /* button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("info","bookFragment button1");
                mainActivity.setBook("book1");
                changeView();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setBook("book2");
                changeView();
            }
        });*/
        return bookView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fragment","onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        mInterstitialAd.loadAd(adRequest);
        Log.i("Fragment","onResume");

    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  button1 = (Button)getActivity().findViewById(R.id.button1);
       // button1.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (bookView.getId()){
                    case R.id.button1:
                        Log.v("info!!","bookFragment button1");

                        mainActivity.setBook("1");
                        changeView();
                        Log.v("info","bookFragment button1");
                        break;
                    case R.id.button2:
                        mainActivity.setBook("2");
                        changeView();
                        break;
                    default:
                        break;
                }
            }
        }).start();


    }*/

    private void changeView(){

        TitleFragment titleFragment = new TitleFragment();
      //  MainActivity.manager = getFragmentManager();
        /**
         * test count
         * */
        int count = MainActivity.manager.getBackStackEntryCount();



        FragmentTransaction transaction = MainActivity.manager.beginTransaction();
        transaction.hide(this);
        //transaction.add(R.id.book_fragment,titleFragment,"title");*/
        transaction.add(R.id.title_fragment,titleFragment);
        transaction.addToBackStack("title");
        //-->test
        //transaction.addToBackStack("title");
        transaction.commit();

    }

}

