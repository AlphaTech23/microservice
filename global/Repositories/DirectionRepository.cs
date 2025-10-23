using System.Collections.Generic;
using Npgsql;
using Global.Models;
using Microsoft.Extensions.Configuration;

namespace Global.Repositories
{
    public class DirectionRepository
    {
        private readonly string _connectionString;

        public DirectionRepository(IConfiguration configuration)
        {
            _connectionString = configuration.GetConnectionString("DefaultConnection");
        }

        public IEnumerable<Direction> GetAll()
        {
            var directions = new List<Direction>();

            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand("SELECT id, libelle, niveau FROM direction ORDER BY id", connection))
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        directions.Add(new Direction
                        {
                            Id = reader.GetInt64(0),
                            Libelle = reader.GetString(1),
                            Niveau = reader.GetInt32(2)
                        });
                    }
                }
            }

            return directions;
        }
    }
}
