/*
 * Copyright 2013 pc01.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author pc01
 */
@Entity
@Table(name = "DOC_GENERICO")
@NamedQueries({
        @NamedQuery(name = "DocGenerico.findAll", query = "SELECT d FROM DocGenerico d")})
public class DocGenerico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_GENERICO")
    private Long idGenerico;
    @Column(name = "TIPO_GENERICO")
    private String tipoGenerico;
    @Column(name = "FECHA_01")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha01;
    @Column(name = "FECHA_02")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha02;
    @Column(name = "FECHA_03")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha03;
    @Column(name = "CADENA_01")
    private String cadena01;
    @Column(name = "CADENA_02")
    private String cadena02;
    @Column(name = "CADENA_03")
    private String cadena03;
    @Column(name = "CADENA_04")
    private String cadena04;
    @Column(name = "CADENA_05")
    private String cadena05;
    @Column(name = "CADENA_06")
    private String cadena06;
    @Column(name = "CADENA_07")
    private String cadena07;
    @Column(name = "CADENA_08")
    private String cadena08;
    @Column(name = "CADENA_09")
    private String cadena09;
    @Column(name = "CADENA_10")
    private String cadena10;
    @Column(name = "CADENA_11")
    private String cadena11;
    @Column(name = "CADENA_12")
    private String cadena12;
    @Column(name = "CADENA_13")
    private String cadena13;
    @Column(name = "CADENA_14")
    private String cadena14;
    @Column(name = "CADENA_15")
    private String cadena15;
    @Column(name = "ENTERO_01")
    private Integer entero01;
    @Column(name = "ENTERO_02")
    private Integer entero02;
    @Column(name = "ENTERO_03")
    private Integer entero03;
    @Column(name = "ENTERO_04")
    private Integer entero04;
    @Column(name = "ENTERO_05")
    private Integer entero05;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_01")
    private BigDecimal valor01;
    @Column(name = "VALOR_02")
    private BigDecimal valor02;
    @Column(name = "VALOR_03")
    private BigDecimal valor03;
    @Column(name = "VALOR_04")
    private BigDecimal valor04;
    @Column(name = "VALOR_05")
    private BigDecimal valor05;
    @JoinColumns({
            @JoinColumn(name = "TIPO_PERIODO", referencedColumnName = "TIPO_PERIODO"),
            @JoinColumn(name = "GESTION", referencedColumnName = "GESTION")})
    @ManyToOne(fetch = FetchType.EAGER)
    private ParCalendario parCalendario;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private DocDocumento idDocumento;

    public DocGenerico() {
    }

    public DocGenerico(Long idGenerico) {
        this.idGenerico = idGenerico;
    }

    public Long getIdGenerico() {
        return idGenerico;
    }

    public void setIdGenerico(Long idGenerico) {
        this.idGenerico = idGenerico;
    }

    public String getTipoGenerico() {
        return tipoGenerico;
    }

    public void setTipoGenerico(String tipoGenerico) {
        this.tipoGenerico = tipoGenerico;
    }

    public Date getFecha01() {
        return fecha01;
    }

    public void setFecha01(Date fecha01) {
        this.fecha01 = fecha01;
    }

    public Date getFecha02() {
        return fecha02;
    }

    public void setFecha02(Date fecha02) {
        this.fecha02 = fecha02;
    }

    public Date getFecha03() {
        return fecha03;
    }

    public void setFecha03(Date fecha03) {
        this.fecha03 = fecha03;
    }

    public String getCadena01() {
        return cadena01;
    }

    public void setCadena01(String cadena01) {
        this.cadena01 = cadena01;
    }

    public String getCadena02() {
        return cadena02;
    }

    public void setCadena02(String cadena02) {
        this.cadena02 = cadena02;
    }

    public String getCadena03() {
        return cadena03;
    }

    public void setCadena03(String cadena03) {
        this.cadena03 = cadena03;
    }

    public String getCadena04() {
        return cadena04;
    }

    public void setCadena04(String cadena04) {
        this.cadena04 = cadena04;
    }

    public String getCadena05() {
        return cadena05;
    }

    public void setCadena05(String cadena05) {
        this.cadena05 = cadena05;
    }

    public String getCadena06() {
        return cadena06;
    }

    public void setCadena06(String cadena06) {
        this.cadena06 = cadena06;
    }

    public String getCadena07() {
        return cadena07;
    }

    public void setCadena07(String cadena07) {
        this.cadena07 = cadena07;
    }

    public String getCadena08() {
        return cadena08;
    }

    public void setCadena08(String cadena08) {
        this.cadena08 = cadena08;
    }

    public String getCadena09() {
        return cadena09;
    }

    public void setCadena09(String cadena09) {
        this.cadena09 = cadena09;
    }

    public String getCadena10() {
        return cadena10;
    }

    public void setCadena10(String cadena10) {
        this.cadena10 = cadena10;
    }

    public String getCadena11() {
        return cadena11;
    }

    public void setCadena11(String cadena11) {
        this.cadena11 = cadena11;
    }

    public String getCadena12() {
        return cadena12;
    }

    public void setCadena12(String cadena12) {
        this.cadena12 = cadena12;
    }

    public String getCadena13() {
        return cadena13;
    }

    public void setCadena13(String cadena13) {
        this.cadena13 = cadena13;
    }

    public String getCadena14() {
        return cadena14;
    }

    public void setCadena14(String cadena14) {
        this.cadena14 = cadena14;
    }

    public String getCadena15() {
        return cadena15;
    }

    public void setCadena15(String cadena15) {
        this.cadena15 = cadena15;
    }

    public Integer getEntero01() {
        return entero01;
    }

    public void setEntero01(Integer entero01) {
        this.entero01 = entero01;
    }

    public Integer getEntero02() {
        return entero02;
    }

    public void setEntero02(Integer entero02) {
        this.entero02 = entero02;
    }

    public Integer getEntero03() {
        return entero03;
    }

    public void setEntero03(Integer entero03) {
        this.entero03 = entero03;
    }

    public Integer getEntero04() {
        return entero04;
    }

    public void setEntero04(Integer entero04) {
        this.entero04 = entero04;
    }

    public Integer getEntero05() {
        return entero05;
    }

    public void setEntero05(Integer entero05) {
        this.entero05 = entero05;
    }

    public BigDecimal getValor01() {
        return valor01;
    }

    public void setValor01(BigDecimal valor01) {
        this.valor01 = valor01;
    }

    public BigDecimal getValor02() {
        return valor02;
    }

    public void setValor02(BigDecimal valor02) {
        this.valor02 = valor02;
    }

    public BigDecimal getValor03() {
        return valor03;
    }

    public void setValor03(BigDecimal valor03) {
        this.valor03 = valor03;
    }

    public BigDecimal getValor04() {
        return valor04;
    }

    public void setValor04(BigDecimal valor04) {
        this.valor04 = valor04;
    }

    public BigDecimal getValor05() {
        return valor05;
    }

    public void setValor05(BigDecimal valor05) {
        this.valor05 = valor05;
    }

    public ParCalendario getParCalendario() {
        return parCalendario;
    }

    public void setParCalendario(ParCalendario parCalendario) {
        this.parCalendario = parCalendario;
    }

    public DocDocumento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocDocumento idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenerico != null ? idGenerico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocGenerico)) {
            return false;
        }
        DocGenerico other = (DocGenerico) object;
        if ((this.idGenerico == null && other.idGenerico != null) || (this.idGenerico != null && !this.idGenerico.equals(other.idGenerico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocGenerico[ idGenerico=" + idGenerico + " ]";
    }

}
