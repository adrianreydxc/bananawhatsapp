package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface IMensajeRepository extends JpaRepository<Mensaje, Long> {
   @Query("SELECT u FROM Mensaje u WHERE u.destinatario = :user OR u.remitente = :user")
   public Optional<List<Mensaje>> getMessageByUser(@Param("user") Usuario user);

    @Query("DELETE FROM Mensaje u WHERE u.remitente = :remitente AND u.destinatario = :destinatario")
    public void borrarEntre(@Param("remitente") Usuario remitente,
                     @Param("destinatario") Usuario destinatario);

    @Query("DELETE FROM Mensaje u WHERE u.remitente = :remitente")
    public void borrarTodoByUser(@Param("remitente") Usuario usuario);
}
