using ePhotographyEquipment.DATA.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ePhotographyEquipment.DATA.Models
{
    public class Proizvod:IEntity
    {
        public int Id { get; set; }
        public bool IsDeleted { get; set; }
        public String Naziv { get; set; }
        public String Sifra { get; set; }
        public String Opis { get; set; }
        public byte[] Slika { get; set; }
        public float Cijena { get; set; }
        public bool Akcija { get; set; }
        public int Popust { get; set; }
        public virtual TipProizvoda TipProizvoda { get; set; }
        public int TipProizvodaId { get; set; }
    }
}
