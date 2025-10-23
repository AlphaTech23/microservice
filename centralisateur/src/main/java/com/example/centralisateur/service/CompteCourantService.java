// CompteCourantService.java
package com.example.centralisateur.service;

import com.example.centralisateur.dto.CompteCourantDTO;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class CompteCourantService {

    public List<CompteCourantDTO> getAllComptes() {
        Response response = UtilService.get(UtilService.getCourantAPI() + "/comptes");
        if (response.getStatus() == 200) {
            return response.readEntity(new jakarta.ws.rs.core.GenericType<List<CompteCourantDTO>>() {});
        }
        return null;
    }

    public CompteCourantDTO getCompteById(Long id) {
        Response response = UtilService.get(UtilService.getCourantAPI() + "/comptes/" + id);
        if (response.getStatus() == 200) {
            return response.readEntity(CompteCourantDTO.class);
        }
        return null;
    }
}