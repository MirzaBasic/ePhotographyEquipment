namespace ePhotographyEquipment.WEB.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class test31 : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Narudzbas",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        IsDeleted = c.Boolean(nullable: false),
                        DatumNarudzbe = c.DateTime(nullable: false),
                        UkupanIznos = c.Single(nullable: false),
                        KorisnikId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Korisniks", t => t.KorisnikId)
                .Index(t => t.KorisnikId);
            
            CreateTable(
                "dbo.StavkeNarudzbes",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        IsDeleted = c.Boolean(nullable: false),
                        ProizvodId = c.Int(nullable: false),
                        NaruzbaId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Narudzbas", t => t.NaruzbaId)
                .ForeignKey("dbo.Proizvods", t => t.ProizvodId)
                .Index(t => t.ProizvodId)
                .Index(t => t.NaruzbaId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.StavkeNarudzbes", "ProizvodId", "dbo.Proizvods");
            DropForeignKey("dbo.StavkeNarudzbes", "NaruzbaId", "dbo.Narudzbas");
            DropForeignKey("dbo.Narudzbas", "KorisnikId", "dbo.Korisniks");
            DropIndex("dbo.StavkeNarudzbes", new[] { "NaruzbaId" });
            DropIndex("dbo.StavkeNarudzbes", new[] { "ProizvodId" });
            DropIndex("dbo.Narudzbas", new[] { "KorisnikId" });
            DropTable("dbo.StavkeNarudzbes");
            DropTable("dbo.Narudzbas");
        }
    }
}
