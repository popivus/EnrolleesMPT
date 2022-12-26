using Microsoft.EntityFrameworkCore;
using MPTAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MPTAPI.Data
{
    public class EnrolleesDBContext : DbContext
    {
        public DbSet<Courses_notifications> Courses_notifications { get; set; }
        public DbSet<Doc_notifications> Doc_notifications { get; set; }
        public DbSet<Specialties> Specialties { get; set; }
        public EnrolleesDBContext(DbContextOptions<EnrolleesDBContext> options) : base (options) { }
    }
}
