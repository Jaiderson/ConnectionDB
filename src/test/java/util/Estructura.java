/*
 * Proyecto Robot FX Propiedad de CHOUCAIR CARDENAS TESTING S. A.
 * el presente proyecto fue iniciativa del equipo de Migracion - BI
 * agradecimiento es pecial al colaborador Jaider Adriam Serrano Sepulveda.
 * Medellin - Colombia 2016.
 */
package util;

import java.util.Objects;

/**
 *
 * @author Jaider Adriam Serrano Sepulveda.
 */
public class Estructura implements Comparable<Estructura> {
    
    private String tabla;
    private String campo;    
    private String tipoDato;
    private int longitud;
    private String digitosDecimales;
    private String comentario;
    //Enumerado de java.sql.Types;
    private int sqlTypes;

    public Estructura(String tabla, String campo, String tipoDato, int longitud, String digitosDecimales, int sqlTypes, String comentario) {
        this.tabla = tabla;
        this.campo = campo;    
        this.tipoDato = tipoDato;
        this.longitud = longitud;
        this.digitosDecimales = digitosDecimales;
        this.sqlTypes = sqlTypes;
        this.comentario = comentario;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getDigitosDecimales() {
        return digitosDecimales;
    }

    public void setDigitosDecimales(String digitosDecimales) {
        this.digitosDecimales = digitosDecimales;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getSqlTypes() {
        return sqlTypes;
    }

    public void setSqlTypes(int sqlTypes) {
        this.sqlTypes = sqlTypes;
    }

    @Override
    public String toString() {
        return "DetalleCampo{" + "tabla=" + tabla + ", campo=" + campo + ", tipoDato=" + tipoDato + ", longitud=" + longitud + ", digitosDecimales=" + digitosDecimales + ", comentario=" + comentario + ", sqlTypes=" + sqlTypes + '}';
    }

    @Override
    public int compareTo(Estructura o) {
        return (this.tabla+"."+this.campo).compareTo(o.getTabla()+"."+o.getCampo());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.tabla);
        hash = 83 * hash + Objects.hashCode(this.campo);
        hash = 83 * hash + Objects.hashCode(this.tipoDato);
        hash = 83 * hash + Objects.hashCode(this.longitud);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estructura other = (Estructura) obj;
        if (!Objects.equals(this.tabla, other.tabla)) {
            return false;
        }
        if (!Objects.equals(this.campo, other.campo)) {
            return false;
        }
        if (!Objects.equals(this.tipoDato, other.tipoDato)) {
            return false;
        }
        if (!Objects.equals(this.digitosDecimales, other.digitosDecimales)) {
            return false;
        }
        return Objects.equals(this.longitud, other.longitud);
    }

    public String getInsertSQL(){
        String sql =  "INSERT INTO estructura_campo(tabla, campo, tipo_dato, longitud, digitos, comentario)" +
               "VALUES ('"+tabla+"', '"+campo+"', '"+tipoDato+"', '"+longitud+"', '"+digitosDecimales+"', '"+comentario+"')";
        return sql.replace("'null'", "null");
    }

}// Fin clase DetalleCampo.