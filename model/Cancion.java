package model;
import java.io.File;
public class Cancion {

    private String titulo;
    private String artista;
    private File archivo;
    private boolean favorito;

    public Cancion(String titulo, String artista, File archivo) {
        this.titulo = titulo;
        this.artista = artista;
        this.archivo = archivo;
        this.favorito = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public File getArchivo() {
        return archivo;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        if(favorito){
            return "❤️ " + titulo;
        }
        return titulo;
    }
}