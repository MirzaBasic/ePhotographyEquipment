﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ePhotographyEquipment.DATA.Helper
{
  public  interface IEntity
    {
        int Id { get; set; }
        bool IsDeleted { get; set; }
    }
}
