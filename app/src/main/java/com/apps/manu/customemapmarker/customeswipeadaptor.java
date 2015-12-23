package com.apps.manu.customemapmarker;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Admin on 12/11/2015.

Costome adaptor for displaying the images in the view pager
 */
public class customeswipeadaptor extends PagerAdapter {
    private  int []  image_resources = {R.drawable.images1,R.drawable.image2,R.drawable.images3,R.drawable.images4,R.drawable.images6};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public  customeswipeadaptor(Context ctx){
        this.ctx= ctx;
    }
    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container1, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.swipeactivitylayout,container1,false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(image_resources[position]);
        ((ViewPager) container1).addView(itemView, 0);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
     container.removeView((View)object);

    }
}
