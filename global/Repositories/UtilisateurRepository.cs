#nullable enable
using System.Collections.Generic;
using Npgsql;
using Global.Models;
using Microsoft.Extensions.Configuration;

namespace Global.Repositories
{
    public class UtilisateurRepository
    {
        private readonly string _connectionString;

        public UtilisateurRepository(IConfiguration configuration)
        {
            _connectionString = configuration.GetConnectionString("DefaultConnection");
        }

        public IEnumerable<Utilisateur> GetAll()
        {
            var utilisateurs = new List<Utilisateur>();

            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();

                string sql = @"
                    SELECT u.id, u.login, u.password, u.role, 
                           d.id AS dir_id, d.libelle, d.niveau
                    FROM utilisateur u
                    JOIN direction d ON d.id = u.id_direction
                    ORDER BY u.id;
                ";

                using (var cmd = new NpgsqlCommand(sql, connection))
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        utilisateurs.Add(new Utilisateur
                        {
                            Id = reader.GetInt64(0),
                            Login = reader.GetString(1),
                            Password = reader.GetString(2),
                            Role = reader.GetInt32(3),
                            Direction = new Direction
                            {
                                Id = reader.GetInt64(4),
                                Libelle = reader.GetString(5),
                                Niveau = reader.GetInt32(6)
                            }
                        });
                    }
                }
            }

            return utilisateurs;
        }

        public Utilisateur? GetByLoginAndPassword(string login, string password)
        {
            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand(@"
                    SELECT u.id, u.login, u.password, u.role, u.id_direction,
                           d.id AS direction_id, d.libelle, d.niveau
                    FROM utilisateur u
                    JOIN direction d ON u.id_direction = d.id
                    WHERE u.login = @login AND u.password = @password", connection))
                {
                    cmd.Parameters.AddWithValue("@login", login);
                    cmd.Parameters.AddWithValue("@password", password);

                    using (var reader = cmd.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Utilisateur
                            {
                                Id = reader.GetInt64(reader.GetOrdinal("id")),
                                Login = reader.GetString(reader.GetOrdinal("login")),
                                Password = reader.GetString(reader.GetOrdinal("password")),
                                Role = reader.GetInt32(reader.GetOrdinal("role")),
                                Direction = new Direction
                                {
                                    Id = reader.GetInt64(reader.GetOrdinal("direction_id")),
                                    Libelle = reader.GetString(reader.GetOrdinal("libelle")),
                                    Niveau = reader.GetInt32(reader.GetOrdinal("niveau"))
                                }
                            };
                        }
                    }
                }
            }
            return null;
        }
    }

}
