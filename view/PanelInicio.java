package view;

import javax.swing.*;
import java.awt.*;
import model.Cancion;
import java.util.ArrayList;
import java.util.List;

public class PanelInicio extends JPanel {

    private DefaultListModel<Cancion> listModel;
    private JList<Cancion> lista;
    private List<Cancion> canciones;

    public PanelInicio(){

        setLayout(new BorderLayout());

        canciones = new ArrayList<>();
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        lista.setBackground(new Color(40, 40, 40)); 
        lista.setForeground(Color.WHITE);           
        lista.setSelectionBackground(new Color(30, 215, 96));
        lista.setSelectionForeground(Color.BLACK);

        add(new JScrollPane(lista), BorderLayout.CENTER);
    }

    public void agregarCancion(Cancion c){
        canciones.add(c);
        listModel.addElement(c);
    }

    public JList<Cancion> getLista(){
        return lista;
    }

    public List<Cancion> getCanciones(){
        return canciones;
    }
}