package view;

import javax.swing.*;
import java.awt.*;
import model.Cancion;

public class PanelReproductor extends JPanel {
    private JButton btnPlay, btnNext, btnPrev, btnImportar, btnFavorito, btnAnadirAPlaylist;
    private JSlider barraProgreso, sliderVolumen;
    private JComboBox<String> comboPlaylists;
    private JLabel lblCancion, lblTiempoActual, lblTiempoTotal;
    private JList<Cancion> lista;
    private DefaultListModel<Cancion> listModel;
    private Color negroSpotify = new Color(18, 18, 18);
    private Color grisOscuro = new Color(40, 40, 40);
    public PanelReproductor() {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 18, 18));

        lblCancion = new JLabel("Ninguna canción");
        lblCancion.setForeground(negroSpotify);
        lblCancion.setFont(new Font("Arial", Font.BOLD, 14));
        lblCancion.setHorizontalAlignment(SwingConstants.CENTER);
        lblCancion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblCancion, BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        lista.setBackground(new Color(40, 40, 40));
        lista.setForeground(negroSpotify);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setBorder(BorderFactory.createLineBorder(new Color(18, 18, 18)));
        add(scrollLista, BorderLayout.CENTER);
        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBackground(new Color(18, 18, 18));
        lblTiempoActual = new JLabel("00:00");
        lblTiempoActual.setForeground(negroSpotify);
        lblTiempoTotal = new JLabel("00:00");
        lblTiempoTotal.setForeground(negroSpotify);
        barraProgreso = new JSlider(0, 100, 0);
        barraProgreso.setBackground(new Color(18, 18, 18));
        barraProgreso.setForeground(new Color(30, 215, 96));
        JPanel panelTiempo = new JPanel(new BorderLayout(10, 0));
        panelTiempo.setBackground(new Color(18, 18, 18));
        panelTiempo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelTiempo.add(lblTiempoActual, BorderLayout.WEST);
        panelTiempo.add(barraProgreso, BorderLayout.CENTER);
        panelTiempo.add(lblTiempoTotal, BorderLayout.EAST);
        panelControles.add(panelTiempo, BorderLayout.NORTH);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(new Color(18, 18, 18));
        btnPrev = new JButton("⏮");
        btnPlay = new JButton("▶");
        btnNext = new JButton("⏭");
        btnImportar = new JButton("⬆ Importar");
        btnFavorito = new JButton("❤️");
                comboPlaylists = new JComboBox<>(new String[]{"Playlist 1", "Playlist 2", "Playlist 3"});
        btnAnadirAPlaylist = new JButton("Añadir a...");
        panelBotones.add(btnPrev);
        panelBotones.add(btnPlay);
        panelBotones.add(btnNext);
        panelBotones.add(btnImportar);
        panelBotones.add(btnFavorito);
        panelBotones.add(comboPlaylists);
        panelBotones.add(btnAnadirAPlaylist);
        panelControles.add(panelBotones, BorderLayout.CENTER);
        JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelDerecha.setBackground(new Color(18, 18, 18));

        sliderVolumen = new JSlider(0, 100, 50);
        sliderVolumen.setPreferredSize(new Dimension(80, 20));
        sliderVolumen.setBackground(new Color(18, 18, 18));
        
        JLabel lblVol = new JLabel("🔊");
        lblVol.setForeground(Color.WHITE);
        panelDerecha.add(lblVol);
        panelDerecha.add(sliderVolumen);

        panelControles.add(panelDerecha, BorderLayout.EAST);

        add(panelControles, BorderLayout.SOUTH);
    }

    public void setNombreCancion(String nombre) {
        lblCancion.setText(nombre);
    }

    public void agregarCancion(Cancion c) {
        listModel.addElement(c);
    }

    public void iniciarDuracion(int duracionSegundos) {
        barraProgreso.setMaximum(duracionSegundos);
        barraProgreso.setValue(0);
        lblTiempoTotal.setText(formatearTiempo(duracionSegundos));
        lblTiempoActual.setText("00:00");
    }

    public void actualizarProgreso(int segundosActual) {
        barraProgreso.setValue(segundosActual);
        lblTiempoActual.setText(formatearTiempo(segundosActual));
    }

    private String formatearTiempo(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }

    public JButton getBtnPlay() { return btnPlay; }
    public JButton getBtnNext() { return btnNext; }
    public JButton getBtnPrev() { return btnPrev; }
    public JButton getBtnImportar() { return btnImportar; }
    public JButton getBtnFavorito() { return btnFavorito; }
    public JButton getBtnAnadirAPlaylist() { return btnAnadirAPlaylist; }
    public JComboBox<String> getComboPlaylists() { return comboPlaylists; }
    public JList<Cancion> getLista() { return lista; }
    public JSlider getBarraProgreso() { return barraProgreso; }
    public JSlider getSliderVolumen() { return sliderVolumen; }

    
}