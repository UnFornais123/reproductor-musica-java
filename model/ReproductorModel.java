package model;

import java.util.ArrayList;
import java.util.List;

public class ReproductorModel {

    private List<Cancion> canciones;
    private List<Cancion> favoritos;
    private List<Cancion> playlist1;
    private List<Cancion> playlist2;
    private List<Cancion> playlist3;

    public ReproductorModel() {
        canciones = new ArrayList<>();
        favoritos = new ArrayList<>();
        playlist1 = new ArrayList<>();
        playlist2 = new ArrayList<>();
        playlist3 = new ArrayList<>();
    }
    public void agregarCancion(Cancion c) {
        if (!canciones.contains(c)) {
            canciones.add(c);
        }
    }
    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void agregarFavorito(Cancion c) {
        c.setFavorito(true);
        if (!favoritos.contains(c)) {
            favoritos.add(c);
        }
    }

    public void quitarFavorito(Cancion c) {
        c.setFavorito(false);
        favoritos.remove(c);
    }

    public List<Cancion> getFavoritos() {
        return favoritos;
    }

    public void agregarAPlaylist1(Cancion c) {
        if (!playlist1.contains(c)) playlist1.add(c);
    }

    public void agregarAPlaylist2(Cancion c) {
        if (!playlist2.contains(c)) playlist2.add(c);
    }

    public void agregarAPlaylist3(Cancion c) {
        if (!playlist3.contains(c)) playlist3.add(c);
    }

    public List<Cancion> getPlaylist1() {
        return playlist1;
    }

    public List<Cancion> getPlaylist2() {
        return playlist2;
    }

    public List<Cancion> getPlaylist3() {
        return playlist3;
    }
}