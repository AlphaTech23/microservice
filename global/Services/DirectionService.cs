using System.Collections.Generic;
using Global.Models;
using Global.Repositories;
using Microsoft.Extensions.Configuration;

namespace Global.Services
{
    public class DirectionService
    {
        private readonly DirectionRepository _repository;

        public DirectionService(IConfiguration configuration)
        {
            _repository = new DirectionRepository(configuration);
        }

        public IEnumerable<Direction> GetAllDirections()
        {
            return _repository.GetAll();
        }
    }
}
