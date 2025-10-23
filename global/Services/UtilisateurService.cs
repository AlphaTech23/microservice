using System.Collections.Generic;
using Global.Models;
using Global.Repositories;
using Microsoft.Extensions.Configuration;

namespace Global.Services
{
    public class UtilisateurService
    {
        private readonly UtilisateurRepository _repository;

        public UtilisateurService(IConfiguration configuration)
        {
            _repository = new UtilisateurRepository(configuration);
        }

        public IEnumerable<Utilisateur> GetAllUtilisateurs()
        {
            return _repository.GetAll();
        }

        public Utilisateur? Authentifier(string login, string password)
        {
            return _repository.GetByLoginAndPassword(login, password);
        }
    }
}
