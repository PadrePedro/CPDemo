package com.example.pedro.cpdemo;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This fragment encapsulates the listview that queries and displays the network requests.
 *
 * Created by pedro on 1/31/15.
 */
public class RequestsFragment extends ListFragment implements UrlRequest.RequestStatusCallback {

    // requests being made
    private ArrayList<UrlRequest> requests;
    private Executor executor;
    private int threadCount;
    // maps data to view to be displayed
    private RequestsAdapter adapter;
    private Handler handler;

    /**
     * Requests url count times. Results are displayed in ListView in realtime.
     * @param url
     * @param count
     */
    public void getUrl(String url, int count, int threadCount) {
        getView().setVisibility(View.VISIBLE);
        if (executor == null || threadCount != this.threadCount) {
            this.threadCount = threadCount;
            executor = Executors.newFixedThreadPool(this.threadCount);
        }
        requests = new ArrayList<UrlRequest>();

        for (int i=0;i<count;i++) {
            UrlRequest request = new UrlRequest(url, i, this);
            requests.add(request);
            executor.execute(request);
        }
        adapter = new RequestsAdapter(getActivity(), R.layout.listview_row, requests);
        setListAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setVisibility(View.INVISIBLE);
        handler = new Handler();
    }

    @Override
    public void onRequestStarted() {

    }

    /**
     * Called when a request is completed.  We instruct the adapter to update the listview.
     */
    @Override
    public void onRequestStopped() {
        // must post message for processing since this is called on non-UI thread
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Handle user selection of request item
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String text = requests.get(position).getText();
        if (text != null) {
            // display result fragment only if text is not empty
            ResponseDialogFragment.showResponse((FragmentActivity) getActivity(), position, text);
        }
    }
}
