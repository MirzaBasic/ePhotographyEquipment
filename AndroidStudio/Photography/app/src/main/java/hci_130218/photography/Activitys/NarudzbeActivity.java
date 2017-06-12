package hci_130218.photography.Activitys;

import android.content.Context;
import android.net.Network;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hci_130218.photography.Helper.GsonConverter;
import hci_130218.photography.Helper.MyApp;
import hci_130218.photography.Helper.Sesija;
import hci_130218.photography.Models.Global;
import hci_130218.photography.Models.NaruzbeVM;
import hci_130218.photography.Models.ProizvodiVM;
import hci_130218.photography.Models.StavkeNarudzbeVM;
import hci_130218.photography.R;
import hci_130218.photography.Utils.NetworkUtils;

public class NarudzbeActivity extends AppCompatActivity {
    private ListView mListView;

    List<NaruzbeVM> naruzbe;

    private TextView mPraznaKorpa;

    private TextView mErrorMessage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzbe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView)findViewById(R.id.list_view_proizvodi);
        mErrorMessage = (TextView) findViewById(R.id.textviev_error_message);
        mPraznaKorpa = (TextView) findViewById(R.id.textviev_search_not_found_message);

        doGetData();





    }





    private void doGetData() {


        new AsyncTask<URL, Void, String>() {
            @Override
            protected String doInBackground(URL... params) {

                String result="";
                try {
                    result=NetworkUtils.getResponseFromHttpUrl(params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                return null;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

if(s!=null) {
    Type listType = new TypeToken<ArrayList<NaruzbeVM>>() {
    }.getType();

    naruzbe = GsonConverter.JsonToListArray(s, listType);

    if (naruzbe.size() == 0) {
        mPraznaKorpa.setVisibility(View.VISIBLE);
    } else {
        doSetAdapter();

    }
}
else{
    mErrorMessage.setVisibility(View.VISIBLE);


}
            }
        }.execute(NetworkUtils.buildNarudzbeByKorisnikURL());


    }
    private void doSetAdapter() {

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return naruzbe.size();
            }

            @Override
            public Object getItem(int position) {
                return naruzbe.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                NaruzbeVM n = naruzbe.get(position);


                if(view==null){
                    final LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.narudzba_list_view,parent,false);
                }
                TextView brojNarudzbe = (TextView) view.findViewById(R.id.broj_narudzbeTxt);
                TextView datum = (TextView) view.findViewById(R.id.datum_narudzbe);
                TextView ukupno = (TextView) view.findViewById(R.id.ukupno_narudzbe_txt);






                brojNarudzbe.setText(n.BrojNarudzbe);
                datum.setText(n.DatumNarudzbe);
                ukupno.setText(n.UkupanIznos + " KM");

                return view;


            }
        });
    }


}
