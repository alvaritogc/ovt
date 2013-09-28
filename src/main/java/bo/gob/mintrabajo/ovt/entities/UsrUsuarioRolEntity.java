package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@IdClass(UsrUsuarioRolEntityPK.class)
@Table(name = "USR_USUARIO_ROL", schema = "ROE", catalog = "")
@Entity
public class UsrUsuarioRolEntity implements Serializable {
    private int idUsuario;
    private int idRol;
    private Timestamp fechaBitacora;
    private String registroBitacora;
    private UsrModuloEntity usrModuloByIdModulo;
    private UsrRolEntity usrRolByIdRol;
    private UsrUsuarioEntity usrUsuarioByIdUsuario;

    @Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Column(name = "ID_ROL", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    @Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getRegistroBitacora() {
        return registroBitacora;
    }

    public void setRegistroBitacora(String registroBitacora) {
        this.registroBitacora = registroBitacora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrUsuarioRolEntity that = (UsrUsuarioRolEntity) o;

        if (idRol != that.idRol) return false;
        if (idUsuario != that.idUsuario) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        result = 31 * result + idRol;
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO", nullable = false)
    public UsrModuloEntity getUsrModuloByIdModulo() {
        return usrModuloByIdModulo;
    }

    public void setUsrModuloByIdModulo(UsrModuloEntity usrModuloByIdModulo) {
        this.usrModuloByIdModulo = usrModuloByIdModulo;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL", nullable = false)
    public UsrRolEntity getUsrRolByIdRol() {
        return usrRolByIdRol;
    }

    public void setUsrRolByIdRol(UsrRolEntity usrRolByIdRol) {
        this.usrRolByIdRol = usrRolByIdRol;
    }

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    public UsrUsuarioEntity getUsrUsuarioByIdUsuario() {
        return usrUsuarioByIdUsuario;
    }

    public void setUsrUsuarioByIdUsuario(UsrUsuarioEntity usrUsuarioByIdUsuario) {
        this.usrUsuarioByIdUsuario = usrUsuarioByIdUsuario;
    }
}
