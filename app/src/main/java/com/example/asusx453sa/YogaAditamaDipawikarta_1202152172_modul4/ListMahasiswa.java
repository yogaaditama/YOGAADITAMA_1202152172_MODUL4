package com.example.asusx453sa.YogaAditamaDipawikarta_1202152172_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {
    private ListView LView;
    private ProgressBar mProgressBar;
    //list nama-nama mahasiswa
    private String [] nama= {
           "Rendy",
            "Ditha",
            "Yoga",
            "Arga",
            "Ridwan",
            "Irma",
            "Wahyu",
            "Rio",
            "Irfan",
            "Riandi",
            "Zayed",
            "elroy",
            "Ryan",
            "Raka",
            "Lara",
    };

    private MyTask mytask;
    private Button btnmulai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        LView = (ListView)findViewById(R.id.listMahasiswa);
        btnmulai = (Button)findViewById(R.id.btnMulai);
        mProgressBar = (ProgressBar)findViewById(R.id.progressbar);

        LView.setVisibility(View.GONE);
        LView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));

        btnmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mytask = new MyTask();
                mytask.execute();

            }
        });
    }

    public class MyTask extends AsyncTask<Void, String, Void>{
        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(ListMahasiswa.this);


        @Override
        protected void onPreExecute() {

            mAdapter = (ArrayAdapter<String>) LView.getAdapter();

            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);

            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface , int which) {
                    mytask.cancel(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialogInterface.dismiss();
                }
            });
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String item : nama){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    mytask.cancel(true);
                }
            }

            return null;
        }




        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter/(float)nama.length)*100);
            mProgressBar.setProgress(current_status);


            mProgressDialog.setProgress(current_status);


            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;


        }  @Override
        protected void onPostExecute( Void aVoid) {
            mProgressBar.setVisibility(View.GONE);

            //remove progress dialog
            mProgressDialog.dismiss();
            LView.setVisibility(View.VISIBLE);
        }    }
}

