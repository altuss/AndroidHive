package com.irvin.listview_loadmorebutton;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hp on 3/22/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String,String>> data;
    private static LayoutInflater inflater = null;

    public ListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        this.activity = activity;
        this.data = data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView id = (TextView) view.findViewById(R.id.id);
        TextView name = (TextView) view.findViewById(R.id.name);

        HashMap<String,String> item = new HashMap<String,String>();
        item = data.get(position);

        id.setText(item.get("id"));
        name.setText(item.get("name"));

        return view;
    }
}
