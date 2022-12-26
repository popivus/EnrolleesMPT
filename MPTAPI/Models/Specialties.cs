using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MPTAPI.Models
{
    public class Specialties
    {
        [Key]
        public int ID_Speciality { get; set; }
        public string Name { get; set; }
        public string Qualification { get; set; }
        public string Training_period { get; set; }
        public string Description { get; set; }
        public string Image_URL { get; set; }
    }
}
