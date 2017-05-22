package com.ducleminh.lecorp.nasa_gallery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ducleminh.lecorp.nasa_gallery.R;
import com.ducleminh.lecorp.nasa_gallery.adapter.GalleryAdapter;
import com.ducleminh.lecorp.nasa_gallery.helper.CONFIG;
import com.ducleminh.lecorp.nasa_gallery.model.NasaModel;
import com.ducleminh.lecorp.nasa_gallery.service.NasaService;
import com.ducleminh.lecorp.nasa_gallery.service.ServiceGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Date mToday = new Date();
    private ArrayList<NasaModel> mItems;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar mCalendar = Calendar.getInstance();
    private RecyclerView mRecyclerView;
    private GalleryAdapter mGridAdapter;
    private NasaService mService;
    private boolean mIsLoading = true, mIsGrid = true, mIsOnSearching;
    private RecyclerView.LayoutManager mLayoutManager;
    private int mPastVisibleItems, mVisibleItemCount, mTotalItemCount, mVisibleThreshold = 2;
    private int mNumOfLoad = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViews();
    }

    private void initEndlessScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mIsOnSearching) {
                    if (dy >= 0) {
                        mVisibleItemCount = mLayoutManager.getChildCount();
                        mTotalItemCount = mLayoutManager.getItemCount();
                        if (mIsGrid)
                            mPastVisibleItems =
                                ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                        else mPastVisibleItems =
                            ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                        if (!mIsLoading) {
                            if ((mVisibleItemCount + mPastVisibleItems) >=
                                mTotalItemCount - mVisibleThreshold) {
                                fetchData();
                            }
                        }
                    }
                }
            }
        });
    }

    private void initViews() {
        mItems = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCalendar.setTime(mToday);
        mCalendar.add(Calendar.DATE, 1);
        mService = ServiceGenerator.createService(NasaService.class);
        mGridAdapter = new GalleryAdapter(this, mItems);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mGridAdapter);
        fetchData();
        initEndlessScroll();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void fetchData() {
        mIsLoading = true;
        for (int i = 0; i < mNumOfLoad; i++) {
            mCalendar.add(Calendar.DATE, -1);
            mService.getNasa(mDateFormat.format(mCalendar.getTime()), CONFIG.KEY)
                .enqueue(new Callback<NasaModel>() {
                    @Override
                    public void onResponse(Call<NasaModel> call, Response<NasaModel> response) {
                        if (response != null) {
                            NasaModel item = response.body();
                            if (item != null) {
                                if (item.getType().equals("image") && item.getTitle() != null) {
                                    mItems.add(item);
                                    mItems.sort(new Comparator<NasaModel>() {
                                        @Override
                                        public int compare(NasaModel o1, NasaModel o2) {
                                            try {
                                                // ngày gần đây nhất phải lên đầu danh sách -->
                                                // nhân -1 (đảo ngược lại)
                                                return -1 * mDateFormat.parse(o1.getDate())
                                                    .compareTo
                                                        (mDateFormat.parse(o2.getDate()));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            return 0;
                                        }
                                    });
                                    mGridAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<NasaModel> call, Throwable t) {
                    }
                });
        }
        mIsLoading = false;
    }
}
