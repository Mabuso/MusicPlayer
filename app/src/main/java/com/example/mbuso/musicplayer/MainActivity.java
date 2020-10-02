package com.example.mbuso.musicplayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "AndroidLogin_Connection";
    private String tag_json_object = "json_array_req";
    private String url = "http://127.0.0.1:3306/team23/models/android_model.php";
    private String url_file = "/index.php";
    private String vUserName;
    private String vPassword;
    //private playListAdapter adapter;
    private ListView playlist;
    private JSONObject json;
    private URL sendurl ;
    private HttpURLConnection connection;
    Intent intent ;

    private DataOutputStream dos;
    //private ArrayList<Tracks> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = (Button) findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(this);
       /* try {
            sendurl = new URL( url );
            connection = (HttpURLConnection) sendurl.openConnection();
            dos = new DataOutputStream( connection.getOutputStream() );
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnLogin){
            TextInputLayout UserNameLayout = (TextInputLayout) findViewById(R.id.UserNameLayout);
            UserNameLayout.setErrorEnabled(true);
            final EditText UserName= (EditText) findViewById(R.id.UserNameView);
            TextInputLayout PasswordLayout = (TextInputLayout) findViewById(R.id.PasswordLayout);
            PasswordLayout.setErrorEnabled(true);
            final EditText password = (EditText) findViewById(R.id.PasswordView);
            vUserName = UserName.getText().toString();
            vPassword = password.getText().toString();
            json = new JSONObject();
            Map<String,String> dataSennd = new HashMap<>();

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Action","login/run");
                    params.put("UserName", vUserName);
                    params.put("Password", vPassword);
                    return params;
                };
                @Override
                public void onResponse(String response) {
                    if(response == "SUCCESS"){
                        AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this) ;
                        d.setTitle("Success");
                        d.setMessage("Now Logged in");
                        d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO launch activity here
                            }
                        });
                        d.create().show();
                        UserName.setText(response);
                    } else {
                        password.setText("");
                        UserName.setText("UserName/Password is incorrect");
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    UserName.setText(error.toString());
                }
            });
            queue.add(stringRequest);
            startActivity(this.getIntent());
            try {
                json.put("UserName", vUserName);
                json.put("Password", vPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

           /* connection.setUseCaches( false );

            connection.setDoOutput( true );
            connection.setInstanceFollowRedirects( false );
            try{
                connection.setRequestMethod("POST");
                dos.writeBytes( json.toString() );

                dos.flush();
                dos.close();
                connection.disconnect();

            } catch(Exception e){
                e.printStackTrace();
            }*/
            intent = new Intent(this, playlistActivity.class);

            //startActivity(intent);
        }


    }


}
