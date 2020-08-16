package edu.pucmm.josecl200.vadimblyat.visual;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import edu.pucmm.josecl200.vadimblyat.entidades.Usuario;
import edu.pucmm.josecl200.vadimblyat.repos.UsuarioRepository;
import edu.pucmm.josecl200.vadimblyat.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("gerentes")
@SpringComponent
@UIScope
public class CRUDGerente extends VerticalLayout {
    boolean editando = false;
    Integer usuarioSeleccionadoID;
    final Grid<Usuario> grid;


    public CRUDGerente(@Autowired UsuarioService userService) {
        TextField nombre = new TextField("Nombre:");
        TextField email = new TextField("Email:");
        PasswordField contrasena = new PasswordField("Contraseña:");

        this.grid = new Grid<>(Usuario.class);
        add(grid);
        listCustomers(userService);

        Binder<Usuario> binder = new Binder<>();
        Grid<Usuario> tabla = new Grid<>();

        Button agregar = new Button("Salvar");
        agregar.getElement().setAttribute("theme", "primary");

        Button cancelar = new Button("Cancelar");
        cancelar.getElement().setAttribute("theme", "error");

        PantallaAccionesGerente pantallaAccionesGerente = new PantallaAccionesGerente();

        if (userService.listUsers().isEmpty())
            getUI().get().navigate("");
        else if (!userService.listUsers().get(0).isInicioSesion())
            getUI().get().navigate("");
        else {
            agregar.addClickListener((evento) -> {
                try {
                    Integer id;
                    if (editando) { userService.createUser(
                            usuarioSeleccionadoID,
                            nombre.getValue(),
                            email.getValue(),
                            contrasena.getValue()
                    );}else{
                        userService.createUser(
                                nombre.getValue(),
                                email.getValue(),
                                contrasena.getValue()
                        );
                    }


                } catch (Exception exp) {
                    exp.printStackTrace();
                }

                nombre.setValue("");
                email.setValue("");
                contrasena.setValue("");

            });

            cancelar.addClickListener((evento) -> {
                nombre.setValue("");
                email.setValue("");
                contrasena.setValue("");
            });


            H4 titulo = new H4("CALENDARIO VAADIN ISC-517");
            H6 subtitulo = new H6("Eventos");

            HorizontalLayout botones = new HorizontalLayout();

            Button calendario = new Button("Volver al Calendario");

            Button salir = new Button("Salir");
            salir.getElement().setAttribute("theme", "error");

            botones.add(calendario, salir);

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

            HorizontalLayout botoneslayout = new HorizontalLayout(agregar, cancelar);
            botoneslayout.setSpacing(true);

            nombre.setTitle("Nombre: ");
            email.setTitle("Email: ");
            contrasena.setTitle("Contraseña: ");

            tabla.setDataProvider(grid.getDataProvider());
            tabla.addColumn(Usuario::getNombre).setHeader("Nombre");
            tabla.addColumn(Usuario::getCorreo).setHeader("Email");

            tabla.addSelectionListener(event -> {
                if (event.getFirstSelectedItem().isPresent()) {
                    abrirPantalla(pantallaAccionesGerente);
                    pantallaAccionesGerente.eliminar.addClickListener((evento) -> {
                        Usuario user = event.getFirstSelectedItem().get();
                        userService.removeUser((int) user.getId());
                        binder.readBean(user);
                    });

                    pantallaAccionesGerente.modificar.addClickListener((evento) -> {
                        Usuario user = event.getFirstSelectedItem().get();

                        nombre.setValue(user.getNombre());
                        email.setValue(user.getCorreo());
                        contrasena.setValue(user.getPassword());
                        editando = true;
                        usuarioSeleccionadoID = (int) user.getId();

                        try {
                            binder.writeBean(user);
                        } catch (ValidationException e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

            setAlignItems(Alignment.CENTER);
            FormLayout form = new FormLayout(nombre, email, contrasena);

            add(titulo, subtitulo, botones, form, botoneslayout, tabla);

            nombre.setValue("");
            email.setValue("");
            contrasena.setValue("");
        }
    }
    private void listCustomers(UsuarioService userService) {
        grid.setItems(userService.listUsersWithPagination());
    }
    private void abrirPantalla(VerticalLayout form) {
        Dialog vistaPantalla = new Dialog();
        vistaPantalla.add(form);

        vistaPantalla.open();
    }
}
