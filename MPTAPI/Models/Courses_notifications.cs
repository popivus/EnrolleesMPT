using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MPTAPI.Models
{
    public class Courses_notifications
    {
        [Key]
        public int ID_Courses_notification { get; set; }
        public string FIO { get; set; }
        public string Email { get; set; }
        public int Courses_ID { get; set; }
        public string Contact_phone_number { get; set; }
        public string Contact_phone_number_parent { get; set; }
    }
}
