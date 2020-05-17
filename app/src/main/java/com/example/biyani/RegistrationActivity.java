package com.example.biyani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.biyani.Model.RegistrationModel;
import com.example.biyani.network.ApiClient;
import com.example.biyani.network.ApiService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationActivity extends AppCompatActivity {
    ApiService mService;
    Spinner statespsn,cityspn,coursespn,categoryspn;
    Button signup;
    ArrayList<String> statearray,cityarray,coursearray1,categoryarray;
    static HashMap<String,String> statemap=new HashMap<String,String>();
    static HashMap<String,String> citymap=new HashMap<String,String>();
    static HashMap<String,String> coursemap=new HashMap<String,String>();
    TextInputEditText name,password,mob,email,dob,father;
    String statekey,citykey,coursekey;
    DatePickerDialog picker;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        statespsn = findViewById(R.id.statespsn);
        cityspn = findViewById(R.id.cityspn);
        coursespn = findViewById(R.id.coursespn);
        categoryspn = findViewById(R.id.categoryspn);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        mob = findViewById(R.id.mob);
        email = findViewById(R.id.email);
        progress = findViewById(R.id.progress);
        signup = findViewById(R.id.signup);
        dob = findViewById(R.id.dob);
        father = findViewById(R.id.father);
        getstate();
        getCourse();

        categoryarray=new ArrayList<>();
        categoryarray.add("Choose Category");
        final ArrayAdapter<String> aa = new ArrayAdapter<>(RegistrationActivity.this,R.layout.simple_spinner_item, categoryarray);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryspn.setAdapter(aa);

        cityarray=new ArrayList<>();
        final ArrayAdapter<String> aa1 = new ArrayAdapter<>(RegistrationActivity.this, R.layout.simple_spinner_item, cityarray);
        aa1.insert("Choose City",0);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityspn.setAdapter(aa1);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrationModel model=new RegistrationModel();
                String a= name.getText().toString();
                String b= father.getText().toString();
                String c= email.getText().toString();
                String d= mob.getText().toString();
                String e= password.getText().toString();
                String f= dob.getText().toString();
                String g= statekey;
                String h= citykey;
                String i= coursekey;
                model.setStudentName(a);
                model.setFatherName(b);
                model.setEmail(c);
                model.setMobile(d);
                model.setPassword(e);
                model.setDateOfBirth(f);
                model.setStudentName(g);
                model.setStudentName(h);
                model.setStudentName(i);
                signup(model);

            }
        });
//        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus) {
//                    // Show your calender here
//                    final Calendar cldr = Calendar.getInstance();
//                    int day = cldr.get(Calendar.DAY_OF_MONTH);
//                    int month = cldr.get(Calendar.MONTH);
//                    int year = cldr.get(Calendar.YEAR);
//                    // date picker dialog
//                    picker = new DatePickerDialog(RegistrationActivity.this,
//                            new DatePickerDialog.OnDateSetListener() {
//                                @Override
//                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                    dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                                }
//                            }, year, month, day);
//                    picker.show();
//                } else {
//                    // Hide your calender here
//                }
//            }
//        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        statespsn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yearspnstr1 = statespsn.getItemAtPosition(position).toString();
                for(Map.Entry<String, String> entry: statemap.entrySet()){
                    if(yearspnstr1.equals(entry.getKey())){
                        statekey = entry.getValue().toString();
                        try {
                            getCity(statekey);
                        }
                        catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        break; //breaking because its one to one map
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cityspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yearspnstr1 = cityspn.getItemAtPosition(position).toString();
                for(Map.Entry<String, String> entry: citymap.entrySet()){
                    if(yearspnstr1.equals(entry.getKey())){
                        citykey = entry.getValue().toString();
                     //breaking because its one to one map
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        coursespn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yearspnstr1 = coursespn.getItemAtPosition(position).toString();
                for(Map.Entry<String, String> entry: coursemap.entrySet()){
                    if(yearspnstr1.equals(entry.getKey())){
                        coursekey = entry.getValue().toString();
                        //breaking because its one to one map
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void signup(RegistrationModel m)
    {

//        dialoq1progress.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        mService = ApiClient.getClient().create(ApiService.class);
        //Call<RegisterResponse> userCall=mService.register(new RegisterRequest(user_email,user_password,"false"));
        Call<ResponseBody> userCall = mService.addStudent(m);
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
//                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
//                    progressBar_downloads.setVisibility(View.INVISIBLE);
                    if (stauts.equalsIgnoreCase("200")) {
//                        JSONArray coursearray = responsejobj.getJSONArray("Data");
                            Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                            startActivity(i);
                        StaticDataHelper.setBooleanInPreferences(RegistrationActivity.this, "isuserlogin", true);
                        RegistrationActivity.this.finish();
                        Toast.makeText(getApplicationContext(),"Signup successful",Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    }
                    else
                    {
//                        dialoq1progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);

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
    private void getstate()
    {

//        dialoq1progress.setVisibility(View.VISIBLE);
        mService = ApiClient.getClient().create(ApiService.class);
        //Call<RegisterResponse> userCall=mService.register(new RegisterRequest(user_email,user_password,"false"));
        Call<ResponseBody> userCall = mService.getState();
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
//                    progressBar_downloads.setVisibility(View.INVISIBLE);
                    if (stauts.equalsIgnoreCase("200")) {
//                        statearr = new ArrayList<>();
                        statearray = new ArrayList<>();
                        JSONArray coursearray = responsejobj.getJSONArray("Data");
                        for (int i = 0; i < coursearray.length(); i++) {
                            JSONObject dataobj = coursearray.getJSONObject(i);
                            String ID = dataobj.getString("ID");
                            String StateName = dataobj.getString("StateName");
                            String UID = dataobj.getString("UID");
                            String Datetime = dataobj.getString("Datetime");
                            String Status = dataobj.getString("Status");

                            statearray.add(StateName);
                            statemap.put(StateName,ID);

                        }

                        final ArrayAdapter<String> aa = new ArrayAdapter<>(RegistrationActivity.this, R.layout.simple_spinner_item, statearray);
                        aa.insert("Choose State",0);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        statespsn.setAdapter(aa);
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
    private void getCity(String id)
    {

//        dialoq1progress.setVisibility(View.VISIBLE);
        mService = ApiClient.getClient().create(ApiService.class);
        //Call<RegisterResponse> userCall=mService.register(new RegisterRequest(user_email,user_password,"false"));
        Call<ResponseBody> userCall = mService.getCity(id);
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
//                    progressBar_downloads.setVisibility(View.INVISIBLE);
                    if (stauts.equalsIgnoreCase("200")) {
//                        statearr = new ArrayList<>();
                        cityarray = new ArrayList<>();
                        JSONArray coursearray = responsejobj.getJSONArray("Data");
                        for (int i = 0; i < coursearray.length(); i++) {
                            JSONObject dataobj = coursearray.getJSONObject(i);
                            String ID = dataobj.getString("ID");
                            String CityName = dataobj.getString("CityName");
                            String UID = dataobj.getString("UID");
                            String Datetime = dataobj.getString("Datetime");
                            String Status = dataobj.getString("Status");
                            String StateID = dataobj.getString("StateID");

                            cityarray.add(CityName);

//                            StateModel model=new StateModel();
//                            model.setId(ID);
//                            model.setName(StateName);
//                            statearr.add(model);

                            citymap.put(CityName,ID);

                        }

                        final ArrayAdapter<String> aa = new ArrayAdapter<>(RegistrationActivity.this, R.layout.simple_spinner_item, cityarray);
                        aa.insert("Choose City",0);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cityspn.setAdapter(aa);
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
    private void getCourse()
    {

//        dialoq1progress.setVisibility(View.VISIBLE);
        mService = ApiClient.getClient().create(ApiService.class);
        //Call<RegisterResponse> userCall=mService.register(new RegisterRequest(user_email,user_password,"false"));
        Call<ResponseBody> userCall = mService.getCourse();
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
//                    progressBar_downloads.setVisibility(View.INVISIBLE);
                    if (stauts.equalsIgnoreCase("200")) {
//                        statearr = new ArrayList<>();
                        coursearray1 = new ArrayList<>();
                        JSONArray coursearray = responsejobj.getJSONArray("Data");
                        for (int i = 0; i < coursearray.length(); i++) {
                            JSONObject dataobj = coursearray.getJSONObject(i);
                            String CourseId = dataobj.getString("CourseId");
                            String CourseName = dataobj.getString("CourseName");
                            String DepartmentId = dataobj.getString("DepartmentId");
                            String UID = dataobj.getString("UID");
                            String Datetime = dataobj.getString("Datetime");
                            String Status = dataobj.getString("Status");

                            coursearray1.add(CourseName);

//                            StateModel model=new StateModel();
//                            model.setId(ID);
//                            model.setName(StateName);
//                            statearr.add(model);

                            coursemap.put(CourseName,CourseId);

                        }

                        final ArrayAdapter<String> aa = new ArrayAdapter<>(RegistrationActivity.this, R.layout.simple_spinner_item, coursearray1);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        aa.insert("Choose Current Class",0);
                        coursespn.setAdapter(aa);
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
