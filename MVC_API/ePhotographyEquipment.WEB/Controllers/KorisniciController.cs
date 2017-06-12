using ePhotographyEquipment.DATA.Models;
using ePhotographyEquipment.WEB.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;

namespace ePhotographyEquipment.WEB.Controllers
{
    public class KorisniciController : Controller
    {
        MojContext mc = new MojContext();
        // GET: Korisnici
        public ActionResult Autentifikacija(string username, string password)
        {
            var model = mc.Korisnici.Where(x => x.Username == username && x.Password == password).FirstOrDefault();

            return Json(model, JsonRequestBehavior.AllowGet);
        }


        public ActionResult Dodaj(Korisnik korisnik)
        {

            Korisnik k = mc.Korisnici.Find(korisnik.Id);
            if (k == null)
            {
                k = new Korisnik();
                mc.Korisnici.Add(k);
            }

            k.Ime = korisnik.Ime;
            k.Prezime = korisnik.Prezime;
            k.Password = korisnik.Password;
            k.Username = korisnik.Username;

            k.Email = korisnik.Email;


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
    }
}