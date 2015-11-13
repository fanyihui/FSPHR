package com.fansen.phr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fansen.phr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/11/5.
 */
public class ClinicalDocumentCaptureImageAdapter extends BaseAdapter{
    private Context context;
    private List<ImageAdapterModel> images = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private View.OnClickListener onClickListener;

    public ClinicalDocumentCaptureImageAdapter(Context context, List<ImageAdapterModel> images, View.OnClickListener onClickListener){
        this.context = context;
        this.images = images;
        this.onClickListener = onClickListener;

        layoutInflater = LayoutInflater.from(context);
    }

    public void addImage(ImageAdapterModel imageAdapterModel){
        images.add(imageAdapterModel);
        notifyDataSetChanged();
    }

    public void removeImage(int position){
        images.remove(position);
        notifyDataSetChanged();
    }

    public void updateImage(int position, ImageAdapterModel imageAdapterModel){
        images.set(position, imageAdapterModel);
        notifyDataSetChanged();
    }

    public void setImages(List<ImageAdapterModel> images){
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageAdapterModel imageAdapterModel = images.get(position);

        ViewHolder viewHolder = null;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_clinical_doc_captured_image_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.capturedImageView = (ImageView) convertView.findViewById(R.id.id_clinical_doc_image);
            //viewHolder.capturedImageView.setOnClickListener(onClickListener);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.capturedImageView.setImageBitmap(imageAdapterModel.getThumbnailBitmap());

        return convertView;
    }

    public final class ViewHolder{
        public ImageView capturedImageView;
        //public TextView imageTitleTextView;
    }
}
