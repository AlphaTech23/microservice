using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Global.Repositories;
using Global.Services;

var builder = WebApplication.CreateBuilder(args);

// === Ajouter les services ===
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// === Injection des services et dépôts ===
builder.Services.AddSingleton<DirectionRepository>();
builder.Services.AddSingleton<ActionRoleRepository>();
builder.Services.AddSingleton<UtilisateurRepository>();
builder.Services.AddSingleton<DirectionService>();
builder.Services.AddSingleton<UtilisateurService>();
builder.Services.AddSingleton<ActionRoleService>();

var app = builder.Build();

// === Swagger UI ===
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// === Pipeline ===
app.UseHttpsRedirection();
app.UseAuthorization();
app.MapControllers();
app.Run();
