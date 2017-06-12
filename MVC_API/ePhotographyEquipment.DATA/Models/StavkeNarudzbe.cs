using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ePhotographyEquipment.DATA.Models
{
    public class StavkeNarudzbe
    {
        public int Id { get; set; }
        public bool IsDeleted { get; set; }
        public virtual Proizvod Proizvod { get; set; }
        public int ProizvodId { get; set; }
        public virtual Narudzba Naruzba { get; set; }
        public int NaruzbaId { get; set; }
    }
}
