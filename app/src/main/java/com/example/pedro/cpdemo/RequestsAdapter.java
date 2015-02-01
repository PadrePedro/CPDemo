package com.example.pedro.cpdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by pedro on 1/31/15.
 */
public class RequestsAdapter extends ArrayAdapter<UrlRequest> {

    private List<UrlRequest> objects;
    private int resource;

    public RequestsAdapter(Context context, int resource, List<UrlRequest> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), resource, null);
        }
        UrlRequest request = objects.get(position);
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        if (request.getDuration() > 0)
            textViewItem.setText(String.format("Fetch %d: HTTP: %d, %dms, %d bytes", position, request.getHttpStatus(), request.getDuration(), request.getSize()));
        else
            textViewItem.setText(String.format("Fetch %d:", position));
        return convertView;
    }
}
