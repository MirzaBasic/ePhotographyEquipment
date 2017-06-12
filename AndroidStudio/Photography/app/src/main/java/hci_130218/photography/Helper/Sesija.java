package hci_130218.photography.Helper;

import android.app.Activity;
import android.content.SharedPreferences;

import hci_130218.photography.Models.KorisniciVM;

/**
 * Created by Developer on 23.05.2017..
 */

public class Sesija {
    public static String USER_DETAILS_SHARED_PREFERENCE_KEY="user_details_key";

    private static final String PREFS_NAME = "SharedPreferencesFile";



    public static void SaveUser(KorisniciVM googleSignInAccount){
        String jsonGoogleUser= GsonConverter.ObjectToJson(googleSignInAccount);
        SharedPreferences preferences= MyApp.getContext().getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(USER_DETAILS_SHARED_PREFERENCE_KEY,jsonGoogleUser);
        editor.commit();

    }

    public static KorisniciVM GetUser(){

        SharedPreferences preferences= MyApp.getContext().getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
   String jsonGoogleUser=preferences.getString(USER_DETAILS_SHARED_PREFERENCE_KEY,"");
        if(jsonGoogleUser.length()==0){
            return null;

        }

      return GsonConverter.JsonToObject(jsonGoogleUser,KorisniciVM.class);


    }
}
