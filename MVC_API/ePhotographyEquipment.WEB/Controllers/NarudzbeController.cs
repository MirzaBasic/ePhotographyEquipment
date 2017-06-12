using ePhotographyEquipment.DATA.Models;
using ePhotographyEquipment.WEB.Data;
using ePhotographyEquipment.WEB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;

namespace ePhotographyEquipment.WEB.Controllers
{
    public class NarudzbeController : Controller
    {
        MojContext mc = new MojContext();
        // GET: Narudzbe
        public ActionResult Korisnik(int Id)
        {

            var model = mc.Narudzbe.Where(x => x.KorisnikId == Id).Select(x => new NarudzbaVM
            {
                Id = x.Id,
                KorisnikId = x.KorisnikId,
                DatumNarudzbe = x.DatumNarudzbe.ToString(),
                BrojNarudzbe = x.BrojNarudzbe,
                UkupanIznos = x.UkupanIznos,




            }).ToList();
            return Json(model, JsonRequestBehavior.AllowGet);
        }
        public ActionResult Obrisi(int Id)
        {

            Narudzba n = mc.Narudzbe.Find(Id);
            if (n != null)
            {

                List<StavkeNarudzbe> s = mc.StavkeNaruzbe.Where(x => x.NaruzbaId == Id).ToList();

                mc.StavkeNaruzbe.RemoveRange(s);

                mc.Narudzbe.Remove(n);


                try
                {
                    mc.SaveChanges();
                }
                catch
                {
                    return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
                }

                return new HttpStatusCodeResult(HttpStatusCode.OK);
            }
            return new HttpStatusCodeResult(HttpStatusCode.BadRequest);


        }

        public ActionResult Dodaj(NarudzbaVM narudzba)
        {
            Narudzba n = new Narudzba();
          

            mc.Narudzbe.Add(n);
            n.KorisnikId = narudzba.KorisnikId;
            n.UkupanIznos = narudzba.UkupanIznos;
            n.DatumNarudzbe = DateTime.Now;
            n.BrojNarudzbe = DateTime.Now.Millisecond.ToString()+"-FDF"+DateTime.Now.ToOADate();


            try
            {
                mc.SaveChanges();
            }
            catch
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }


            for(int i=0;i<narudzba.Stavke.Count;i++)
            {
               
                mc.StavkeNaruzbe.Add(new StavkeNarudzbe {

                    NaruzbaId=n.Id,
                    ProizvodId=narudzba.Stavke[i].ProizvodId
                });

                try
                {
                    mc.SaveChanges();
                }
                catch
                {
                    return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
                }
            }
           


            return new HttpStatusCodeResult(HttpStatusCode.OK);


        }
    }
}
