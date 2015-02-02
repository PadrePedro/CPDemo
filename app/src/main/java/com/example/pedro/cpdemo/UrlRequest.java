package com.example.pedro.cpdemo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Encapsulates the data to be tracked for each individual request
 *
 * Created by pedro on 1/31/15.
 */
public class UrlRequest implements Runnable {

    interface RequestStatusCallback {
        // called when starting to process request
        void onRequestStarted();
        // called when requested processed
        void onRequestStopped();
    }

    private String url;
    private int id;
    private long startTimestamp;
    private long stopTimestamp;
    private int httpStatus;
    private long bytes;
    private String text;
    private RequestStatusCallback callback;

    public UrlRequest(String url, int id, RequestStatusCallback callback) {
        this.url = url;
        this.id = id;
        this.callback = callback;
    }

    @Override
    public void run() {
        startTimestamp = System.currentTimeMillis();
        if (callback != null)
            callback.onRequestStarted();

        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpParameters, 1000 * 30);
        HttpClient httpclient = new DefaultHttpClient(httpParameters);
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpget);
            if (response != null) {
                text = EntityUtils.toString(response.getEntity(), "UTF-8");
                bytes = text.length();
                httpStatus = response.getStatusLine().getStatusCode();
            }
        }
        catch (IOException e) {

        }
        stopTimestamp = System.currentTimeMillis();
        if (callback != null)
            callback.onRequestStopped();
    }

    @Override
    public String toString() {
        if (stopTimestamp > 0)
            return String.format("Fetch %d: HTTP %d, %dms, %d bytes", id, httpStatus, getDuration(), getSize());
        return String.format("Fetch %d", id);
    }

    public long getDuration() {
        return stopTimestamp - startTimestamp;
    }

    public String getText() {
        return text;
    }

    public long getSize() {
        return bytes;
    }

    public int getHttpStatus () {
        return httpStatus;
    }

}
