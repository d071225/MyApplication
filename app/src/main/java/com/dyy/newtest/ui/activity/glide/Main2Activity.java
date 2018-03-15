package com.dyy.newtest.ui.activity.glide;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.img_glide)
    ImageView imgGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        RequestListener<String,GlideDrawable> listener=new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                LogUtils.e("model:"+model+";isFirstResource："+isFirstResource+";Exception:"+e);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                LogUtils.e("model:"+model+";isFromMemoryCache:"+isFromMemoryCache);
//                imgGlide.getWidth();
//                imgGlide.getHeight();
                LogUtils.e("imgGlide.getWidth:"+imgGlide.getWidth()+";imgGlide.getHeight:"+imgGlide.getHeight());
                return false;
            }
        };

        Glide.with(this).load("http://img.tuku.cn/file_big/201502/3d101a2e6cbd43bc8f395750052c8785.jpg").transform(new GlideRoundTransform(this,20)).listener(listener).placeholder(R.drawable.anim_loading_view).error(R.drawable.ic_arrow).animate(new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                AnimatorSet animatorSet=new AnimatorSet();
                animatorSet.play(ObjectAnimator.ofFloat(view,"rotation",0,720).setDuration(3000)).with(
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(5000)).
                        with(ObjectAnimator.ofFloat(view,"scaleX",0,1).setDuration(5000)).
                        with(ObjectAnimator.ofFloat(view,"scaleY",0,1).setDuration(5000));
                animatorSet.start();
//                Animation animation = AnimationUtils.loadAnimation(Main2Activity.this, R.anim.glide_image);
//                view.startAnimation(animation);
            }
        }).signature(new StringSignature(String.valueOf(System.currentTimeMillis()))).into(imgGlide);
    }
    /**
     * 将图像转换为四个角有弧度的图像
     */
    public class GlideRoundTransform extends BitmapTransformation {
        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 100);
        }

        public GlideRoundTransform(Context context, int dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            int size=Math.max(source.getHeight(),source.getWidth());
            float r=size/2;
            LogUtils.e(source.getWidth()+";"+ source.getHeight()+";"+r);
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            BitmapShader bitmapShader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            RadialGradient radialGradient=new RadialGradient(r,r,r, Color.TRANSPARENT,Color.WHITE, Shader.TileMode.CLAMP);
//            ComposeShader composeShader = new ComposeShader(bitmapShader, radialGradient, PorterDuff.Mode.SRC_OVER);
            paint.setShader(bitmapShader);
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
//            canvas.drawCircle(r,r,r,paint);
            Log.e("11aa", radius + "");
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }
}
