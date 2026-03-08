package view;

import javax.swing.*;
import java.awt.*;
import model.Cancion;
import java.util.List;
import java.util.ArrayList;

public class PanelFavoritos extends JPanel {

    private JList<Cancion> lista;
    private DefaultListModel<Cancion> listModel;
    private List<Cancion> favoritos;
//both interface java.util.list
    public PanelFavoritos(){

        setLayout(new BorderLayout());

        favoritos = new ArrayList<>();
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);

        add(new JScrollPane(lista), BorderLayout.CENTER);
    }

    public void agregarFavorito(Cancion c){
        listModel.addElement(c);
        favoritos.add(c);
    }

    public void quitarFavorito(Cancion c){
        listModel.removeElement(c);
        favoritos.remove(c);
    }

    public JList<Cancion> getLista(){
        return lista;
    }

    public List<Cancion> getFavoritos(){
        return favoritos;
    }
}