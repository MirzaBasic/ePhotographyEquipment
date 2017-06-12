package hci_130218.photography;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hci_130218.photography.Helper.Sesija;

/**
 * Created by Developer on 04.06.2017..
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       if(Sesija.GetUser()!=null) {
            Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
            startActivity(intent);

       }
       else{
         Intent Intent=new Intent(MainActivity.this,LoginActivity.class);
          startActivity(Intent);

      }
        finish();

    }
}
