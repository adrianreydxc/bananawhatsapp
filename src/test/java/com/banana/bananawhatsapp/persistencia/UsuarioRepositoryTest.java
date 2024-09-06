package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.util.DBUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
@EnableAutoConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
@ExtendWith(SpringExtension.class)
class UsuarioRepositoryTest {
    @Autowired
    IUsuarioRepository repo;
    @Autowired
    IMensajeRepository mensajeRepository;

    @BeforeEach
    void cleanAndReloadData() {
        DBUtil.reloadDB();
    }

    @Test
    @Order(1)
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws Exception {
        Usuario nuevo = new Usuario(null, "Ricardo", "r@r.com", LocalDate.now(), true);
        repo.save(nuevo);

        assertThat(nuevo, notNullValue());
        assertThat(nuevo.getId(), greaterThan(0L));
    }

    @Test
    @Order(2)
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        Usuario nuevo = new Usuario(null, "Ricardo", "r", LocalDate.now(), true);
        assertThrows(Exception.class, () -> {
            repo.save(nuevo);
        });
    }

    @Test
    @Order(3)
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws Exception {
        Long iDUser = 1L;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true);
        repo.save(user);
        Usuario userUpdate = repo.findById(1L).get();


        assertThat(userUpdate.getNombre(), is("Juan"));
    }

    @Test
    @Order(4)
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws UsuarioException {
        Long iDUser = -1L;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true);
        assertThrows(NullPointerException.class, () -> {
                repo.saveUser(user);
        });
    }

    @Test
    @Order(5)
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException {
        Usuario user = new Usuario(1L, null, null, null, true);
        boolean ok;
        try{
            repo.delete(user);
            ok = true;
        }catch (Exception e){
            ok = false;
        }
        assertTrue(ok);
    }

    @Test
    @Order(6)
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(-1L, null, null, null, true);
        assertThrows(Exception.class, () -> {
            repo.delete(user);
        });
    }

    @Test
    @Order(7)
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() throws Exception {
        Long iDUser = 1L;
        int numPosibles = 100;
        Usuario user = new Usuario(iDUser, "Juan", "j@j.com", LocalDate.now(), true);

        List<Usuario> conjuntoDestinatarios = repo.obtenerPosiblesDestinatarios(user.getId(), numPosibles);
        assertTrue(conjuntoDestinatarios.size() <= numPosibles);
    }

    @Test
    @Order(8)
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() throws Exception {
        Usuario user = new Usuario(-1L, null, null, null, true);
        int numPosibles = 100;
        assertThrows(UsuarioException.class, () -> {
            List<Usuario> conjuntoDestinatarios = repo.obtenerPosiblesDestinatarios(user.getId(), numPosibles);
        });

    }


}