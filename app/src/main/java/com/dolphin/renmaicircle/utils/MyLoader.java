package com.dolphin.renmaicircle.utils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dolphin.renmaicircle.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Administrator on 2018/4/6.
 */

public class MyLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);
        Glide.with(context).load((String) path).into(imageView);
    }
    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//        simpleDraweeView.setle
        View inflate = LayoutInflater.from(context).inflate(R.layout.banner_simpledraweeview, null);
        ImageView iv = (ImageView) inflate.findViewById(R.id.iv);
        ((ViewGroup)inflate).removeAllViews();
        return iv;
    }
}
