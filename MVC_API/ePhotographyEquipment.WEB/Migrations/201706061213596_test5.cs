namespace ePhotographyEquipment.WEB.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class test5 : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Proizvods", "Akcija", c => c.Boolean(nullable: false));
            AddColumn("dbo.Proizvods", "Popust", c => c.Int(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Proizvods", "Popust");
            DropColumn("dbo.Proizvods", "Akcija");
        }
    }
}
