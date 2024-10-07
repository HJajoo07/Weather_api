package com.example.weather_api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.os.Build;
import android.os.Bundle;
import android.view.PixelCopy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.internal.StaticLayoutBuilderConfigurer;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView weather,tem,wind_speed,humidity,atmp,state_country;
    Button get;
    public static final String api =BuildConfig.apikey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusbarcolor();
        state_country=findViewById(R.id.state_country);
        city = findViewById(R.id.editTextText);
        weather = findViewById(R.id.textView2);
        tem = findViewById(R.id.textView5);
        wind_speed = findViewById(R.id.textView7);
        humidity=findViewById(R.id.textView9);
        atmp=findViewById(R.id.textView11);
        get = findViewById(R.id.button);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    result();
                    System.out.println("Working button");
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(),"Mistake in get value",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        });
    }
    public void result() throws IOException
    {
        String getcity=city.getText().toString();
        OkHttpClient client=new OkHttpClient();
        String url=api+getcity;
        Request request=new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call,IOException e) {

                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                if(response.isSuccessful())
                {
                    String responseData=response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONObject current = json.getJSONObject("current");
                        JSONObject location = json.getJSONObject("location");
                        JSONObject condition = current.getJSONObject("condition");


                        double temprature = current.getDouble("temp_c");
                        String weath_r=condition.getString("text");
                        double wind_s = current.getDouble("wind_kph");
                        double humi_ity = current.getDouble("humidity");
                        double at_p=current.getDouble("pressure_mb");
                        String c_ty=location.getString("name");
                        String s_tate=location.getString("region");
                        String c_untry=location.getString("country");


                        runOnUiThread(() ->state_country.setText(c_ty+" : "+s_tate+" : "+c_untry));
                        runOnUiThread(() ->tem.setText(temprature + "°C"));
                        runOnUiThread(()-> wind_speed.setText(wind_s + " kph"));
                        runOnUiThread(()-> weather.setText(weath_r));
                        runOnUiThread(()-> humidity.setText(humi_ity + " %"));
                        runOnUiThread(()-> atmp.setText(at_p +" mb"));
                    } catch (JSONException e)
                    {
                        runOnUiThread(() ->
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show());
                        e.printStackTrace();

                        System.out.println("Error parsing"+e.getMessage());
                    }


                }
                else {
                    runOnUiThread(() -> state_country.setText("Enter Valid City"));
                    runOnUiThread(() -> tem.setText("N/A"));
                    runOnUiThread(() -> wind_speed.setText("N/A"));
                    runOnUiThread(() -> weather.setText("N/A"));
                    runOnUiThread(() -> humidity.setText("N/A"));
                    runOnUiThread(() -> atmp.setText("N/A"));
                }
            }
        });



    }

    private void statusbarcolor()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        } else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

    }
}