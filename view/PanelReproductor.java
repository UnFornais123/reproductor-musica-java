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
    private JLabel lblTiempoActual;
    private JLabel lblTiempoTotal;
    private JList<Cancion> lista;
    private DefaultListModel<Cancion> listModel;
    private Timer timer;

    public PanelReproductor() {

        setLayout(new BorderLayout());
        setBackground(new Color(18,18,18));

        // Titulo canción
        lblCancion = new JLabel("Ninguna canción");
        lblCancion.setForeground(Color.WHITE);
        lblCancion.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblCancion, BorderLayout.NORTH);

        // Lista canciones
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        lista.setBackground(new Color(40,40,40));
        lista.setForeground(Color.WHITE);

        add(new JScrollPane(lista), BorderLayout.CENTER);

        // PANEL CONTROLES
        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBackground(new Color(18,18,18));

        // TIEMPOS
        lblTiempoActual = new JLabel("00:00");
        lblTiempoActual.setForeground(Color.WHITE);

        lblTiempoTotal = new JLabel("00:00");
        lblTiempoTotal.setForeground(Color.WHITE);

        barraProgreso = new JSlider();
        barraProgreso.setPreferredSize(new Dimension(300,20));
        barraProgreso.setBackground(new Color(18,18,18));
        barraProgreso.setForeground(new Color(30,215,96));

        JPanel panelTiempo = new JPanel(new BorderLayout());
        panelTiempo.setBackground(new Color(18,18,18));

        panelTiempo.add(lblTiempoActual, BorderLayout.WEST);
        panelTiempo.add(barraProgreso, BorderLayout.CENTER);
        panelTiempo.add(lblTiempoTotal, BorderLayout.EAST);

        panelControles.add(panelTiempo, BorderLayout.NORTH);

        // BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(18,18,18));

        btnPrev = new JButton("⏮");
        btnPlay = new JButton("▶");
        btnNext = new JButton("⏭");
        btnImportar = new JButton("⬆ Importar");
        btnFavorito = new JButton("❤️");

        panelBotones.add(btnPrev);
        panelBotones.add(btnPlay);
        panelBotones.add(btnNext);
        panelBotones.add(btnImportar);
        panelBotones.add(btnFavorito);

        panelControles.add(panelBotones, BorderLayout.CENTER);

        // VOLUMEN
        JPanel panelDerecha = new JPanel();
        panelDerecha.setBackground(new Color(18,18,18));

        sliderVolumen = new JSlider(0,100,50);
        sliderVolumen.setPreferredSize(new Dimension(100,20));
        sliderVolumen.setBackground(new Color(18,18,18));
        sliderVolumen.setForeground(new Color(30,215,96));

        JLabel lblVol = new JLabel("🔊");
        lblVol.setForeground(Color.WHITE);
        panelDerecha.add(lblVol);
        panelDerecha.add(sliderVolumen);

        panelControles.add(panelDerecha, BorderLayout.EAST);

        add(panelControles, BorderLayout.SOUTH);
    }

    public JButton getBtnPlay() { return btnPlay; }
    public JButton getBtnNext() { return btnNext; }
    public JButton getBtnPrev() { return btnPrev; }
    public JButton getBtnImportar() { return btnImportar; }
    public JButton getBtnFavorito(){ return btnFavorito; }

    public JSlider getBarraProgreso() { return barraProgreso; }
    public JSlider getSliderVolumen() { return sliderVolumen; }

    public JList<Cancion> getLista(){ return lista; }

    public void agregarCancion(Cancion c){
        listModel.addElement(c);
    }

    public void setNombreCancion(String nombre) {
        lblCancion.setText(nombre);
    }

    private String formatearTiempo(int segundos){

        int min = segundos / 60;
        int seg = segundos % 60;

        return String.format("%02d:%02d", min, seg);
    }

    public void iniciarDuracion(int duracionSegundos){

        barraProgreso.setMaximum(duracionSegundos);

        lblTiempoTotal.setText(formatearTiempo(duracionSegundos));

        barraProgreso.setValue(0);
        lblTiempoActual.setText("00:00");
    }

    public void pausarProgreso() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public void reanudarProgreso() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    public void actualizarProgreso(int segundosActual){

        barraProgreso.setValue(segundosActual);
        lblTiempoActual.setText(formatearTiempo(segundosActual));

    }
}