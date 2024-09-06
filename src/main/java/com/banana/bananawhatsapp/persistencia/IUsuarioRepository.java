package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String email);

    @Query(value = "SELECT u FROM Usuario u WHERE u.id <> ?1 LIMIT ?2", nativeQuery = true)
    public List<Usuario> obtenerPosiblesDestinatarios(Long id, Integer max) throws SQLException;

    public default void saveUser(@Param("user") Usuario user) throws Exception{
        if(user.getId() > 1){
            save(user);
        }else throw new Exception("Id inválido");
    }
}
