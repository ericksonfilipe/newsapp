package com.apps.ericksonfilipe.newsapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>, SwipeRefreshLayout.OnRefreshListener {

    public static final int LOADER_ID = 0;

    private ListView newsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_listing);

        newsListView = (ListView) findViewById(R.id.books_list_view);
        noDataTextView = (TextView) findViewById(R.id.no_data_text_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(NewsActivity.this);

        getSupportLoaderManager().initLoader(LOADER_ID, null, NewsActivity.this);
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(NewsActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        if (data == null) {
            if (!Utils.hasInternetConnection(NewsActivity.this)) {
                Toast.makeText(NewsActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(NewsActivity.this, getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        } else if (data.isEmpty()) {
            noDataTextView.setVisibility(View.VISIBLE);
        } else {
            noDataTextView.setVisibility(View.GONE);
            newsListView.setAdapter(new BookListAdapter(NewsActivity.this, data));
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, NewsActivity.this);
    }
}
