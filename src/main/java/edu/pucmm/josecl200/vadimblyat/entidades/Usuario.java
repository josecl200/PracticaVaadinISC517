package edu.pucmm.josecl200.vadimblyat.entidades;

import lombok.*;


import javax.persistence.*;
import java.io.Serializable;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Usuario implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String correo;
    private String password;
    private boolean inicioSesion;
}
