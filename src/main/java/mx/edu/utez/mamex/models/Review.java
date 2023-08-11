package mx.edu.utez.mamex.models;

public class Review {
    private long id_review;
    private String name_user;
    // Cambio de userId a usuario
    private int evaluacion; // Cambio de rating a evaluacion
    private String comentario;
    private long producto;

    public Review() {
    }


    public Review(long id_review, String name_user, int evaluacion, String comentario, long producto) {
        this.id_review = id_review;
        this.name_user = name_user;
        this.evaluacion = evaluacion;
        this.comentario = comentario;
        this.producto = producto;

    }

    public long getId_review() {
        return id_review;
    }

    public void setId_review(long id_review) {
        this.id_review = id_review;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(long usuario) {
        this.name_user = name_user;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getProducto() {
        return producto;
    }

    public void setProducto(long producto) {
        this.producto = producto;
    }
}
