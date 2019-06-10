package net.lzzy.kirinfm.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

/**
 * @author Administrator
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Picasso.get().load((String) path).into(imageView);
        //Glide.with(context).load(path).into(imageView);
    }
}
