package controller;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.sound.sampled.*;
import javax.swing.JFileChooser;
import model.Cancion;
import model.ReproductorModel;
import view.ReproductorView;

public class ReproductorController {
    private ReproductorView view;
    private ReproductorModel model;
    private Clip clip;
    private int indiceActual = -1;
    private boolean pausado = false;
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
            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
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
        } else {
            clip.start();
            pausado = false;
            view.getPanelReproductor().getBtnPlay().setText("⏸");
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
    
}