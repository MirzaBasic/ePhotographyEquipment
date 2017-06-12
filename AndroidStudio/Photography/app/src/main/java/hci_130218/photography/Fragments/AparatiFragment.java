package hci_130218.photography.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hci_130218.photography.Activitys.DetaljiProizvodaActivity;
import hci_130218.photography.Helper.GsonConverter;
import hci_130218.photography.Models.ProizvodiVM;
import hci_130218.photography.R;
import hci_130218.photography.Utils.NetworkUtils;

public class AparatiFragment extends Fragment {
    private TextView mErrorMessage;
    private ProgressBar mProggresBar;
    private ListView mListView;
    private List<ProizvodiVM> mProizvodi;
    private String mSearchQuery="";

    private TextView mSearchNotFound;
    public AparatiFragment() {

    }

    public static AparatiFragment newInstance() {
        AparatiFragment fragment = new AparatiFragment();

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_proizvodi,container,false);




        mListView = (ListView) view.findViewById(R.id.list_view_proizvodi);
        mErrorMessage = (TextView) view.findViewById(R.id.textviev_error_message);
        mProggresBar = (ProgressBar) view.findViewById(R.id.proggres_bar);
        mSearchNotFound= (TextView) view.findViewById(R.id.textviev_search_not_found_message);


        doGetData();
        return view;








    }
    private void doGetData() {
        mProggresBar.setVisibility(View.VISIBLE);
        new AsyncTask<URL, Void, String>() {

            @Override

            protected String doInBackground(URL... params) {
                String result=null;
                try {
                    result =NetworkUtils.getResponseFromHttpUrl(params[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                mProggresBar.setVisibility(View.INVISIBLE);
                if (data != null) {
                    Type listType = new TypeToken<ArrayList<ProizvodiVM>>() {
                    }.getType();

                    mProizvodi = GsonConverter.JsonToListArray(data, listType);

                    doSetAdapter();
                    if (mProizvodi.size() == 0) {
                        doShowSearchNotFound();
                    } else {
                        doShowData();

                    }

                }
                else{
                    doShowErrorMessage();
                }

            }






        }.execute(NetworkUtils.buildSearchURL(mSearchQuery,1,false));


    }
    private void doSetAdapter() {

        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mProizvodi.size();
            }

            @Override
            public Object getItem(int position) {
                return mProizvodi.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final ProizvodiVM proizvod = mProizvodi.get(position);


                if(view==null){
                    final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.proizvodi_list_view,parent,false);
                }
                final TextView naziv = (TextView) view.findViewById(R.id.tvNaziv);

                final TextView cijena = (TextView) view.findViewById(R.id.tvCijena);

                ImageView imageProizvod = (ImageView) view.findViewById(R.id.imageProizvod);







                naziv.setText(proizvod.Naziv);

                cijena.setText(String.valueOf(proizvod.Cijena)+" KM");
if(proizvod.Slika!=null) {

    Bitmap decodedByte = BitmapFactory.decodeByteArray(proizvod.Slika, 0, proizvod.Slika.length);
    imageProizvod.setImageBitmap(decodedByte);
}

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        do_GetDetalji(proizvod);
                    }
                });

                return view;
            }
        });
    }

    private void do_GetDetalji(ProizvodiVM proizvod) {

        Intent intent =new Intent(getActivity(), DetaljiProizvodaActivity.class);
        Bundle arg=new Bundle();
        arg.putSerializable("proizvod_key",proizvod);
        intent.putExtras(arg);
        startActivity(intent);
    }


    public void doSetSearchQuery(String s) {
        mSearchQuery=s;
        doGetData();
    }




    public void doShowErrorMessage() {
        mSearchNotFound.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }
    public void doShowSearchNotFound() {

        mSearchNotFound.setVisibility(View.VISIBLE);
    }
    public void doShowData() {
        mSearchNotFound.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
    }
}
