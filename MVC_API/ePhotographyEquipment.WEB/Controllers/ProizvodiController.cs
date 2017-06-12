
using ePhotographyEquipment.DATA.Models;
using ePhotographyEquipment.WEB.Data;
using ePhotographyEquipment.WEB.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace ePhotographyEquipment.WEB.Controllers
{
    public class ProizvodiController : Controller
    {
        MojContext mc = new MojContext();
        // GET: Proizvodi
        public ActionResult Pretraga(string q,int tipProizvoda,bool akcija)
        {

            var model = mc.Proizvodi.Where(x => x.Naziv.Contains(q) & x.Akcija == akcija).ToList();
            if (tipProizvoda != 0)
            {
                model=model.Where(x => x.TipProizvodaId == tipProizvoda).ToList();
            }


            return Json(model, JsonRequestBehavior.AllowGet);
        }



        public ActionResult InsertImage(int id, string path) {

            byte[] buff = null;
            FileStream fs = new FileStream(path,
                                           FileMode.Open,
                                           FileAccess.Read);
            BinaryReader br = new BinaryReader(fs);
            long numBytes = new FileInfo(path).Length;
            buff = br.ReadBytes((int)numBytes);


            Proizvod p = mc.Proizvodi.Find(id);
            if (p != null) {


                p.Slika = buff;
                mc.SaveChanges();
            }

            return null;
        }
    }
}