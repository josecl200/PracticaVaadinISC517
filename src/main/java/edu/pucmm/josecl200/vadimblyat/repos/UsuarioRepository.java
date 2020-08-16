package edu.pucmm.josecl200.vadimblyat.repos;

import edu.pucmm.josecl200.vadimblyat.entidades.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query("select usuario from Usuario usuario where usuario.correo = :correo and usuario.password = :contrasena")
    Usuario findByEmailAndPassword(@Param("correo") String correo, @Param("contrasena") String contrasena);

    @Override
    long count();

    @Override
    List<Usuario> findAll();
}
