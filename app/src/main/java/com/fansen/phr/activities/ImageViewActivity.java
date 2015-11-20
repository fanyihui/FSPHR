package com.fansen.phr.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.fansen.phr.R;
import com.fansen.phr.fragment.details.PrescriptionFragment;
import com.fansen.phr.utils.FileUtil;
import com.fansen.phr.utils.ImageUtil;

public class ImageViewActivity extends AppCompatActivity {

    private String imagePath;
    private ImageView imageView;
    private boolean isGetWidth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();
        imageView = (ImageView) findViewById(R.id.id_full_image_view);

        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!isGetWidth) {
                    int height = imageView.getMeasuredHeight();
                    int width = imageView.getMeasuredWidth();

                    if (intent != null) {
                        imagePath = intent.getStringExtra(PrescriptionFragment.BUNDLE_KEY_VIEW_IMAGE);
                        Bitmap bitmap = FileUtil.getBitmap(imagePath);
                        Bitmap bm = ImageUtil.getBitmap(bitmap, width, height);
                        bitmap.recycle();
                        imageView.setImageBitmap(bm);
                    }

                    isGetWidth = true;
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
