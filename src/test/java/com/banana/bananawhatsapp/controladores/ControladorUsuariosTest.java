package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControladorUsuariosTest {
    ControladorUsuarios controladorUsuarios;

    IUsuarioRepository repoUser;


    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }
    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true);
        controladorUsuarios.alta(nuevo);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0L));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        Usuario user = new Usuario(null, "Gema", "g@gccom", LocalDate.now(), true);
        assertThrows(Exception.class, () -> {
            controladorUsuarios.alta(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws Exception {
        Long iDUser = 2L;
        LocalDate fecha = LocalDate.parse("2023-12-17");
        Usuario user = repoUser.findById(iDUser).orElseThrow();
        user.setNombre("Juan Luis");
        user.setEmail("jl@jll.com");
        user.setAlta(fecha);
        controladorUsuarios.actualizar(user);
        assertThat(repoUser.findById(iDUser).orElseThrow().getNombre(), is("Juan Luis"));
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            Long iDUser = 3L;
            LocalDate fecha = LocalDate.parse("2025-12-17");
            Usuario user = repoUser.findById(iDUser).orElseThrow();
            user.setNombre("Juan Luis");
            user.setEmail("jl@jll.com");
            user.setAlta(fecha);
            controladorUsuarios.actualizar(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() throws Exception {
        Usuario user = repoUser.findById(1L).orElseThrow();
        boolean ok = controladorUsuarios.baja(user);
        assertThat(ok, is(true));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        Usuario user = new Usuario(-1L, null, null, null, true);
        assertThrows(Exception.class, () -> {
            controladorUsuarios.baja(user);
        });
    }
}