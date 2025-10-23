using Microsoft.AspNetCore.Mvc;
using CompteDepot.Services;
using CompteDepot.Models;
using System.Collections.Generic;

namespace CompteDepot.Controllers
{
    [ApiController]
    [Route("depot")]
    public class DepotController : ControllerBase
    {
        private readonly DepotService _service;

        public DepotController(DepotService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Depot>> GetAll()
        {
            var depots = _service.GetAllDepots();
            return Ok(depots);
        }
    }
}
