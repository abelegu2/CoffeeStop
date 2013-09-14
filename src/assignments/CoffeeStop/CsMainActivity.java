package assignments.CoffeeStop;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class CsMainActivity extends Activity {

	final static String IMAGE_URL = "http://images2.fanpop.com/image/photos/13800000/Coffee-coffee-13874494-1920-1200.jpg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cs_main);
		
		try {
			new DownloadImageTask().execute(new URL(IMAGE_URL));
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cs_main, menu);
		return true;
	}
	
	public void goToMapButtonClick(View v)
	{
		Intent I =new Intent(this, CsMapActivity.class);
		this.startActivity(I);
	}
	
	public void goToSurveyButtonClick(View v)
	{
		Intent I =new Intent(this, CsSurveyActivity.class);
		this.startActivity(I);
	}
	
	private InputStream openHttpConnection(final URL url) throws IOException {
        InputStream in = null;
        int response = -1;
        final URLConnection conn = url.openConnection();
                 
        if (!(conn instanceof HttpURLConnection)) {                     
            throw new IOException("Not an HTTP connection");
        }
        
        try {
            final HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        } catch (Exception ex) {
        	ex.printStackTrace();            
        }
        return in;     
    }
	
	private class DownloadImageTask extends AsyncTask<URL, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(URL... urls) {
			assert urls.length == 1;
	        return downloadImage(urls[0]);
	    }
		@Override
		protected void onPostExecute(Bitmap bitmap) {
	        final ImageView img = (ImageView) findViewById(R.id.img);
	        img.setImageBitmap(bitmap);
		}
    }
	    
    private Bitmap downloadImage(final URL url) {
        Bitmap bitmap = null;
        InputStream in = null;        
        try {
            in = openHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;                
    }

}
