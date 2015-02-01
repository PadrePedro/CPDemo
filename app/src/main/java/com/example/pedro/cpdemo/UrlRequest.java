package com.example.pedro.cpdemo;

/**
 * Created by pedro on 1/31/15.
 */
public class UrlRequest implements Runnable {

    interface RequestStatusCallback {
        void onRequestStarted();
        void onRequestStopped();
    }

    private String url;
    private int id;
    private long startTimestamp;
    private long stopTimestamp;
    private int httpStatus;
    private int bytes;
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
        try {
            int sleep = (int)(Math.random() * 5000);
            Thread.sleep(sleep);
            httpStatus = 200;
            text = "Random text " + Integer.toString(sleep);
        }
        catch (InterruptedException e) {

        }
        stopTimestamp = System.currentTimeMillis();
        if (callback != null)
            callback.onRequestStopped();
    }

    @Override
    public String toString() {
        if (stopTimestamp > 0)
            return String.format("Fetch %d: HTTP %d, %dms, %d bytes");
        return "Fetch " + Integer.toString(id);
    }

    public long getDuration() {
        return stopTimestamp - startTimestamp;
    }

    public String getText() {
        return text;
    }

    public int getSize() {
        return text != null ? text.length() : 0;
    }

    public int getHttpStatus () {
        return httpStatus;
    }

}
