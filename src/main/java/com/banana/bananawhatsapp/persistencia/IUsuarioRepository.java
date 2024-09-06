package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.SQLException;
import java.util.Set;

public interface IUsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query("Select ")
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException;

}
