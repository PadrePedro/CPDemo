package com.example.pedro.cpdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Renders the view for each request item.
 *
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
            // load new view if not being recycled
            convertView = View.inflate(getContext(), resource, null);
        }
        UrlRequest request = objects.get(position);
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(request.toString());
        return convertView;
    }
}
