package com.pangge.listenstudy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by iuuu on 16/10/18.
 */
public class ContentFragment extends Fragment implements RewardedVideoAdListener{
    private View contentView;
    private MainActivity mainActivity;
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    @BindView(R.id.play) Button play;
    @BindView(R.id.pause) Button pause;
    @BindView(R.id.stop) Button stop;
    @BindView(R.id.adPlayView)
    NativeExpressAdView adPlayView;
    //private Button play;
    //private Button pause;
    //private Button stop;
    private int pathResourceId;



    private MediaPlayer mediaPlayer = new MediaPlayer();

    private TextView textView;

    private RewardedVideoAd mAd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        contentView = inflater.inflate(R.layout.three_fragment,container,false);
        mainActivity = (MainActivity)getActivity();
       // play = (Button)contentView.findViewById(R.id.play);
       // pause = (Button)contentView.findViewById(R.id.pause);
       // stop = (Button)contentView.findViewById(R.id.stop);
        textView = (TextView) contentView.findViewById(R.id.content);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        dbHelper= mainActivity.getDbHelper();
        db = dbHelper.getWritableDatabase();

        ButterKnife.bind(this,contentView);
        addContent();
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("ACDB891B6E42D095DCF265B71DB1AA62") //5.0.0
               // .addTestDevice("CFAE95F79289C04794E8AB846FA7459F")
                //.addTestDevice("D42D68726F943CCF3ADE0D0345ACF0B2") xiaomi
                .build();
        adPlayView.loadAd(adRequest);


        mAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        RxView.clicks(play)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(o -> playMedia());
        RxView.clicks(pause)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(o -> pauseMedia());
        RxView.clicks(stop)
                .flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {

                        if(mAd.isLoaded()){
                            mAd.show();
                        }
                        return Observable.just("world");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(o -> stopMedia());

        initMediaPlayer();



        return contentView;

    }
    private void playMedia(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }

    }
    private void pauseMedia(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    private void stopMedia(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.reset();
            initMediaPlayer();
        }
    }
    private void loadRewardedVideoAd(){
        mAd.loadAd("ca-app-pub-5349410171010771/7510346360", new AdRequest.Builder().addTestDevice("D42D68726F943CCF3ADE0D0345ACF0B2").build());
    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(getContext(), "停止想干啥类，广告好看不", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    private void addContent(){
       // String course = mainActivity.getCourse();
        String course = CourseAdapter.selected.getTitle();
        String book = mainActivity.getBook();
       // String columns = "content";
        Cursor cursor = db.query(book, null, "title=?", new String[] {course}, null, null, null, null);
        while (cursor.moveToNext()) {
            //String content = cursor.getString(cursor.getColumnIndex("content"));
            String content_path = cursor.getString(cursor.getColumnIndex("content"));
            String video_path = cursor.getString(cursor.getColumnIndex("path"));

           // content_path = "content1_1";
            //Course course = new Course(content);

            Log.v("error: ","--"+mainActivity.getPackageName());
            try {

                int contentResourceId = contentView.getResources().getIdentifier("raw/" + content_path,
                        null, mainActivity.getPackageName());
                pathResourceId = contentView.getResources().getIdentifier("raw/" + video_path,
                        null, mainActivity.getPackageName());
                BufferedReader contentBufferReader = new BufferedReader(
                        new InputStreamReader(contentView.getResources().openRawResource(contentResourceId)));

                String result = "";
                String line = "";
                while ((line = contentBufferReader.readLine()) != null){
                    result += line ;

                }


                textView.setText(result);
                Log.v("inn","content:  "+ result);

            }catch (Exception e){
                e.printStackTrace();
            }

           //textView.setText(content);
        }
        cursor.close();
     //   String result = "";
      /*  try{
            InputStream inputStream = getResources().openRawResource(content);

            InputStreamReader inputReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputReader);

            // int length = inputStream.available();
           // byte[] buffer = new byte[length];
           // inputStream.read(buffer);
            textView.setText(bufferedReader.readLine());

        }catch (Exception e){
            e.printStackTrace();
        }*/

    }

    private void initMediaPlayer(){
        try {
            mediaPlayer = MediaPlayer.create(mainActivity,pathResourceId);
           // contentView.getResources().openRawResource(pathResourceId);
            //mediaPlayer.prepare();
            //mediaPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                break;
            case R.id.pause:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
            case R.id.stop:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mAd.destroy(getContext());
    }
}
