using Microsoft.AspNetCore.Mvc;
using Global.Services;
using Global.Models;
using System.Collections.Generic;

namespace Global.Controllers
{
    [ApiController]
    [Route("utilisateur")]
    public class UtilisateurController : ControllerBase
    {
        private readonly UtilisateurService _service;

        public UtilisateurController(UtilisateurService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Utilisateur>> GetAll()
        {
            var utilisateurs = _service.GetAllUtilisateurs();
            return Ok(utilisateurs);
        }

        [HttpPost("login")]
        public ActionResult<Utilisateur> Login([FromForm] string login, [FromForm] string password)
        {
            var utilisateur = _service.Authentifier(login, password);

            if (utilisateur == null)
                return Unauthorized(new { message = "Login ou mot de passe incorrect" });

            // Retourne l'utilisateur complet (avec Direction)
            return Ok(utilisateur);
        }
    }
}
