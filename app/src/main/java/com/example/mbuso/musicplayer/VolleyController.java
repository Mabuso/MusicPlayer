package com.example.mbuso.musicplayer;

import android.app.Application;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.text.TextUtils;

/**
 * Created by Mbuso on 2016/10/15.
 */
public class VolleyController extends Application {
    public static final String TAG_APP = VolleyController.class.getSimpleName();
    private RequestQueue requestQueue;
    private ImageLoader imageloader;
    private static VolleyController instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized VolleyController getInstance(){
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;

    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageloader == null) {
            imageloader = new ImageLoader(this.requestQueue,
                    new LruBitmapCache());
        }
        return this.imageloader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG_APP : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG_APP);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
