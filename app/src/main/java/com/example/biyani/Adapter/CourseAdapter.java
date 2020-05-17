package com.example.biyani.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.biyani.CategoryActivity;
import com.example.biyani.Model.CourseModel;
import com.example.biyani.R;
import com.example.biyani.network.ApiService;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    ArrayList<CourseModel> answerItemArrayList;
    Context context;


    public CourseAdapter(Context context, ArrayList<CourseModel> answerItems) {
        this.context = context;
        this.answerItemArrayList = answerItems;
        //this.emailIds = emailIds;
        // this.orderid = mobileNumbers;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courseview, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(answerItemArrayList.get(position).getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CategoryActivity.class);
                i.putExtra("id",answerItemArrayList.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return answerItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = itemView.findViewById(R.id.name);
        }

    }

}

