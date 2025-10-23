package com.example.centralisateur.dto;

public class UtilisateurDTO {
    private Long id;

    private String login;
    private String password;
    private int role;
    private DirectionDTO direction;

    // === GETTERS & SETTERS ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }

    public DirectionDTO getDirection() { return direction; }
    public void setDirection(DirectionDTO direction) { this.direction = direction; }
    @Override

    public String toString() {
        return "{id=" + id + ", login='" + login + "', role=" + role + ", direction=" + direction.toString() + "}";
    }

}
