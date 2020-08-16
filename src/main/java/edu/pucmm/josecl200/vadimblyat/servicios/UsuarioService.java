package edu.pucmm.josecl200.vadimblyat.servicios;


import edu.pucmm.josecl200.vadimblyat.entidades.Usuario;
import edu.pucmm.josecl200.vadimblyat.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository userRepo;

    public Usuario createUser(String name, String mail, String password) throws Exception{
        try{
            Usuario usuario = new Usuario();
            usuario.setCorreo(mail);
            usuario.setNombre(name);
            usuario.setPassword(password);
            return userRepo.save(usuario);
        } catch (PersistenceException e){
            throw new PersistenceException("Hubo un error al editar el usuario.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Al editar el usuario hubo un error de datos nulos.");
        } catch (Exception e) {
            throw new Exception("Hubo un error general al editar un usuario.");
        }
    }
    public Usuario createUser(Integer id,String  name, String mail, String password) throws Exception{
        try{
            Usuario usuario = new Usuario();
            usuario.setId(id);
            usuario.setCorreo(mail);
            usuario.setNombre(name);
            usuario.setPassword(password);
            return userRepo.save(usuario);
        } catch (PersistenceException e){
            throw new PersistenceException("Hubo un error al editar el usuario.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Al editar el usuario hubo un error de datos nulos.");
        } catch (Exception e) {
            throw new Exception("Hubo un error general al editar un usuario.");
        }
    }
    public boolean validate(String email, String password) {
        Usuario user = userRepo.findByEmailAndPassword(email, password);
        return (user != null);
    }

    public List<Usuario> listUsers() {
        return userRepo.findAll();
    }


    public List<Usuario> listUsersWithPagination(){
        return userRepo.findAll();
    }

    public void editUser(Usuario user) throws Exception {
        try {
            userRepo.save(user);
        } catch (PersistenceException e) {
            throw new PersistenceException("Hubo un error al editar el usuario.");
        } catch (NullPointerException e) {
            throw new NullPointerException("Al editar el usuario hubo un error de datos nulos.");
        } catch (Exception e) {
            throw new Exception("Hubo un error general al editar un usuario.");
        }
    }

    @Transactional
    public long totalUsers() {
        return userRepo.count()+1;
    }

    @Transactional
    public void removeUser(Integer usuarioID){
        userRepo.deleteById(usuarioID);
    }

    @Transactional
    public Usuario findUser(Integer usuarioID){
        return userRepo.getOne(usuarioID);
    }

}
