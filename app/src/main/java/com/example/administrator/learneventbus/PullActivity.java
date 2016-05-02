package com.example.administrator.learneventbus;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/5/1.
 */
public class PullActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    PullToRefreshListView mListView;

    private ArrayAdapter<String> adapter = null;
    private String[] data = null;
    private Activity activity;
    private int index = 0;
    private List<String> listItem = new ArrayList<>();
    private Observable observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_activity);
        ButterKnife.bind(this);
        activity = this;
        data = getResources().getStringArray(R.array.dumy_name);
        listItem.addAll(Arrays.asList(data));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);


        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                Observable.timer(3, TimeUnit.SECONDS)
                        .map(new Func1<Long, String>() {
                            @Override
                            public String call(Long aLong) {
                                return "top";
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Toast.makeText(PullActivity.this, s, Toast.LENGTH_SHORT).show();
                                refreshView.onRefreshComplete();
                            }
                        });

            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                Observable.timer(3, TimeUnit.SECONDS)
                        .map(new Func1<Long, String>() {
                            @Override
                            public String call(Long aLong) {
                                return "bottom";
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Toast.makeText(PullActivity.this, s, Toast.LENGTH_SHORT).show();
                                refreshView.onRefreshComplete();
                            }
                        });

            }
        });
        mListView.setAdapter(adapter);
    }
}
