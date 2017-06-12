using ePhotographyEquipment.DATA.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ePhotographyEquipment.DATA.Models
{
    public class Korisnik:IEntity
    {
        public int Id { get; set; }
        public String Username { get; set; }
        public String Password { get; set; }
        public String Ime { get; set; }
        public bool IsDeleted { get; set; }
        public String Prezime { get; set; }
        public String  Email { get; set; }
        
    }
}
