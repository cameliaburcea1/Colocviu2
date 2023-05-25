package ro.pub.cs.systems.eim.practical2test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

//    private String url;
    private ImageView imageView;

    public ImageLoadTask(ImageView imageView) {
//        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGetXKCD = new HttpGet(urls[0]);
        Bitmap cartoonBitmap = null;

        try {
            HttpGet httpGetCartoon = new HttpGet(urls[0]);
            HttpResponse httpResponse = httpClient.execute(httpGetCartoon);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                cartoonBitmap = BitmapFactory.decodeStream(httpEntity.getContent());
            }
        } catch (ClientProtocolException clientProtocolException) {
            Log.e(Constants.TAG, clientProtocolException.getMessage());
            if (Constants.DEBUG) {
                clientProtocolException.printStackTrace();
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }

        return cartoonBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}
