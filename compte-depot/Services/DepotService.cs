using System.Collections.Generic;
using CompteDepot.Models;
using CompteDepot.Repositories;
using Microsoft.Extensions.Configuration;

namespace CompteDepot.Services
{
    public class DepotService
    {
        private readonly DepotRepository _repository;

        public DepotService(IConfiguration configuration)
        {
            _repository = new DepotRepository(configuration);
        }

        public IEnumerable<Depot> GetAllDepots()
        {
            return _repository.GetAll();
        }
    }
}
