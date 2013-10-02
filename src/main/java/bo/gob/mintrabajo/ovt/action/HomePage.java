package bo.gob.mintrabajo.ovt.action;

import bo.gob.mintrabajo.ovt.entities.DocDefinicionEntity;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import bo.gob.mintrabajo.ovt.api.Definicion;
import bo.gob.mintrabajo.ovt.repositories.UserRepository;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class HomePage extends WebPage {


    @SpringBean
    private UserRepository repositoryUser;

    @SpringBean
    private Definicion defincion;


    public HomePage() {
        add(new AjaxFallbackDefaultDataTable<UsrUsuarioEntity, String>(
                "listado de usuarios",
                new LinkedList<IColumn<UsrUsuarioEntity, String>>() {
                    {
                        add(new PropertyColumn<UsrUsuarioEntity, String>(Model.of("USUARIO"), "usuario"));
                        add(new PropertyColumn<UsrUsuarioEntity, String>(Model.of("CLAVE"), "clave"));
                    }
                },
                new SortableDataProvider<UsrUsuarioEntity, String>() {

                    @Override
                    public Iterator<? extends UsrUsuarioEntity> iterator(long first, long count) {
                        return repositoryUser.findByExample(null, null, null, first, count).iterator();
                    }

                    @Override
                    public long size() {
                        return repositoryUser.count();
                    }

                    @Override
                    public IModel<UsrUsuarioEntity> model(UsrUsuarioEntity object) {
                        return Model.of(object);
                    }
                },
                10
        ));

        add(new Link<Void>("insertar uno") {

            private Random random = new Random();

            @Override
            public void onClick() {
                final DocDefinicionEntity e1 = new DocDefinicionEntity();
                e1.setCodDocumento("Codigo "+random.nextInt(100));
                e1.setNombre("Nombre "+random.nextInt(100));
                e1.setTipoGrupoDocumento("Tipo"+random.nextInt(100));
                e1.setFechaBitacora(new Timestamp((new Date()).getTime()));
                e1.setRegistroBitacora("RegistroBitacora " +random.nextInt(10));

                defincion.guardarDefincion(e1);
            }
        });
    }
}
