package koover.com.koover2021;

public class KooDetailModel {

    private String comentarioUsuario;
    private String detalleComentario;
    private int pos;

    public KooDetailModel(String comentarioUsuario, String detalleComentario, int pos) {
        this.comentarioUsuario = comentarioUsuario;
        this.detalleComentario = detalleComentario;
        this.pos = pos;
    }

    public String getComentarioUsuario() {
        return comentarioUsuario;
    }

    public String getDetalleComentario() {
        return detalleComentario;
    }

    public int getPos() {
        return pos;
    }
}
