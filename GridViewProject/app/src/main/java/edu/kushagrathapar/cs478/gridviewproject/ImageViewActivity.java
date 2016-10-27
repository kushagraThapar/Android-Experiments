package edu.kushagrathapar.cs478.gridviewproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent customIntent = getIntent();

        int imageId = customIntent.getIntExtra(GridViewActivity.GET_IMAGE_ID, 0);
        int imagePosition = customIntent.getIntExtra(GridViewActivity.GET_IMAGE_POSITION, 0);
        ImageView customImageView = new ImageView(getApplicationContext());
        customImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imagePosition = v.getId();
                String carName = GridViewActivity.carManufacturerNames.get(imagePosition);
                String url = GridViewActivity.carManufacturerWebsiteMap.get(carName);
                openBrowser(url);
            }
        });
        customImageView.setImageResource(imageId);
        customImageView.setId(imagePosition);
        setContentView(customImageView);
    }

    public void openBrowser(String BROWSER_URL) {
        Uri browserUri = Uri.parse(BROWSER_URL);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
        this.startActivity(browserIntent);
    }
}
