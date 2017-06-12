namespace ePhotographyEquipment.WEB.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class test6 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Narudzbas", "BrojNarudzbe", c => c.String());
        }
        
        public override void Down()
        {
            DropColumn("dbo.Narudzbas", "BrojNarudzbe");
        }
    }
}
