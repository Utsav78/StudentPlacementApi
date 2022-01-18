package com.codewithutsav.studentplacementprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codewithutsav.studentplacementprediction.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String url = "https://utsav-student-placement-api.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.predict.setOnClickListener(view1 -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url, response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    String data = object.getString("placement");
                    if (data.equals("1")){
                        binding.result.setText("Placement Huncha");

                    }else{
                        binding.result.setText("Placement Hudaina");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }, error -> {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }){
                @Override
                protected Map<String, String> getParams()  {
                    Map<String,String> params =new HashMap<>();
                    params.put("cgpa",binding.cgpa.getText().toString());
                    params.put("iq",binding.iq.getText().toString());
                    params.put("profile_score",binding.profileScore.getText().toString());

                    return params;

                }
            };

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(stringRequest);





        });
    }
}