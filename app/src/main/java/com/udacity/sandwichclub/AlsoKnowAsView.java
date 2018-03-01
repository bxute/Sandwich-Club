package com.udacity.sandwichclub;

import android.content.Context;
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

public class AlsoKnowAsView extends FrameLayout{

    private ViewGroup rootView;
    private List<String> otherNames;
    private Context mContext;

    public AlsoKnowAsView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public AlsoKnowAsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AlsoKnowAsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        otherNames = new ArrayList<>();
        View view = LayoutInflater.from(context).inflate(R.layout.container_view, this);
        rootView = view.findViewById(R.id.viewWrapper);
    }

    private void addViews() {

        for (int i = 0; i < otherNames.size(); i++) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.also_known_as_item_view,null);
            TextView ing_tv = view.findViewById(R.id.ingredient);
            ing_tv.setText(otherNames.get(i));
            rootView.addView(view, i,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public void setOtherNames(List<String> otherNames) {
        this.otherNames = otherNames;
        addViews();
    }

}
