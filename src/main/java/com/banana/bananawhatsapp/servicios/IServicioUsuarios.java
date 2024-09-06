package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IServicioUsuarios {
    Usuario obtener(long id) throws UsuarioException, SQLException;

    Usuario crearUsuario(Usuario usuario) throws UsuarioException;

    boolean borrarUsuario(Usuario usuario) throws UsuarioException;

    Usuario actualizarUsuario(Usuario usuario) throws UsuarioException;

    List<Usuario> obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException, SQLException;
}
