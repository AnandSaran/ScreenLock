package poc.anand.screenlock.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import poc.anand.screenlock.R;

/**
 * Created by Anand.S on 4/27/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private File[] mediaFiles;

    public ImageAdapter(File[] mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_image, viewGroup, false);
        return new ImageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        if (mediaFiles[position].exists()) {
            Glide.with(holder.itemView.getContext()).load(mediaFiles[position]).apply(new RequestOptions()
                    .dontAnimate()
                    .dontTransform()).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return mediaFiles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
