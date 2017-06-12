package hci_130218.photography.Utils;


import android.net.Uri;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import hci_130218.photography.Helper.Sesija;
import hci_130218.photography.Models.KorisniciVM;

/**
 * Created by Developer on 29.04.2017..
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String BASIC_URL = "http://hci230.app.fit.ba";

    public static String CONTROLER_PROIZVODI = "Proizvodi";
    public static String CONTROLER_NARUZBA = "Narudzbe";
    public  static String CONTROLER_KORISNICI="Korisnici";
    public static String ACTION_PRETRAGA = "Pretraga";
    public static String ACTION_KORISNIK = "Korisnik";
    public static String ACTION_DODAJ = "Dodaj";
    public static String ACTION_AUTENTIFIKACIJA="Autentifikacija";
    public static String SEARCH_QUERY = "q";

    public static String TIP_PROIZVODA = "tipProizvoda";
    public static String AKCIJA_PROIZVODI = "akcija";


    public static URL buildSearchURL(String searchQuery,int tip,Boolean akcija) {
        Uri uri = Uri.parse(BASIC_URL).buildUpon().
                appendPath(CONTROLER_PROIZVODI).
                appendPath(ACTION_PRETRAGA).
                appendQueryParameter(SEARCH_QUERY, searchQuery).
appendQueryParameter(AKCIJA_PROIZVODI,akcija.toString()).
                appendQueryParameter(TIP_PROIZVODA,String.valueOf(tip)).
                build();


        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built url" + url.toString());
        return url;


    }

    public static URL buildNarudzbaAddURL() {
        Uri uri = Uri.parse(BASIC_URL).buildUpon().
                appendPath(CONTROLER_NARUZBA).
                appendPath(ACTION_DODAJ).

                build();


        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built url" + url.toString());
        return url;


    }

    public static URL buildNarudzbeByKorisnikURL() {
        Uri uri = Uri.parse(BASIC_URL).buildUpon().
                appendPath(CONTROLER_NARUZBA).
                appendPath(ACTION_KORISNIK).
                appendQueryParameter("id",String.valueOf( Sesija.GetUser().Id)).

                build();


        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built url" + url.toString());
        return url;


    }

    public static URL buildAutentifikacijaURL(String username, String pass) {

        Uri uri = Uri.parse(BASIC_URL).buildUpon().
                appendPath(CONTROLER_KORISNICI).appendPath(ACTION_AUTENTIFIKACIJA).

                appendQueryParameter("Username",username).
                appendQueryParameter("Password",pass).


                build();


        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built url" + url.toString());
        return url;


    }

    public static URL buildPostKorisnikURL(KorisniciVM korisnik) {

        Uri uri = Uri.parse(BASIC_URL).buildUpon().
                appendPath(CONTROLER_KORISNICI).appendPath(ACTION_DODAJ).
                appendQueryParameter("Id",String.valueOf(korisnik.Id)).
                appendQueryParameter("Email",korisnik.Email ).
                appendQueryParameter("Ime",korisnik.Ime).
                appendQueryParameter("Prezime",korisnik.Prezime).
                appendQueryParameter("Username",korisnik.Username).
                appendQueryParameter("Password",korisnik.Password).


                build();


        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built url" + url.toString());
        return url;


    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }




public static Void postResponseToHttpUrl(URL url, String jsonParameters) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
        urlConnection.setDoOutput(true);
        urlConnection.setChunkedStreamingMode(0);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");
        Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));


if(!jsonParameters.equals("")){

   writer.write(jsonParameters);


    writer.close();
}

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

    } finally {
        urlConnection.disconnect();
    }


    return null;
}




}
