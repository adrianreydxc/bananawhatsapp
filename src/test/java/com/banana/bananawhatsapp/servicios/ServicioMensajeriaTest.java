package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

class ServicioMensajeriaTest {
    IUsuarioRepository repoUsuario;
    IServicioMensajeria servicio;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();
        String texto = "Felices Fiestas!";
        Mensaje message = servicio.enviarMensaje(remitente, destinatario, texto);
        assertThat(message.getId(), greaterThan(0L));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();
        String texto = "SMS < 10";
        assertThrows(Exception.class, () -> {
            servicio.enviarMensaje(remitente, destinatario, texto);
        });
    }


    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();

        List<Mensaje> userMessages = servicio.mostrarChatConUsuario(remitente, destinatario);
        assertNotNull(userMessages);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = new Usuario(2L, null, null, null, false);
        assertThrows(Exception.class, () -> {
            List<Mensaje> userMessages = servicio.mostrarChatConUsuario(remitente, destinatario);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = repoUsuario.findById(2L).orElseThrow();
        boolean borrarChat = servicio.borrarChatConUsuario(remitente, destinatario);
        assertTrue(borrarChat);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() throws Exception {
        Usuario remitente = repoUsuario.findById(1L).orElseThrow();
        Usuario destinatario = new Usuario(2L, null, null, null, false);
        assertThrows(Exception.class, () -> {
            boolean borrarChat = servicio.borrarChatConUsuario(remitente, destinatario);
        });
    }
}