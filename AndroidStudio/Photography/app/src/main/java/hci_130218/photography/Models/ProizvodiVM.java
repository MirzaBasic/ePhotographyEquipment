package hci_130218.photography.Models;

import java.io.Serializable;

/**
 * Created by Developer on 04.06.2017..
 */

public class ProizvodiVM implements Serializable {

    public int Id ;
    public boolean IsDeleted ;
    public String Naziv ;
    public String Opis ;
    public byte[] Slika ;
    public float Cijena ;
    public float Ocjena ;
    public int Popust ;
    public String Sifra;
}
