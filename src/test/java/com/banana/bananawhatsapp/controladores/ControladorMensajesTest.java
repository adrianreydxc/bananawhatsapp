package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ControladorMensajesTest {

    ControladorMensajes controladorMensajes;

    IUsuarioRepository repoUser;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }
    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {
        Long remitente = 1L;
        Long destinatario = 2L;
        String texto = "Perfecto! Muchas gracias!";
        boolean sendMessage = controladorMensajes.enviarMensaje(remitente, destinatario, texto);
        assertTrue(sendMessage);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
        Long remitente = 1L;
        Long destinatario = 2L;
        String texto = "SMS < 10";
        assertThrows(Exception.class, () -> {
            controladorMensajes.enviarMensaje(remitente, destinatario, texto);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
        Long remitente = 1L;
        Long destinatario = 2L;
        boolean mostrarChat = controladorMensajes.mostrarChat(remitente, destinatario);
        assertTrue(mostrarChat);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
        Long remitente = 2L;
        Long destinatario = -1L;
        assertThrows(Exception.class, () -> {
            controladorMensajes.mostrarChat(remitente, destinatario);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {
        Long remitente = 1L;
        Long destinatario = 2L;
        boolean eliminarChat = controladorMensajes.eliminarChatConUsuario(remitente, destinatario);
        assertTrue(eliminarChat);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
        Long remitente = -1L;
        Long destinatario = 5L;
        assertThrows(Exception.class, () -> {
            controladorMensajes.eliminarChatConUsuario(remitente, destinatario);
        });
    }
}