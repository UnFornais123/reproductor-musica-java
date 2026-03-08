package view;
import javax.swing.*;
import java.awt.*;
import model.Cancion;
public class PanelReproductor extends JPanel {
    private JButton btnPlay;
    private JButton btnNext;
    private JButton btnPrev;
    private JButton btnImportar;
    private JButton btnFavorito;
    private JSlider barraProgreso;
    private JSlider sliderVolumen;
    private JLabel lblCancion;
    private JList<Cancion> lista;
    private DefaultListModel<Cancion> listModel;

    public PanelReproductor() {
        setLayout(new BorderLayout());
        setBackground(new Color(18,18,18));
        lblCancion = new JLabel("Ninguna canción");
        lblCancion.setForeground(Color.WHITE);
        add(lblCancion, BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        lista.setBackground(new Color(40,40,40));
        lista.setForeground(Color.WHITE);
        add(new JScrollPane(lista), BorderLayout.CENTER);
        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBackground(new Color(18,18,18));
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(18,18,18));

        btnPrev = new JButton("⏮");
        btnPlay = new JButton("▶");
        btnNext = new JButton("⏭");
        btnImportar = new JButton("⬆ Importar");

        btnFavorito = new JButton("❤️");
        panelBotones.add(btnFavorito);

        panelBotones.add(btnPrev);
        panelBotones.add(btnPlay);
        panelBotones.add(btnNext);
        panelBotones.add(btnImportar);
        panelBotones.add(btnFavorito);

        panelControles.add(panelBotones, BorderLayout.CENTER);

        barraProgreso = new JSlider();
        panelControles.add(barraProgreso, BorderLayout.NORTH);

        JPanel panelDerecha = new JPanel();
        panelDerecha.setBackground(new Color(18,18,18));

        sliderVolumen = new JSlider(0,100,50);
        sliderVolumen.setPreferredSize(new Dimension(100,20));

        panelDerecha.add(new JLabel("🔊"));
        panelDerecha.add(sliderVolumen);

        panelControles.add(panelDerecha, BorderLayout.EAST);

        add(panelControles, BorderLayout.SOUTH);
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }

    public JButton getBtnNext() {
        return btnNext;
    }

    public JButton getBtnPrev() {
        return btnPrev;
    }

    public JButton getBtnImportar() {
        return btnImportar;
    }

    public JButton getBtnFavorito(){ return btnFavorito; }

    public JSlider getBarraProgreso() {
        return barraProgreso;
    }

    public JSlider getSliderVolumen() {
        return sliderVolumen;
    }

    public JList<Cancion> getLista(){
        return lista;
    }

    public void agregarCancion(Cancion c){
        listModel.addElement(c);
    }

    public void setNombreCancion(String nombre) {
        lblCancion.setText(nombre);
    }
}