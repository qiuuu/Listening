package com.pangge.listenstudy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * Created by iuuu on 16/10/18.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.RecyclerViewHolder> {
    //ArrayAdapter<Course>
    private List<Course> courseList;
    private RecyclerViewHolder recyclerViewHolder;

    public static Course selected;
   // private TitleFragment tF;





    // private int resourceId;
   /* public CourseAdapter(Context context, int textViewResourceId, List<Course> objects){
        //super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;

    }*/
    public CourseAdapter(List<Course> objects) {
        this.courseList = objects;


    }

/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView courseTitle = (TextView)view.findViewById(R.id.course_text);

        courseTitle.setText(course.getTitle());
        return view;
    }*/

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final Course course = courseList.get(position);
        holder.titleName.setText(course.getTitle());


        RxView.clicks(holder.titleName)
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        String title = holder.titleName.getText().toString();
                        selected.setTitle(title);

                        Log.i(title,"----title---");
                        TitleFragment.changeView();


                       // tF.changeView();
                        Log.i(title,"--OK ----title---");





                        return Observable.just("sucess");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent,false);

        recyclerViewHolder = new RecyclerViewHolder(view);


        return recyclerViewHolder;
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView titleName;

        public RecyclerViewHolder(View view) {
            super(view);
            titleName = (TextView) view.findViewById(R.id.course_text);

        }
    }
}

