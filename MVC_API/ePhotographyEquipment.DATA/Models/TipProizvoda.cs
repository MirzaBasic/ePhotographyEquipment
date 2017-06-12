using ePhotographyEquipment.DATA.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ePhotographyEquipment.DATA.Models
{
   
    public class TipProizvoda:IEntity
    {
        public int Id { get; set; }
        public String Naziv { get; set; }

        public bool IsDeleted { get; set; }

    }
}
