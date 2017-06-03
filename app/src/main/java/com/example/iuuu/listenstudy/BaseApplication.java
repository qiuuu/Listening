package com.example.iuuu.listenstudy;

import android.app.Application;
import android.util.Log;

/**
 * Created by iuuu on 17/6/2.
 */

public class BaseApplication extends Application {
    //private BookFragment bookFragment;
    //private DbHelper dbHelper;
   // private TitleFragment titleFragment;
    //private ContentFragment contentFragment;

    private static Course selected;

    @Override
    public void onCreate() {
        super.onCreate();
        selected = new Course();
        //titleFragment = new TitleFragment();
        //BookFragment.

        Log.i("kiddd－－－－","-----");

        CourseAdapter.selected = selected;
    }

    public static Course getSelecedCourse(){
        return selected;
    }


}
