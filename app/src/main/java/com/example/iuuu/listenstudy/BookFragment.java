package com.example.iuuu.listenstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * Created by iuuu on 16/10/18.
 */
public class BookFragment extends Fragment{

    //private View bookView;
    private MainActivity mainActivity;
    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        View bookView = inflater.inflate(R.layout.one_fragment,container,false);
        mainActivity.setTitle("change title!");
        Log.v("info","bookFragment");
        //Button button1 = (Button)bookView.findViewById(R.id.button1);
        //Button button2 = (Button)bookView.findViewById(R.id.button2);
        ButterKnife.bind(this, bookView);
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
        // count =1
        Log.v("book--count",""+count);

/*        String tag = MainActivity.manager.getBackStackEntryAt(count).getName();
        Log.v("successful--tag",tag);
        /**
         * test end
         */

        FragmentTransaction transaction = MainActivity.manager.beginTransaction();
        transaction.hide(this);
        //transaction.add(R.id.book_fragment,titleFragment,"title");*/
        transaction.add(R.id.title_fragment,titleFragment);
        transaction.addToBackStack("title");
        //-->test
        //transaction.addToBackStack("title");
        transaction.commit();
        int count2 = MainActivity.manager.getBackStackEntryCount();
        // count =1
        Log.v("book2--count",""+count2);

    }

}

