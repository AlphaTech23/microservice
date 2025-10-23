using Microsoft.AspNetCore.Mvc;
using Global.Services;
using Global.Models;
using System.Collections.Generic;

namespace Global.Controllers
{
    [ApiController]
    [Route("direction")]
    public class DirectionController : ControllerBase
    {
        private readonly DirectionService _service;

        public DirectionController(DirectionService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Direction>> GetAll()
        {
            var directions = _service.GetAllDirections();
            return Ok(directions);
        }
    }
}
