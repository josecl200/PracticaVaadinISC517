package edu.pucmm.josecl200.vadimblyat.visual;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import edu.pucmm.josecl200.vadimblyat.entidades.Usuario;
import edu.pucmm.josecl200.vadimblyat.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("usuario")
@SpringComponent
@UIScope
public class PantallaUsuario extends VerticalLayout {

    public PantallaUsuario(@Autowired UsuarioService userService) {
        Usuario user;

        HorizontalLayout horizontalLayout;
        HorizontalLayout botones;
        FormLayout editarInformacion;

        if (userService.listUsers().isEmpty())
            getUI().get().navigate("");
        else if (!userService.listUsers().get(0).isInicioSesion())
            getUI().get().navigate("");
        else {
            user = userService.listUsers().get(0);

            /* ********* CONFIGURAR ESTILO ********** */
            horizontalLayout = new HorizontalLayout();
            botones = new HorizontalLayout();

            setAlignItems(Alignment.CENTER);

            horizontalLayout.setMargin(true);
            horizontalLayout.setSpacing(true);
            horizontalLayout.setSizeFull();
            horizontalLayout.setAlignItems(Alignment.CENTER);

            /* ********* HEADER ********** */
            H4 titulo = new H4("CALENDARIO VAADIN ISC-517");
            H6 subtitulo = new H6("Eventos");

            Button calendario = new Button("Volver al Calendario");
            calendario.setIcon(new Icon(VaadinIcon.ARROW_CIRCLE_LEFT_O));

            Button salir = new Button("Salir");
            salir.setIcon(new Icon(VaadinIcon.SIGN_OUT));
            salir.getElement().setAttribute("theme", "error");

            salir.addClickListener((evento) -> {
                try {
                    Usuario usuarioaux = userService.listUsers().get(0);
                    usuarioaux.setInicioSesion(false);
                    userService.editUser(usuarioaux);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getUI().get().navigate("");
            });

            calendario.addClickListener((evento) -> getUI().get().navigate("calendario"));

            /* ********* INFORMACION DE USUARIO ********** */
            FormLayout fml = new FormLayout();

            H3 titulo1 = new H3("Datos del usuario");
            H6 nombre = new H6("Nombre: " + user.getNombre());
            H6 email = new H6("Correo electrónico: " + user.getCorreo());

            fml.add(titulo1, nombre, email);

            VerticalLayout editarVertical = new VerticalLayout();
            editarInformacion = new FormLayout();

            H3 titulo2 = new H3("Editar datos del usuario");

            TextField nuevoEmail = new TextField("Email");
            TextField nuevoNombre = new TextField("Nombre");

            Button guardar = new Button("Guardar cambios");
            guardar.setIcon(new Icon(VaadinIcon.DATABASE));

            editarInformacion.add(titulo2, nuevoNombre, nuevoEmail);
            editarVertical.add(titulo2, editarInformacion, guardar);

            /* ********* FILTRO DE INFORMACION ********** */
            guardar.addClickListener((evento) -> {
                try {
                    if (!nuevoEmail.getValue().equals(""))
                        user.setCorreo(nuevoEmail.getValue());

                    if (!nuevoNombre.getValue().equals(""))
                        user.setNombre(nuevoNombre.getValue());

                    userService.editUser(user);
                    getUI().get().getPage().reload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            horizontalLayout.add(fml, editarVertical);
            horizontalLayout.setAlignItems(Alignment.CENTER);

            botones.add(calendario, salir);

            add(titulo, subtitulo, botones, horizontalLayout);
        }
    }
}

