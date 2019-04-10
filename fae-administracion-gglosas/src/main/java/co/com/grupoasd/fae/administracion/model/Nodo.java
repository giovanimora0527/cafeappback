/*
* Archivo: Nodo.java
* Fecha: 24/09/2018
* Todos los derechos de propiedad intelectual e industrial sobre esta
* aplicacion son de propiedad exclusiva del GRUPO ASESORIA EN
* SISTEMATIZACION DE DATOS SOCIEDAD POR ACCIONES SIMPLIFICADA GRUPO ASD
  S.A.S.
* Su uso, alteracion, reproduccion o modificacion sin la debida
* consentimiento por escrito de GRUPO ASD S.A.S.
* autorizacion por parte de su autor quedan totalmente prohibidos.
*
* Este programa se encuentra protegido por las disposiciones de la
* Ley 23 de 1982 y demas normas concordantes sobre derechos de autor y
* propiedad intelectual. Su uso no autorizado dara lugar a las sanciones
* previstas en la Ley.
*/
package co.com.grupoasd.fae.administracion.model;

import java.io.Serializable;

/**
 * Modelo que representa un nodo del servicio.
 * @author Juan Carlos Castellanos jccastellanos@grupoasd.com.co
 */
public class Nodo implements Serializable {
    /**
     * Version del API.
     */
    private String version;
    /**
     * Codigo de compilacion.
     */
    private String build;
    /**
     * Nombre del nodo.
     */
    private String nodo;
    /**
     * Identificador del servidor de base de datos (En mariadb es el server_id).
     */
    private String dbms;

    /**
     * Constructor de clase.
     */
    public Nodo() {
        
    }   
    
    /**
     * Constructor de clase.
     * @param version Version de los servicios.
     * @param build Codigo de compilacion.
     * @param nodo Nodo de peticion.
     * @param dbms Identificador del servidor de base de datos (En mariadb es el server_id).
     */
    public Nodo(final String version, final String build, final String nodo, 
            final String dbms) {
        this.version = version;
        this.build = build;
        this.nodo = nodo;
        this.dbms = dbms;
    }

    /**
     * Version del API.
     * @return String
     */
    public String getVersion() {
        return version;
    }

    /**
     * Version del API.
     * @param version String
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Codigo de compilacion.
     * @return String
     */
    public String getBuild() {
        return build;
    }

    /**
     * Codigo de compilacion.
     * @param build String
     */
    public void setBuild(final String build) {
        this.build = build;
    }        

    /**
     * Nombre del nodo.
     * @return String
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * Nombre del nodo.
     * @param nodo String
     */
    public void setNodo(final String nodo) {
        this.nodo = nodo;
    }

    /**
     * Identificador del servidor de base de datos (En mariadb es el server_id).
     * @return String
     */
    public String getDbms() {
        return dbms;
    }

    /**
     * Identificador del servidor de base de datos (En mariadb es el server_id).
     * @param dbms String
     */
    public void setDbms(final String dbms) {
        this.dbms = dbms;
    }
    
    
}
