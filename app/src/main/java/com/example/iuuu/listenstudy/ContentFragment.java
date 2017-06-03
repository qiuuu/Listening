package com.example.iuuu.listenstudy;

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

import com.jakewharton.rxbinding2.view.RxView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by iuuu on 16/10/18.
 */
public class ContentFragment extends Fragment{
    private View contentView;
    private MainActivity mainActivity;
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    @BindView(R.id.play) Button play;
    @BindView(R.id.pause) Button pause;
    @BindView(R.id.stop) Button stop;
    //private Button play;
    //private Button pause;
    //private Button stop;
    private int pathResourceId;



    private MediaPlayer mediaPlayer = new MediaPlayer();

    private TextView textView;

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
/*
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
*/
        RxView.clicks(play)
                .flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if(!mediaPlayer.isPlaying()){
                            mediaPlayer.start();
                        }
                        return Observable.just("world");
                    }
                })
                .subscribe();
        RxView.clicks(pause)
                .flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                        }

                        return Observable.just("world");
                    }
                })
                .subscribe();
        RxView.clicks(stop)
                .flatMap(new Function<Object, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.reset();
                            initMediaPlayer();
                        }
                        return Observable.just("world");
                    }
                })
                .subscribe();

        initMediaPlayer();



        return contentView;

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
    }
}
