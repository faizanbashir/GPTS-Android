package com.godpowerturbo.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import api.ActionBarListActivity;
import api.Model;
import api.MySingleton;

public class CustomListAdapter extends BaseAdapter {

    private ActionBarListActivity activity;
    private LayoutInflater inflater;
    private List<Model> movieItems;
    ImageLoader imageLoader = MySingleton.getInstance().getImageLoader();

    public CustomListAdapter(ActionBarListActivity activity, List<Model> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = MySingleton.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        // getting movie data for the row
        Model m = movieItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        id.setText(m.getId());
        title.setText(m.getTitle());
        description.setText(m.getDescription());

        return convertView;
    }

}
