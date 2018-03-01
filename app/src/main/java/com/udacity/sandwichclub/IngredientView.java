package com.udacity.sandwichclub;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IngredientView extends FrameLayout {

    private ViewGroup rootView;
    private List<String> ingredients;
    private Context mContext;

    public IngredientView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public IngredientView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IngredientView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        ingredients = new ArrayList<>();
        View view = LayoutInflater.from(context).inflate(R.layout.container_view, this);
        rootView = view.findViewById(R.id.viewWrapper);
    }

    private void addViews() {
        for (int i = 0; i < ingredients.size(); i++) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.ingredients_item_view,null);
            TextView ing_tv = view.findViewById(R.id.ingredient);
            ing_tv.setText(ingredients.get(i));
            rootView.addView(view, i,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        addViews();
    }

}
