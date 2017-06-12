using ePhotographyEquipment.DATA.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ePhotographyEquipment.DATA.Models
{
   public class Narudzba:IEntity
    {
        public int Id { get; set; }
        public bool IsDeleted { get; set; }
        public DateTime DatumNarudzbe { get; set; }
        public string BrojNarudzbe { get; set; }
        public float UkupanIznos { get; set; }
        public virtual Korisnik Korisnik { get; set; }
        public int KorisnikId { get; set; }
    }
}
