package com.example.iuuu.listenstudy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;


/**
 * Created by iuuu on 16/10/18.
 */
public class TitleFragment extends Fragment {

    RecyclerView.Adapter<CourseAdapter.RecyclerViewHolder> adapter;
    RecyclerView.LayoutManager layoutManager;

    //private static FragmentManager manager;

    public MainActivity mainActivity;
    private List<Course> courseList = new ArrayList<Course>();
    private SQLiteDatabase db;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View titleView = inflater.inflate(R.layout.two_fragment,container,false);
      //  adapter = new CourseAdapter(getActivity(),R.layout.item_course,courseList);

        Log.i("－－－－－－2-－－－－","--OK ----title---");




       // contentFragment = new ContentFragment();
       // current = getTargetFragment();
        //getParentFragment().
        //manager = getFragmentManager();


        mainActivity = (MainActivity)getActivity();
        DbHelper dbHelper= mainActivity.getDbHelper();
        db = dbHelper.getWritableDatabase();
        addCourse();



        //Fragment fragment1 = manager.findFragmentById(0);
       // Fragment fragment2 = manager.findFragmentById(1);


      //  ListView listView = (ListView)titleView.findViewById(R.id.reView);
        RecyclerView recyclerView = (RecyclerView)titleView.findViewById(R.id.reView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mainActivity);
        adapter = new CourseAdapter(courseList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);





     //   Log.v("Title DBDBDB successful","777hhaa");


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Course course = courseList.get(position);
                Log.v("course",course.getTitle());
                mainActivity.setCourse(course.getTitle());
                changeView();
            }
        });
       RxRecyclerView.childAttachStateChangeEvents(recyclerView).subscribe(o -> changeView());
*/
        //changeView();
        return titleView;
    }

    private void addCourse() {
       // new Thread(new Runnable() {
      //      @Override
       //     public void run() {
                String book = mainActivity.getBook();

                Cursor cursor = db.query(book, null, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    Course course = new Course(title);
                    courseList.add(course);
                }
                cursor.close();

         //   }
     //   }).start();
    }

    public static void changeView(){

        FragmentTransaction transaction = MainActivity.manager.beginTransaction();
        ContentFragment contentFragment = new ContentFragment();


        //TitleFragment tF = new TitleFragment();
        Fragment current = MainActivity.manager.findFragmentById(R.id.title_fragment);


        //get current fragment--why
      /*  int count = MainActivity.manager.getBackStackEntryCount();
        // count =1
        Log.v("titleF--count",""+count);

        String tag = MainActivity.manager.getBackStackEntryAt(count-1).getName();
        Log.v("titleF--tag",tag);
        Fragment currentFragment = MainActivity.manager.findFragmentByTag(tag);

        transaction.hide(currentFragment);*/
        transaction.hide(current);
       // Log.v("successful--current",currentFragment.toString());
        //Log.v("successful--tF",tF.toString());
        transaction.add(R.id.content_fragment, contentFragment);
       // Log.v("titleF--tag","success");

        //transaction.show();
        transaction.addToBackStack("content");
        transaction.commit();

    }



}
