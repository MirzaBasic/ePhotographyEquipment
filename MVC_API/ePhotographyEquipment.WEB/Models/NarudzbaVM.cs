using ePhotographyEquipment.DATA.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ePhotographyEquipment.WEB.Models
{
    public class NarudzbaVM
    {

        public int Id { get; set; }
        public bool IsDeleted { get; set; }
        public String DatumNarudzbe { get; set; }
        public float UkupanIznos { get; set; }
        public string BrojNarudzbe { get; set; }
        public List<StavkeNarudzbe> Stavke { get; set; }
        public int KorisnikId { get; set; }
    }
}