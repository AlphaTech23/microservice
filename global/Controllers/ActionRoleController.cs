using Microsoft.AspNetCore.Mvc;
using Global.Services;
using Global.Models;
using System.Collections.Generic;

namespace Global.Controllers
{
    [ApiController]
    [Route("action-roles")]
    public class ActionRoleController : ControllerBase
    {
        private readonly ActionRoleService _service;

        public ActionRoleController(ActionRoleService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<ActionRole>> GetAll()
        {
            var actionRoles = _service.GetAllActionRoles();
            return Ok(actionRoles);
        }

        [HttpGet("table/{nomTable}/action/{action}")]
        public ActionResult<ActionRole> GetRoleForTableAndAction(string nomTable, string action)
        {
            var actionRole = _service.GetRoleForTableAndAction(nomTable, action);
            
            if (actionRole == null)
            {
                return NotFound($"Aucun rôle trouvé pour la table '{nomTable}' et l'action '{action}'");
            }
            
            return Ok(actionRole);
        }

        [HttpGet("table/{nomTable}/required-role")]
        public ActionResult<int> GetRequiredRoleForTableAndAction(string nomTable, [FromQuery] string action)
        {
            if (string.IsNullOrEmpty(action))
            {
                return BadRequest("Le paramètre 'action' est requis");
            }

            var requiredRole = _service.GetRequiredRole(nomTable, action);
            
            if (requiredRole == null)
            {
                return NotFound($"Aucun rôle requis trouvé pour la table '{nomTable}' et l'action '{action}'");
            }
            
            return Ok(requiredRole.Value);
        }

        [HttpGet("table/{nomTable}")]
        public ActionResult<IEnumerable<ActionRole>> GetActionsForTable(string nomTable)
        {
            var actions = _service.GetActionsForTable(nomTable);
            return Ok(actions);
        }

        [HttpGet("role/{role}")]
        public ActionResult<IEnumerable<ActionRole>> GetActionsForRole(int role)
        {
            var actions = _service.GetActionsForRole(role);
            return Ok(actions);
        }
    }
}