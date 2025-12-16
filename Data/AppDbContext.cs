using Microsoft.EntityFrameworkCore;

namespace BrasilBurger.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options)
            : base(options) { }

        // Exemple
        // public DbSet<User> Users { get; set; }
    }
}
