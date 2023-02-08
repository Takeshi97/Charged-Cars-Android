package electric.cars.chargedcars.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Sitios_recarga {

    private String nombre_punto, direccion_punto, telefono_punto, disponibilidad_punto, grupo_punto, uriImage;
    private Integer id_Logo;
    private String idUsario;   // Se usara para obtener el usuario de la bd
    private Double longitud,latitud;
    private Boolean marcaExclusiva;
    private Boolean disponibilidad_formulario;
    private Boolean ratingBar;

    public Sitios_recarga(String nombre_punto, String direccion_punto, String telefono_punto, String disponibilidad_punto, String grupo_punto, Integer id_Logo) {
        this.nombre_punto = nombre_punto;
        this.direccion_punto = direccion_punto;
        this.telefono_punto = telefono_punto;
        this.disponibilidad_punto = disponibilidad_punto;
        this.grupo_punto = grupo_punto;
        this.id_Logo = id_Logo;
    }



    public Sitios_recarga(String nombre_punto, String direccion_punto, String telefono_punto, String disponibilidad_punto, String grupo_punto, Integer id_Logo, String idUsario, Boolean disponibilidad_formulario) {
        this.nombre_punto = nombre_punto;
        this.direccion_punto = direccion_punto;
        this.telefono_punto = telefono_punto;
        this.disponibilidad_punto = disponibilidad_punto;
        this.grupo_punto = grupo_punto;
        this.id_Logo = id_Logo;
        this.idUsario = idUsario;
        this.disponibilidad_formulario = disponibilidad_formulario;
    }


    public Sitios_recarga() {

    }


    public Sitios_recarga(String nombre_punto, String grupo_punto , String direccion_punto, String telefono_punto, String uriImage, String idUsario, Double longitud, Double latitud) {
        this.nombre_punto = nombre_punto;
        this.direccion_punto = direccion_punto;
        this.telefono_punto = telefono_punto;
        this.grupo_punto = grupo_punto;
        this.uriImage = uriImage;
        this.idUsario = idUsario;
        this.longitud = longitud;
        this.latitud = latitud;

    }



    public Sitios_recarga(String nombre_punto, String grupo_punto , String direccion_punto, String telefono_punto, Integer id_Logo, String idUsario) {
        this.nombre_punto = nombre_punto;
        this.direccion_punto = direccion_punto;
        this.telefono_punto = telefono_punto;
        this.grupo_punto = grupo_punto;
        this.id_Logo = id_Logo;
        this.idUsario = idUsario;
    }


    public String getNombre_punto() {
        return nombre_punto;
    }

    public void setNombre_punto(String nombre_punto) {
        this.nombre_punto = nombre_punto;
    }

    public String getDireccion_punto() {
        return direccion_punto;
    }

    public void setDireccion_punto(String direccion_punto) {
        this.direccion_punto = direccion_punto;
    }

    public String getTelefono_punto() {
        return telefono_punto;
    }

    public void setTelefono_punto(String telefono_punto) {
        this.telefono_punto = telefono_punto;
    }

    public String getDisponibilidad_punto() {
        return disponibilidad_punto;
    }

    public void setDisponibilidad_punto(String disponibilidad_punto) {
        this.disponibilidad_punto = disponibilidad_punto;
    }

    public String getGrupo_punto() {
        return grupo_punto;
    }

    public void setGrupo_punto(String grupo_punto) {
        this.grupo_punto = grupo_punto;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public Integer getId_Logo() {
        return id_Logo;
    }

    public void setId_Logo(Integer id_Logo) {
        this.id_Logo = id_Logo;
    }

    public String getIdUsario() {
        return idUsario;
    }

    public void setIdUsario(String idUsario) {
        this.idUsario = idUsario;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Boolean getMarcaExclusiva() {
        return marcaExclusiva;
    }

    public void setMarcaExclusiva(Boolean marcaExclusiva) {
        this.marcaExclusiva = marcaExclusiva;
    }

    public Boolean getDisponibilidad_formulario() {
        return disponibilidad_formulario;
    }

    public void setDisponibilidad_formulario(Boolean disponibilidad_formulario) {
        this.disponibilidad_formulario = disponibilidad_formulario;
    }

    public Boolean getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(Boolean ratingBar) {
        this.ratingBar = ratingBar;
    }


    @Override
    public String toString() {
        return "Sitios_recarga{" +
                "nombre_punto='" + nombre_punto + '\'' +
                ", direccion_punto='" + direccion_punto + '\'' +
                ", telefono_punto='" + telefono_punto + '\'' +
                ", disponibilidad_punto='" + disponibilidad_punto + '\'' +
                ", grupo_punto='" + grupo_punto + '\'' +
                ", uriImage='" + uriImage + '\'' +
                ", id_Logo=" + id_Logo +
                ", idUsario='" + idUsario + '\'' +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                ", marcaExclusiva=" + marcaExclusiva +
                ", disponibilidad_formulario=" + disponibilidad_formulario +
                ", ratingBar=" + ratingBar +
                '}';
    }
}
