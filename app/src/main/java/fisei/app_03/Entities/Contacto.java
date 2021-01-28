package fisei.app_03.Entities;

public class Contacto {
    private int codigo;
    private String nombre;
    private String apellido;
    private String teefono;
    private String correo;

    public Contacto() {
    }

    public Contacto(int codigo, String nombre, String apellido, String teefono, String correo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.teefono = teefono;
        this.correo = correo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTeefono() {
        return teefono;
    }

    public void setTeefono(String teefono) {
        this.teefono = teefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
