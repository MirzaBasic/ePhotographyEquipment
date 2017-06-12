package hci_130218.photography.Activitys;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

import hci_130218.photography.Helper.MyApp;
import hci_130218.photography.Helper.Sesija;
import hci_130218.photography.Models.Global;
import hci_130218.photography.Models.NaruzbeVM;
import hci_130218.photography.Models.ProizvodiVM;
import hci_130218.photography.Models.StavkeNarudzbeVM;
import hci_130218.photography.R;


import static hci_130218.photography.Helper.MyApp.getContext;

public class DetaljiProizvodaActivity extends AppCompatActivity {
    ProizvodiVM proizvod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji_proizvoda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           Bundle arg=getIntent().getExtras();
        if( arg.containsKey("proizvod_key")){

            proizvod= (ProizvodiVM) arg.getSerializable("proizvod_key");
            getSupportActionBar().setTitle(proizvod.Naziv);

        }else{

            finish();
        }



        TextView naziv = (TextView) findViewById(R.id.tvNaziv);

        TextView cijena = (TextView) findViewById(R.id.tvCijena);
        TextView opis = (TextView) findViewById(R.id.tvOpis);
        TextView sifra = (TextView) findViewById(R.id.tvSifra);

        ImageView image = (ImageView) findViewById(R.id.imageDetalji);

        naziv.setText(proizvod.Naziv);
        sifra.setText("Sifra: "+proizvod.Sifra);
        cijena.setText(String.valueOf("Cijena: "+proizvod.Cijena)+" KM");
        opis.setText("Opis: " + String.valueOf(proizvod.Opis));

        if (proizvod.Slika != null) {

            Bitmap decodedByte = BitmapFactory.decodeByteArray(proizvod.Slika, 0, proizvod.Slika.length);
            image.setImageBitmap(decodedByte);

        }

        Button btnKorpa = (Button) findViewById(R.id.btnKupi);
        btnKorpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sesija.GetUser() == null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(DetaljiProizvodaActivity.this).create();
                    alertDialog.setTitle("KUPI PROIZVOD");
                    alertDialog.setMessage("Da bi izvršili kupovinu morate biti prijavljeni!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Uredu",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    do_UKorpu();
                                }
                            });
                    alertDialog.show();
                } else {
                    do_UKorpu();
                }
            }
        });
    }



    private void do_UKorpu() {
        final AlertDialog alertDialog = new AlertDialog.Builder(DetaljiProizvodaActivity.this).create();
        final SeekBar seek = new SeekBar(this);
        seek.setProgress(1);
        seek.setMax(10);
        alertDialog.setMessage("Količina: 1/10");
        seek.setKeyProgressIncrement(1);
      seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              if(progress==0){

                  seek.setProgress(1);
              }
              alertDialog.setMessage("Količina: "+progress+"/10");

          }

          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {

          }

          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {

          }


      });


        alertDialog.setView(seek);
        alertDialog.setTitle("Odaberite željenu količinu");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "POTVRDI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        boolean proizvodVecUKorpi=false;
                      if(Global.Korpa==null){
                          Global.Korpa=new NaruzbeVM();
                          Global.Korpa.Stavke=new ArrayList<StavkeNarudzbeVM>();
                      }

                      for (int i=0;i<Global.Korpa.Stavke.size();i++){

                          if(Global.Korpa.Stavke.get(i).ProizvodId==proizvod.Id){

                              Global.Korpa.Stavke.get(i).Kolicina+=seek.getProgress();
                              Global.Korpa.UkupanIznos+=proizvod.Cijena*seek.getProgress();
                              proizvodVecUKorpi=true;
                              Toast.makeText(MyApp.getContext(), "Uspješno izmijenjena količina", Toast.LENGTH_SHORT).show();
                              break;


                          }
                      }
                      if(!proizvodVecUKorpi){
                      StavkeNarudzbeVM stavka=new StavkeNarudzbeVM();

                        stavka.Kolicina=seek.getProgress();
                        stavka.ProizvodId=proizvod.Id;
                        stavka.Proizvod=new ProizvodiVM();
                          stavka.Proizvod.Naziv=proizvod.Naziv;
                          stavka.Proizvod.Cijena=proizvod.Cijena;
                       Global.Korpa.Stavke.add(stavka);
                          Global.Korpa.UkupanIznos+=proizvod.Cijena* seek.getProgress();
                          Toast.makeText(getContext(), "Uspješno dodan proizvod u korpu", Toast.LENGTH_SHORT).show();
                      }

                    }


                });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ODUSTANI",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}








