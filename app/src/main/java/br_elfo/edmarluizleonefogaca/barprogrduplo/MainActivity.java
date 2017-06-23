package br_elfo.edmarluizleonefogaca.barprogrduplo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStartProgress;
    ProgressBar progressBar, myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.progress_drawable);

        btnStartProgress = (Button)findViewById(R.id.startprogress);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        myProgressBar = (ProgressBar)findViewById(R.id.myprogressbar);

        btnStartProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStartProgress.setEnabled(false);
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            myProgressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            progressBar.setSecondaryProgress(0);
            myProgressBar.setProgress(0);
            myProgressBar.setSecondaryProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0; i<10; i++){
                for(int j=0; j<10; j++){
                    publishProgress(i, j);
                    SystemClock.sleep(100);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            progressBar.setSecondaryProgress(values[1]);
            myProgressBar.setProgress(values[0]);
            myProgressBar.setSecondaryProgress(values[1]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            myProgressBar.setVisibility(View.GONE);
            btnStartProgress.setEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            TextView content = (TextView) getLayoutInflater().inflate(R.layout.about_view, null);
            content.setMovementMethod(LinkMovementMethod.getInstance());
            content.setText(Html.fromHtml(getString(R.string.about_body)));
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about)
                    .setView(content)
                    .setInverseBackgroundForced(true)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
        return super.onOptionsItemSelected(item);
    }

}
