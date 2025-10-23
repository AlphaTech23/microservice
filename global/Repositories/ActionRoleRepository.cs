using System.Collections.Generic;
using Npgsql;
using Global.Models;
using Microsoft.Extensions.Configuration;

namespace Global.Repositories
{
    public class ActionRoleRepository
    {
        private readonly string _connectionString;

        public ActionRoleRepository(IConfiguration configuration)
        {
            _connectionString = configuration.GetConnectionString("DefaultConnection");
        }

        public IEnumerable<ActionRole> GetAll()
        {
            var actionRoles = new List<ActionRole>();

            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand("SELECT id, nom_table, action, role FROM action_role ORDER BY id", connection))
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        actionRoles.Add(new ActionRole
                        {
                            Id = reader.GetInt64(0),
                            NomTable = reader.GetString(1),
                            Action = reader.GetString(2),
                            Role = reader.GetInt32(3)
                        });
                    }
                }
            }

            return actionRoles;
        }

        public ActionRole GetByTableAndAction(string nomTable, string action)
        {
            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand(
                    "SELECT id, nom_table, action, role FROM action_role WHERE LOWER(nom_table) = LOWER(@nomTable) AND LOWER(action) = LOWER(@action)", 
                    connection))
                {
                    cmd.Parameters.AddWithValue("@nomTable", nomTable);
                    cmd.Parameters.AddWithValue("@action", action);

                    using (var reader = cmd.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new ActionRole
                            {
                                Id = reader.GetInt64(0),
                                NomTable = reader.GetString(1),
                                Action = reader.GetString(2),
                                Role = reader.GetInt32(3)
                            };
                        }
                    }
                }
            }

            return null;
        }

        public int? GetRequiredRole(string nomTable, string action)
        {
            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand(
                    "SELECT role FROM action_role WHERE LOWER(nom_table) = LOWER(@nomTable) AND LOWER(action) = LOWER(@action)", 
                    connection))
                {
                    cmd.Parameters.AddWithValue("@nomTable", nomTable);
                    cmd.Parameters.AddWithValue("@action", action);

                    var result = cmd.ExecuteScalar();
                    return result != null ? (int)result : null;
                }
            }
        }

        public IEnumerable<ActionRole> GetByTable(string nomTable)
        {
            var actionRoles = new List<ActionRole>();

            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand(
                    "SELECT id, nom_table, action, role FROM action_role WHERE LOWER(nom_table) = LOWER(@nomTable) ORDER BY action", 
                    connection))
                {
                    cmd.Parameters.AddWithValue("@nomTable", nomTable);

                    using (var reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            actionRoles.Add(new ActionRole
                            {
                                Id = reader.GetInt64(0),
                                NomTable = reader.GetString(1),
                                Action = reader.GetString(2),
                                Role = reader.GetInt32(3)
                            });
                        }
                    }
                }
            }

            return actionRoles;
        }

        public IEnumerable<ActionRole> GetByRole(int role)
        {
            var actionRoles = new List<ActionRole>();

            using (var connection = new NpgsqlConnection(_connectionString))
            {
                connection.Open();
                using (var cmd = new NpgsqlCommand(
                    "SELECT id, nom_table, action, role FROM action_role WHERE role = @role ORDER BY nom_table, action", 
                    connection))
                {
                    cmd.Parameters.AddWithValue("@role", role);

                    using (var reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            actionRoles.Add(new ActionRole
                            {
                                Id = reader.GetInt64(0),
                                NomTable = reader.GetString(1),
                                Action = reader.GetString(2),
                                Role = reader.GetInt32(3)
                            });
                        }
                    }
                }
            }

            return actionRoles;
        }
    }
}