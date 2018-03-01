package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView descriptionTv;
    private TextView placeOfOriginTv;
    private AlsoKnowAsView alsoKnowAsCv;
    private IngredientView ingredientView;
    private ImageView imageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        descriptionTv  = findViewById(R.id.description_tv);
        placeOfOriginTv = findViewById(R.id.place_of_origin_tv);
        alsoKnowAsCv = findViewById(R.id.alsoKnownAsCv);
        ingredientView = findViewById(R.id.ingredients_cv);
        imageView = findViewById(R.id.image_iv);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        loadImage(sandwich.getImage());
        setTitle(sandwich.getMainName());

    }

    private void changeStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    private void loadImage(String imageUrl) {

        Picasso.with(this)
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        assert bitmap!=null;
                        imageView.setImageBitmap(bitmap);

                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                        if (textSwatch == null) {
                                            Toast.makeText(DetailActivity.this, "Null swatch :(", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        collapsingToolbarLayout.setContentScrimColor(textSwatch.getRgb());
                                        changeStatusBarColor(textSwatch.getRgb());
                                        toolbar.setTitleTextColor(textSwatch.getTitleTextColor());

                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        collapsingToolbarLayout.setTitleEnabled(false);
        collapsingToolbarLayout.setTitle(sandwich.getMainName());
        descriptionTv.setText(sandwich.getDescription());
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        ingredientView.setIngredients(sandwich.getIngredients());
        alsoKnowAsCv.setOtherNames(sandwich.getAlsoKnownAs());

    }
}
