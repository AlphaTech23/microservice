namespace Global.Models
{
    public class Utilisateur
    {
        public long Id { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public int Role { get; set; }
        public Direction Direction { get; set; }
    }
}
