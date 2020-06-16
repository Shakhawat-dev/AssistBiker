package com.metacoders.assistbiker.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.metacoders.assistbiker.Activities.ProductDetailActivity;
import com.metacoders.assistbiker.OrderDetailsActivity;
import com.metacoders.assistbiker.R;
import com.metacoders.assistbiker.models.CartModel;
import com.metacoders.assistbiker.models.ProductsModel;
import com.metacoders.assistbiker.models.Sent_Response_cart;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class past_order_list_adapter extends RecyclerView.Adapter <past_order_list_adapter.ViewHolder> {

    private  List<Sent_Response_cart> orders  = new ArrayList<>();
    List<CartModel> students = new ArrayList<>();
    private  Context context ;
    CartModel student ;

    public past_order_list_adapter(List<Sent_Response_cart> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public past_order_list_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_past_order, parent, false);
        past_order_list_adapter.ViewHolder viewHolder = new past_order_list_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull past_order_list_adapter.ViewHolder holder, final  int position) {

        Sent_Response_cart item = orders.get(position) ;


        holder.orderID.setText("Order Id : app_" + item.getOrder_id());
        holder.status.setText(item.getOrder_status());
        holder.textPrice.setText(item.getDue_amount()+ "");
        holder.date.setText("Orderd On : "+ item.getOrder_date());

        try{
         //   Gson g = new Gson();
         //   CartModel p = g.fromJson(item.getOrder_list(), CartModel.class) ;

       //     List<CartModel> list = Arrays.asList(new GsonBuilder().create().fromJson(item.getOrder_list(), CartModel.class));
       //     Read more: https://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html#ixzz6PWi7uYqm

            JSONArray jsonArray = new JSONArray(item.getOrder_list());
            for (int i = 0; i < jsonArray.length(); i++) {
                 student = new Gson().fromJson(jsonArray.get(i).toString(), CartModel.class);
                students.add(student);
            }
           holder.oriderCount.setText("" + students.size());


        }
        catch ( Exception e )
        {
            holder.oriderCount.setText("N/A");
            Log.d("TAG", "onBindViewHolder: " + e.getMessage() + "\n"+ item.getOrder_list() );
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context, OrderDetailsActivity.class);

                i.putExtra("list" , item.getOrder_list()) ;
                //  Toasty.warning(context , singleProduct.getProduct_title() , Toasty.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderID;
        public TextView textPrice;
        public TextView status;
        public TextView oriderCount;
        public TextView date;



        public ViewHolder(View view) {
            super(view);

            orderID = view.findViewById(R.id.order_id) ;
            status = view.findViewById(R.id.status);
            textPrice = view.findViewById(R.id.price);
            oriderCount = view.findViewById(R.id.count) ;
            date = view.findViewById(R.id.date);







        }
    }
}
