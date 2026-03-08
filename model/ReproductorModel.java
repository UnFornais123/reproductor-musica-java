package model;

import java.util.ArrayList;
import java.util.List;

public class ReproductorModel {

    private List<Cancion> canciones;
    private List<Cancion> favoritos;

    public ReproductorModel() {
        canciones = new ArrayList<>();
        favoritos = new ArrayList<>();
    }

    public void agregarCancion(Cancion c) {
        canciones.add(c);
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void agregarFavorito(Cancion c) {
        if (!favoritos.contains(c)) {
            favoritos.add(c);
            c.setFavorito(true);
        }
    }

    public void quitarFavorito(Cancion c) {
        favoritos.remove(c);
        c.setFavorito(false);
    }

    public List<Cancion> getFavoritos() {
        return favoritos;
    }
}