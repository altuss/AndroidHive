package com.irvin.gridview_layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Hp on 3/21/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all images in array
    public Integer[] mThumbIds = {
            R.drawable.boston, R.drawable.los_angeles, R.drawable.miami, R.drawable.newyork,
            R.drawable.san_francisco, R.drawable.seatlle, R.drawable.washington
    };

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);

        // declares that images should be cropped toward the center
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        // sets the height and width for the View
        // this ensures that, no matter the size of the drawable, each image is resized and cropped
        // to fit in these dimensions, as appropriate.
        imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
        return imageView;
    }
}
