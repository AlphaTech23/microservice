using System.Collections.Generic;
using Global.Models;
using Global.Repositories;
using Microsoft.Extensions.Configuration;

namespace Global.Services
{
    public class ActionRoleService
    {
        private readonly ActionRoleRepository _repository;

        public ActionRoleService(IConfiguration configuration)
        {
            _repository = new ActionRoleRepository(configuration);
        }

        public IEnumerable<ActionRole> GetAllActionRoles()
        {
            return _repository.GetAll();
        }

        public ActionRole GetRoleForTableAndAction(string nomTable, string action)
        {
            return _repository.GetByTableAndAction(nomTable, action);
        }

        public int? GetRequiredRole(string nomTable, string action)
        {
            return _repository.GetRequiredRole(nomTable, action);
        }

        public IEnumerable<ActionRole> GetActionsForTable(string nomTable)
        {
            return _repository.GetByTable(nomTable);
        }

        public IEnumerable<ActionRole> GetActionsForRole(int role)
        {
            return _repository.GetByRole(role);
        }
    }
}