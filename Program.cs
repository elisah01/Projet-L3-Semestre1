using Microsoft.EntityFrameworkCore;
using BrasilBurger.Data;

var builder = WebApplication.CreateBuilder(args);

// 🔐 Connection string via variable d'environnement
var connectionString =
    Environment.GetEnvironmentVariable("DATABASE_URL")
    ?? builder.Configuration.GetConnectionString("DefaultConnection");

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(connectionString));

builder.Services.AddControllers();

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.UseHttpsRedirection();
app.UseAuthorization();

app.MapControllers();
// Route racine pour tester
app.MapGet("/", () => "Brasil Burger API est en ligne !");

// Ecouter sur le port Render
var port = Environment.GetEnvironmentVariable("PORT") ?? "5000";
app.Run($"http://0.0.0.0:{port}");
