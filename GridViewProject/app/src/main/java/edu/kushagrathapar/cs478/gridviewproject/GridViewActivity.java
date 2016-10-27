package edu.kushagrathapar.cs478.gridviewproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewActivity extends AppCompatActivity {

    public static List<Integer> carImages = new ArrayList<>(
            Arrays.asList(R.drawable.lam1, R.drawable.lam2, R.drawable.lam3,
                    R.drawable.lam4, R.drawable.audi1, R.drawable.audi2,
                    R.drawable.audi3, R.drawable.audi4, R.drawable.mustang1,
                    R.drawable.mustang2, R.drawable.mustang3, R.drawable.mustang4,
                    R.drawable.ben1, R.drawable.ben2, R.drawable.ben3, R.drawable.ben4));

    public static List<String> carManufacturerNames = new ArrayList<>(Arrays.asList(
            "Lamborghini", "Lamborghini", "Lamborghini", "Lamborghini",
            "Audi", "Audi", "Audi", "Audi",
            "Ford", "Ford", "Ford", "Ford",
            "Bentley", "Bentley", "Bentley", "Bentley"
    ));

    public static Map<String, String> carManufacturerWebsiteMap = new HashMap<>();

    public static Map<String, List<String>> carDealerMap = new HashMap<>();

    public static final String GET_IMAGE_ID = "GET_IMAGE_ID";
    public static final String GET_IMAGE_POSITION = "GET_IMAGE_POSITION";

    public GridViewImageAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareMap();
        GridView gridView = (GridView) findViewById(R.id.gridView);
        registerForContextMenu(gridView);
        customAdapter = new GridViewImageAdapter(this, carImages, carManufacturerNames);
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showImage(position);
            }
        });
    }

    public void showImage(int position) {
        Integer imageId = (Integer) customAdapter.getItem(position);
        Intent customIntent = new Intent(GridViewActivity.this, ImageViewActivity.class);
        customIntent.putExtra(GET_IMAGE_ID, imageId);
        customIntent.putExtra(GET_IMAGE_POSITION, position);
        startActivity(customIntent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        int position = (int) info.id;
        String carName = carManufacturerNames.get(position);
        Uri browserUri = Uri.parse(carManufacturerWebsiteMap.get(carName));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
        final List<String> items = carDealerMap.get(carName);
        final CharSequence dealers[] = new CharSequence[items.size()];
        for (int i = 0; i < items.size(); i++) {
            dealers[i] = items.get(i);
        }
        switch (itemId) {
            case R.id.show_image:
                showImage(position);
                return true;
            case R.id.show_web_page:
                this.startActivity(browserIntent);
                return true;
            case R.id.show_dealers:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Car Dealers");
                builder.setItems(dealers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void prepareMap() {
        carManufacturerWebsiteMap.put(carManufacturerNames.get(0), "https://www.lamborghini.com/en-en/");
        carManufacturerWebsiteMap.put(carManufacturerNames.get(4), "https://www.audiusa.com/");
        carManufacturerWebsiteMap.put(carManufacturerNames.get(8), "http://www.ford.com/");
        carManufacturerWebsiteMap.put(carManufacturerNames.get(12), "https://www.bentleymotors.com/en.html");

        carDealerMap.put(carManufacturerNames.get(0), new ArrayList<>(Arrays.asList(
                "Bentley Gold Coast - 834 N Rush, Chicago, IL 60611",
                "Perillo Downers Grove - 330 OGDEN AVE, Downers Grove, IL 60515",
                "Luxury Auto Selection - 4580 N Elston Ave, Chicago, IL 60630"
        )));
        carDealerMap.put(carManufacturerNames.get(4), new ArrayList<>(Arrays.asList(
                "Fletcher Jones Audi - 1111 W. Clark Chicago, IL 60610",
                "Audi Morton Grove - 7000 Golf Rd, Morton Grove, IL 60053",
                "Audi Westmont - 276 East Ogden Avenue, Westmont, IL 60559"
        )));
        carDealerMap.put(carManufacturerNames.get(8), new ArrayList<>(Arrays.asList(
                "Metro Ford - 6455 S Western Ave, Chicago, IL 60636",
                "Hawk Ford - 6100 W 95th St, Oak Lawn, IL 60453-2784",
                "Bredemann Ford - 2038 N. Waukegan Rd, Glenview, IL 60025-1722"
        )));
        carDealerMap.put(carManufacturerNames.get(12), new ArrayList<>(Arrays.asList(
                "Bentley Gold Coast - 834 N Rush, Chicago, IL 60611",
                "Perillo Downers Grove - 330 OGDEN AVE, Downers Grove, IL 60515",
                "Greater Chicago Motors - 1850 North Elston Ave, Chicago, IL 60642"
        )));

    }
}
