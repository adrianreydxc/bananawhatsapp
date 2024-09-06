package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class ServicioUsuarios implements IServicioUsuarios{

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Override
    public Usuario obtener(long id) throws UsuarioException, SQLException {
        return usuarioRepository.findById(id).orElseThrow();
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if(optionalUsuario.isEmpty()){
            return usuarioRepository.save(usuario);
        }else{
            throw new UsuarioException();
        }
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        try{
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());
            if(optionalUsuario.isEmpty()){
                //TODO Hacer excepcion o return false
            }
            usuarioRepository.delete(usuario);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
             return false;
        }
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        try{
             Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());
            if(optionalUsuario.isEmpty()){
                //TODO Hacer excepcion o return false
            }
            return usuarioRepository.save(usuario);
        }catch (Exception e){
            System.out.println(e.getMessage());
             throw new UsuarioException();
        }
    }

    @Override
    public List<Usuario> obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException, SQLException {
        try{
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuario.getId());
            if(optionalUsuario.isEmpty()){
                //TODO Hacer excepcion o return false
            }
            return usuarioRepository.obtenerPosiblesDestinatarios(usuario.getId(), max);
        }catch (Exception e){
            throw new UsuarioException();
        }
    }
}
