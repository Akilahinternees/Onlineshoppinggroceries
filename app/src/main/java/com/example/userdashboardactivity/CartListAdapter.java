package com.example.userdashboardactivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<CartList> data;//modify here
    DatabaseReference deletedataref;

    public CartListAdapter(Context mContext, ArrayList<CartList> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();// # of items in your arraylist
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);// get the actual item
    }
    @Override
    public long getItemId(int id) {
        return id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.pendingorders_layout, parent,false);//modify here
            viewHolder = new ViewHolder();
            //modify this block of code
            viewHolder.name = (TextView) convertView.findViewById(R.id.Pname);
            viewHolder.quantitywithprice = (TextView) convertView.findViewById(R.id.Ppriceandquantity);
            viewHolder.totalamount = (TextView) convertView.findViewById(R.id.Ptotalprice);
            viewHolder.deleteproduct = (TextView) convertView.findViewById(R.id.RemoveProduct);
            //viewHolder.imageView=(ImageView) convertView.findViewById(R.id.imageView);
            //Up to here
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //MODIFY THIS BLOCK OF CODE
        CartList product = data.get(position);//modify here
        viewHolder.name.setText(product.getProductName());//modify here
        viewHolder.quantitywithprice.setText(product.getProductPrice()+"["+product.ProductQuantity+"]");//modify here
        viewHolder.totalamount.setText(product.getTotalPrice());//modify here
        //viewHolder.imageView.setImageResource(person.getImage());
        deletedataref = FirebaseDatabase.getInstance().getReference("CartLists").child("key");
        viewHolder.deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedataref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return convertView;

    }
    static class ViewHolder {
        TextView name;
        TextView quantitywithprice;
        TextView totalamount;
        TextView deleteproduct;

        //ImageView imageView;
    }


}
