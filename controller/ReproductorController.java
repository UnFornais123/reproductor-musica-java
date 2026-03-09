package controller;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import model.Cancion;
import model.ReproductorModel;
import view.ReproductorView;
import javax.swing.*;

public class ReproductorController {
    private ReproductorView view;
    private ReproductorModel model;
    private Clip clip;
    private int indiceActual = -1;
    private boolean pausado = false;
    private Timer timerProgreso;
    public ReproductorController(ReproductorView view, ReproductorModel model) {
        this.view = view;
        this.model = model;
        initController();
    }

    private void initController() {
        view.getPanelReproductor().getBtnImportar()
                .addActionListener(e -> importarCarpeta());
        view.getPanelReproductor().getLista()
                .addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            indiceActual = view.getPanelReproductor()
                                               .getLista()
                                               .getSelectedIndex();
                            reproducir(indiceActual);
                        }
                    }
                });
        view.getPanelReproductor().getBtnPlay().addActionListener(e -> playPause());
        view.getPanelReproductor().getBtnNext().addActionListener(e -> siguiente());
        view.getPanelReproductor().getBtnPrev().addActionListener(e -> anterior());
        view.getPanelReproductor().getBtnFavorito().addActionListener(e -> toggleFavorito());
        view.getPanelReproductor().getSliderVolumen().addChangeListener(e -> ajustarVolumen());

    }

    private void importarCarpeta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resultado = fileChooser.showOpenDialog(view);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpeta = fileChooser.getSelectedFile();
            File[] archivos = carpeta.listFiles();
            if (archivos == null) return;
            for (File archivo : archivos) {
                String nombre = archivo.getName().toLowerCase();
                if (nombre.endsWith(".wav")) {
                    String titulo = archivo.getName().substring(0, archivo.getName().lastIndexOf("."));
                    Cancion cancion =
                            new Cancion(titulo, "Desconocido", archivo);
                    model.agregarCancion(cancion);
                    view.getPanelReproductor().agregarCancion(cancion);
                }
            }
        }
    }
    private void reproducir(int index) {
        if (index < 0 || index >= model.getCanciones().size()) return;
        Cancion cancion = model.getCanciones().get(index);
        File archivo = cancion.getArchivo();
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audio);
            FloatControl controlVolumen =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();
            view.getPanelReproductor().setNombreCancion(cancion.getTitulo());

            // duración total
            long duracionMicro = clip.getMicrosecondLength();
            int duracionSeg = (int)(duracionMicro / 1_000_000);
            view.getPanelReproductor().iniciarDuracion(duracionSeg);

            // uso del tiempo real del audio
            if(timerProgreso != null){
                timerProgreso.stop();
            }

            timerProgreso = new Timer(500, e -> {

                long microActual = clip.getMicrosecondPosition();
                int segundosActual = (int)(microActual / 1_000_000);

                view.getPanelReproductor().actualizarProgreso(segundosActual);

            });

            timerProgreso.start();
            pausado = false;
            view.getPanelReproductor().getBtnPlay().setText("⏸");
            System.out.println("Reproduciendo: " + archivo.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playPause() {
        if (clip == null) return;
        if (!pausado) {
            clip.stop();
            pausado = true;
            view.getPanelReproductor().getBtnPlay().setText("▶");
            view.getPanelReproductor().pausarProgreso();
        } else {
            clip.start();
            pausado = false;
            view.getPanelReproductor().getBtnPlay().setText("⏸");
            view.getPanelReproductor().reanudarProgreso();
        }
    }

    private void siguiente() {
        if (model.getCanciones().isEmpty()) return;
        if (indiceActual == -1) {
            indiceActual = 0;
        } else {
            indiceActual++;
        }
        if (indiceActual >= model.getCanciones().size()) {
            indiceActual = 0;
        }
        reproducir(indiceActual);
        view.getPanelReproductor().getLista().setSelectedIndex(indiceActual);
    }

    private void anterior() {
        if (model.getCanciones().isEmpty()) return;
        indiceActual--;
        if (indiceActual < 0) {
            indiceActual = model.getCanciones().size() - 1;
        }
        reproducir(indiceActual);
        view.getPanelReproductor().getLista().setSelectedIndex(indiceActual);
    }

    private void toggleFavorito() {

        int index = view.getPanelReproductor().getLista().getSelectedIndex();
        if(index < 0) return;
        Cancion c = model.getCanciones().get(index);
        if(c.isFavorito()){
            model.quitarFavorito(c);
            view.getPanelFavoritos().quitarFavorito(c);
            } else {
            model.agregarFavorito(c);
            view.getPanelFavoritos().agregarFavorito(c);
        }
        view.getPanelReproductor().getLista().repaint();
    }

    private void ajustarVolumen() {

        if (clip == null) return;

        JSlider slider = view.getPanelReproductor().getSliderVolumen();

        int valor = slider.getValue(); // 0 - 100

        FloatControl control =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float min = control.getMinimum();
        float max = control.getMaximum();

        float volumen = min + (max - min) * (valor / 100f);

        control.setValue(volumen);
    }
}