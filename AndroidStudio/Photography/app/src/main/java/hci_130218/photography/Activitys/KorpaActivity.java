package hci_130218.photography.Activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hci_130218.photography.Activitys.DetaljiProizvodaActivity;
import hci_130218.photography.Helper.GsonConverter;
import hci_130218.photography.Helper.MyApp;
import hci_130218.photography.Helper.Sesija;
import hci_130218.photography.Models.Global;
import hci_130218.photography.Models.ProizvodiVM;
import hci_130218.photography.Models.StavkeNarudzbeVM;
import hci_130218.photography.NavigationDrawerActivity;
import hci_130218.photography.R;
import hci_130218.photography.Utils.NetworkUtils;

public class KorpaActivity extends AppCompatActivity {

    private ListView mListView;



    private TextView mPraznaKorpa;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korpa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView)findViewById(R.id.list_view_proizvodi);

        mPraznaKorpa = (TextView) findViewById(R.id.tv_empty_basket_message);

        doGetData();
        FloatingActionButton zakljuci= (FloatingActionButton) findViewById(R.id.btn_zakljuci);
        zakljuci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Global.Korpa!=null&&Sesija.GetUser()!=null){

                    Global.Korpa.KorisnikId= Sesija.GetUser().Id;
                    new AsyncTask<URL, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(URL... params) {
                            boolean response=true;
                            try {
                               NetworkUtils.postResponseToHttpUrl(params[0],GsonConverter.ObjectToJson(Global.Korpa));
                            } catch (IOException e) {
                                e.printStackTrace();
                                response=false;

                            }
                            return response;
                        }

                        @Override
                        protected void onPostExecute(Boolean success) {
                            super.onPostExecute(success);
                            if(success){
                            Global.Korpa=null;
                            mListView.setAdapter(null);
                            doGetData();
                            Toast.makeText(MyApp.getContext(), "Uspješno izvršena narudžba", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MyApp.getContext(), "Došlo je do greške!", Toast.LENGTH_SHORT).show();


                            }

                        }
                    }.execute(NetworkUtils.buildNarudzbaAddURL());

                }



            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.korpa,menu);
        return super.onCreateOptionsMenu(menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_isprazni) { // This is the home/back button
            Global.Korpa=null;
            mListView.setAdapter(null);
            doGetData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void doGetData() {
        if (Global.Korpa == null){

        mPraznaKorpa.setVisibility(View.VISIBLE);
       mListView.setVisibility(View.INVISIBLE);
    }
        else{


        doSetAdapter();
    }




    }
    private void doSetAdapter() {

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return Global.Korpa.Stavke.size();
            }

            @Override
            public Object getItem(int position) {
                return Global.Korpa.Stavke.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final StavkeNarudzbeVM stavka = Global.Korpa.Stavke.get(position);


                if(view==null){
                    final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.korpa_list_view,parent,false);
                }
                final TextView naziv = (TextView) view.findViewById(R.id.tvNaziv);
                final TextView kolicina = (TextView) view.findViewById(R.id.tvKolicina);
                final TextView cijena = (TextView) view.findViewById(R.id.tvCijena);







                kolicina.setText("Kom: "+stavka.Kolicina);
                naziv.setText(stavka.Proizvod.Naziv);
                if(stavka.Proizvod.Popust!=0){
                cijena.setText(String.valueOf(stavka.Proizvod.Cijena*stavka.Proizvod.Popust/100)+" KM");
                }
                else{
                    cijena.setText(String.valueOf(stavka.Proizvod.Cijena)+" KM");
                }






                return view;
            }
        });
    }


}



