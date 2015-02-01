package com.example.pedro.cpdemo;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;

import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by pedro on 1/31/15.
 */
public class RequestsFragment extends ListFragment implements UrlRequest.RequestStatusCallback {

    private ArrayList<UrlRequest> requests;
    private Executor executor;
    private RequestsAdapter adapter;
    private Handler handler;

    public void getUrl(String url, int count) {
        executor = Executors.newFixedThreadPool(10);
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
        handler = new Handler();
    }

    @Override
    public void onRequestStarted() {

    }

    @Override
    public void onRequestStopped() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
