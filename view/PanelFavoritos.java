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
    public PanelFavoritos(){

        setLayout(new BorderLayout());

        favoritos = new ArrayList<>();
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        lista.setBackground(new Color(40, 40, 40));
        lista.setForeground(Color.WHITE);          
        lista.setSelectionBackground(new Color(30, 215, 96)); 
        lista.setSelectionForeground(Color.BLACK);

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