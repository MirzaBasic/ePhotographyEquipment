using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ePhotographyEquipment.WEB.Models
{
    public class ProizvodVM
    {
        public int Id { get; set; }
      
        public String Naziv { get; set; }
        public String Sifra { get; set; }
        public String Opis { get; set; }
        public String Slika { get; set; }
        public float Cijena { get; set; }
        public bool Akcija { get; set; }
        public int Popust { get; set; }

        public int TipProizvodaId { get; set; }
    }
}