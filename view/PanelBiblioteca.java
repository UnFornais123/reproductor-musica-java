package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Cancion;
import java.util.ArrayList;
import java.util.List;

public class PanelBiblioteca extends JPanel {

    private List<Cancion> playlist1 = new ArrayList<>();
    private List<Cancion> playlist2 = new ArrayList<>();
    private List<Cancion> playlist3 = new ArrayList<>();
    private JButton btnVolver;
    private JList<Object> lista;
    private DefaultListModel<Object> listModel;

    public PanelBiblioteca() {
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        btnVolver = new JButton("⬅ Volver");
        add(btnVolver, BorderLayout.NORTH);
        mostrarCarpetas();

        lista = new JList<>(listModel);
lista.setBackground(new Color(40, 40, 40)); 
lista.setForeground(Color.WHITE);         
lista.setSelectionBackground(new Color(30, 215, 96)); 
lista.setSelectionForeground(Color.BLACK);
        lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Object seleccionado = lista.getSelectedValue();
                    if (seleccionado == null) {
                        return;
                    }
                    String itemStr = seleccionado.toString();

                    if (itemStr.contains("📁")) {
                        listModel.clear();
                        if (itemStr.contains("1")) {
                            for (Cancion c : playlist1) {
                                listModel.addElement(c);
                            }
                        } else if (itemStr.contains("2")) {
                            for (Cancion c : playlist2) {
                                listModel.addElement(c);
                            }
                        } else if (itemStr.contains("3")) {
                            for (Cancion c : playlist3) {
                                listModel.addElement(c);
                            }
                        }
                    }
                }
            }
        });

        add(new JScrollPane(lista), BorderLayout.CENTER);
    }

    public void mostrarCarpetas() {
        listModel.clear();
        listModel.addElement("📁 Playlist1");
        listModel.addElement("📁 Playlist2");
        listModel.addElement("📁 Playlist3");
    }

    public void agregarAPlaylist1(Cancion c) {
        playlist1.add(c);
    }

    public void agregarAPlaylist2(Cancion c) {
        playlist2.add(c);
    }

    public void agregarAPlaylist3(Cancion c) {
        playlist3.add(c);
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public JList<Object> getLista() {
        return lista;
    }
}
