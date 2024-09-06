package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
@ExtendWith(SpringExtension.class)
class MensajeRepositoryTest {

    @Autowired
    IUsuarioRepository repoUsuario;
    @Autowired
    IMensajeRepository repoMensaje;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    @Order(1)
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();

        Mensaje message = new Mensaje(null, remitente, destinatario, "De acuerdo Juana. Un saludo.", LocalDate.now());

        repoMensaje.save(message);
        assertThat(message, notNullValue());
        assertThat(message.getId(), greaterThan(0L));
    }

    @Test
    @Order(2)
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() throws Exception {
        Usuario remitente = new Usuario(1L, null, null, null, true);
        Usuario destinatario = new Usuario(2L, null, null, null, true);
        Mensaje message = new Mensaje(null, destinatario, remitente, "SMS < 10", LocalDate.now());
        assertThrows(Exception.class, () -> {
            repoMensaje.save(message);
        });
    }

    @Test
    @Order(3)
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws Exception {
        Usuario user = repoUsuario.findById(1L).orElseThrow();

        List<Mensaje> userMessages = repoMensaje.getMessageByUser(user).orElseThrow();
        assertNotNull(userMessages);
    }

    @Test
    @Order(4)
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(1L, null, null, null, false);

        assertThrows(UsuarioException.class, () -> {
            List<Mensaje> userMessages = repoMensaje.getMessageByUser(user).orElseThrow();
        });
    }

    @Test
    @Order(5)
    void dadoUnRemitenteValido_cuandoBorrarEntre_entoncesOK() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();

        boolean borrarChat;

        try{
             repoMensaje.borrarEntre(remitente, destinatario);
             borrarChat = true;
        }catch (Exception e){
            borrarChat = false;
        }
        assertTrue(borrarChat);
    }

    @Test
    @Order(6)
    void dadoUnRemitenteNOValido_cuandoBorrarEntre_entoncesExcepcion() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        remitente.setActivo(false);
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();

        assertThrows(UsuarioException.class, () -> {
            repoMensaje.borrarEntre(remitente, destinatario);
        });
    }

    @Test
    @Order(7)
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws Exception {
        Usuario user = repoUsuario.findById(1L).orElseThrow();

        boolean borrarChat;
        try{
            repoMensaje.borrarTodoByUser(user);
            borrarChat = true;
        }catch (Exception e){
            borrarChat = false;
        }
        assertTrue(borrarChat);
    }

    @Test
    @Order(8)
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(123434L, null, null, null, true);

        assertThrows(UsuarioException.class, () -> {
            repoMensaje.borrarTodoByUser(user);
        });
    }

}