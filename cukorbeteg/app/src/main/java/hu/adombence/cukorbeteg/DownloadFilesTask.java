package hu.adombence.cukorbeteg;

import android.os.AsyncTask;

import java.net.URL;

public class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

    @Override
    protected Long doInBackground(URL... urls) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }

    
}
