using ePhotographyEquipment.DATA.Models;

using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;


namespace ePhotographyEquipment.WEB.Data
{
    public class MojContext : DbContext
    {
        public MojContext() : base("Name=MyConnectionString")
        {


        }
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Conventions.Remove<OneToManyCascadeDeleteConvention>();

        }



        public DbSet<Korisnik> Korisnici { get; set; }
        public DbSet<Proizvod> Proizvodi { get; set; }
        public DbSet<TipProizvoda> TipProizvoda { get; set; }
        public DbSet<Narudzba> Narudzbe { get; set; }
        public DbSet<StavkeNarudzbe> StavkeNaruzbe { get; set; }



    }
}
