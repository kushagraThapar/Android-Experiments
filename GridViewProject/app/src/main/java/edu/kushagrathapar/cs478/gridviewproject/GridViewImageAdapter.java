package edu.kushagrathapar.cs478.gridviewproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kushagrathapar on 9/27/16.
 */

public class GridViewImageAdapter extends BaseAdapter {

    private static final int PADDING = 5;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private Activity customActivity;
    private List<Integer> carImages;
    private List<String> carManufacturerNames;

    GridViewImageAdapter(Activity customActivity, List<Integer> carImages, List<String> carManufacturerNames) {
        this.customActivity = customActivity;
        this.carImages = carImages;
        this.carManufacturerNames = carManufacturerNames;
    }

    @Override
    public int getCount() {
        return carImages.size();
    }

    @Override
    public Object getItem(int position) {
        return carImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater customInflator = customActivity.getLayoutInflater();
        CustomImageView customImageView;
        if (convertView == null) {
            convertView = customInflator.inflate(R.layout.single_thumbnail_view, null);
            customImageView = new CustomImageView();
            customImageView.thumbnailTextView = (TextView) convertView.findViewById(R.id.customTextView);
            customImageView.thumbnailImageView = (ImageView) convertView.findViewById(R.id.customImageView);
            convertView.setTag(customImageView);
        } else {
            customImageView = (CustomImageView) convertView.getTag();
        }
        customImageView.thumbnailTextView.setText(carManufacturerNames.get(position));
        customImageView.thumbnailImageView.setImageResource(carImages.get(position));
        customImageView.thumbnailImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        customImageView.thumbnailImageView.setLayoutParams(new RelativeLayout.LayoutParams(WIDTH, HEIGHT));
        customImageView.thumbnailImageView.setPadding(PADDING, PADDING, PADDING, PADDING);

        return convertView;
    }
}
