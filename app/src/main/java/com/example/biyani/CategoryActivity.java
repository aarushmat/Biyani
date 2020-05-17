package com.example.biyani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.biyani.Adapter.CategoryAdapter;
import com.example.biyani.Adapter.CourseAdapter;
import com.example.biyani.Model.CourseModel;
import com.example.biyani.network.ApiClient;
import com.example.biyani.network.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CategoryActivity extends AppCompatActivity {
    ApiService mService;
    ArrayList<CourseModel> coursearray1;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        recyclerView = findViewById(R.id.recycler);
        String id=getIntent().getStringExtra("id");
        getcategory(id);
    }
    private void getcategory(String id)
    {

//        dialoq1progress.setVisibility(View.VISIBLE);
        mService = ApiClient.getClient().create(ApiService.class);
        //Call<RegisterResponse> userCall=mService.register(new RegisterRequest(user_email,user_password,"false"));
        Call<ResponseBody> userCall = mService.getCategory(id);
        userCall.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response)
            {
                ResponseBody user = response.body();
                try {
                    String data = user.string();

                    JSONObject responsejobj=new JSONObject(data);
                    String stauts=responsejobj.getString("Status_Code");
                    String message=responsejobj.getString("Message");
                    String isActive=responsejobj.getString("isActive");
                    if(isActive.equalsIgnoreCase("false")){
                        Intent i = new Intent(CategoryActivity.this,SubjectActivity.class);
                        startActivity(i);
                        CategoryActivity.this.finish();
                    }
//                    progressBar_downloads.setVisibility(View.INVISIBLE);
                    if (stauts.equalsIgnoreCase("200")) {
//                        statearr = new ArrayList<>();
                        coursearray1 = new ArrayList<>();
                        JSONArray coursearray = responsejobj.getJSONArray("Data");
                        for (int i = 0; i < coursearray.length(); i++) {
                            JSONObject dataobj = coursearray.getJSONObject(i);
                            String CategoryId = dataobj.getString("CategoryId");
                            String CategoryName = dataobj.getString("CategoryName");
                            String DepartmentId = dataobj.getString("DepartmentId");
                            String CourseId = dataobj.getString("CourseId");
                            String Datetime = dataobj.getString("Datetime");
                            String UID = dataobj.getString("UID");
                            String Status = dataobj.getString("Status");

                            CourseModel model=new CourseModel();
                            model.setId(CategoryId);
                            model.setName(CategoryName);
                            model.setCourseid(CourseId);
                            coursearray1.add(model);

                        }

                        LinearLayoutManager layoutManager = new GridLayoutManager(CategoryActivity.this, 2, GridLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        CategoryAdapter customAdapter = new CategoryAdapter(CategoryActivity.this,coursearray1);
                        recyclerView.setAdapter(customAdapter);
                    }
                    else
                    {
//                        dialoq1progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }

                    Log.e("Result",data);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
//                dialoq1progress.setVisibility(View.INVISIBLE);
                //staticDataHelper.showLoadingDialog();
            }
        });
    }
}
