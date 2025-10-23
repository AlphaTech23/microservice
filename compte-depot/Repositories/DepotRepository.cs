using System.Collections.Generic;
using Npgsql;
using CompteDepot.Models;
using Microsoft.Extensions.Configuration;

namespace CompteDepot.Repositories
{
    public class DepotRepository
    {
        private readonly string _connectionString;

        public DepotRepository(IConfiguration configuration)
        {
            _connectionString = configuration.GetConnectionString("DefaultConnection");
        }

        public IEnumerable<Depot> GetAll()
        {
            var depots = new List<Depot>();

            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();

                using (var cmd = new NpgsqlCommand("SELECT id, solde FROM depot", connection))
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        depots.Add(new Depot
                        {
                            Id = reader.GetInt64(0),
                            Solde = reader.GetDouble(1)
                        });
                    }
                }
            }

            return depots;
        }
    }
}