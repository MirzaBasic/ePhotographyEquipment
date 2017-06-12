namespace ePhotographyEquipment.WEB.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class test7 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Proizvods", "Sifra", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.Proizvods", "Sifra");
        }
    }
}
