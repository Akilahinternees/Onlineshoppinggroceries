package com.example.userdashboardactivity;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Uploadproducts> mUploads;
    private OnItemClickListener mListener;

    public ProductAdapter(Context mContext, List<Uploadproducts> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.allproducstforadmin,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Uploadproducts uploadCurrent = mUploads.get(position);
        holder.allid.setText(uploadCurrent.getKey());
        holder.AllNames.setText(uploadCurrent.getName());
        holder.Allprice.setText(uploadCurrent.getPrice()+" FRW");
        holder.Alldescription.setText(uploadCurrent.getDescription());
        Glide.with(mContext).load(uploadCurrent.getImage_url()).placeholder(R.mipmap.defaultimg)
                .centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView allid;
        public TextView AllNames;
        public TextView Allprice;
        public TextView Alldescription;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            AllNames = itemView.findViewById(R.id.allnames);
            allid = itemView.findViewById(R.id.product_id);
            Allprice = itemView.findViewById(R.id.allprice);
            Alldescription = itemView.findViewById(R.id.allquantity);
            imageView = itemView.findViewById(R.id.allproductPic);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
        // Handle Menu Items
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1,"Do Whatever");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){

                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setSearchOperation(List<Uploadproducts> newList){
        mUploads = new ArrayList<>();
        mUploads.addAll(newList);
        notifyDataSetChanged();
    }
}
