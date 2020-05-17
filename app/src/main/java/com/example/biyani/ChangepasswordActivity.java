package com.example.biyani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biyani.network.ApiClient;
import com.example.biyani.network.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangepasswordActivity extends AppCompatActivity {
 ApiService mService;
    EditText editOldPassword, editNewPassword;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        editOldPassword= findViewById(R.id.editOldPassword);
        editNewPassword = findViewById(R.id.editNewPassword);
        submitButton = findViewById(R.id.submitButton);

        getSupportActionBar().hide();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentid = StaticDataHelper.getStringFromPreferences(ChangepasswordActivity.this,"studentid");
                String old=editOldPassword.getText().toString();
                String new1=editNewPassword.getText().toString();
                changePassword(studentid,old,new1);
            }
        });


    }
    private void changePassword(String id, String old, String newPassword) {

        mService = ApiClient.getClient().create(ApiService.class);
        //Call<RegisterResponse> userCall=mService.register(new RegisterRequest(user_email,user_password,"false"));
        Call<ResponseBody> userCall = mService.changePassword(id, old, newPassword);
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
                    Toast.makeText(ChangepasswordActivity.this, message, Toast.LENGTH_SHORT).show();
//                    progressBar_downloads.setVisibility(View.INVISIBLE);
                    if (stauts.equalsIgnoreCase("200")) {


                       startActivity(new Intent(ChangepasswordActivity.this,MainActivity.class));
                        ChangepasswordActivity.this.finish();
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
