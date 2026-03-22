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

        view.getBtnInicio().addActionListener(e -> view.mostrarInicio());
        view.getBtnBiblioteca().addActionListener(e -> view.mostrarBiblioteca());
        view.getBtnFavoritos().addActionListener(e -> view.mostrarFavoritos());

        view.getPanelReproductor().getBtnImportar().addActionListener(e -> importarCarpeta());
        view.getPanelReproductor().getBtnPlay().addActionListener(e -> playPause());
        view.getPanelReproductor().getBtnNext().addActionListener(e -> siguiente());
        view.getPanelReproductor().getBtnPrev().addActionListener(e -> anterior());
        //FAVORITOS PARA BD
        view.getPanelReproductor().getBtnFavorito().addActionListener(e -> toggleFavorito());
        view.getPanelReproductor().getSliderVolumen().addChangeListener(e -> ajustarVolumen());

        view.getPanelInicio().getLista().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = view.getPanelInicio().getLista().getSelectedIndex();
                    if (index != -1) {
                        indiceActual = index;
                        reproducir(indiceActual);
                    }
                }
            }
        });
        view.getPanelBiblioteca().getLista().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Object seleccionado = view.getPanelBiblioteca().getLista().getSelectedValue();
                    if (seleccionado == null) return;
                    if (seleccionado instanceof Cancion) {
                        archivorepro((Cancion) seleccionado);
                    } else if (seleccionado instanceof String && !seleccionado.toString().contains("📁")) {
                        archivorepro(seleccionado.toString());
                    }
                }
            }
        });
        view.getPanelBiblioteca().getBtnVolver().addActionListener(e -> view.getPanelBiblioteca().mostrarCarpetas());        
        view.getPanelReproductor().getBtnAnadirAPlaylist().addActionListener(e -> {
            Cancion seleccionada = view.getPanelInicio().getLista().getSelectedValue();
            String playlistDestino = (String) view.getPanelReproductor().getComboPlaylists().getSelectedItem();
            if (seleccionada != null) {
                if (playlistDestino.equals("Playlist 1")) view.getPanelBiblioteca().agregarAPlaylist1(seleccionada);
                else if (playlistDestino.equals("Playlist 2")) view.getPanelBiblioteca().agregarAPlaylist2(seleccionada);
                else if (playlistDestino.equals("Playlist 3")) view.getPanelBiblioteca().agregarAPlaylist3(seleccionada);
                System.out.println(seleccionada.getTitulo() + " añadida a " + playlistDestino);
            } else {
                System.out.println("Selecciona una canción en la lista de Inicio primero.");
            }
        });
    }

    //PARA BD
    private void importarCarpeta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File carpeta = fileChooser.getSelectedFile();
            File[] archivos = carpeta.listFiles();
            if (archivos == null) return;
            for (File archivo : archivos) {
                if (archivo.getName().toLowerCase().endsWith(".wav")) {
                    String titulo = archivo.getName().substring(0, archivo.getName().lastIndexOf("."));
                    Cancion cancion = new Cancion(titulo, "Desconocido", archivo);
                    model.agregarCancion(cancion);
                    view.getPanelInicio().agregarCancion(cancion); // Aseguramos que se vea en inicio
                    view.getPanelReproductor().agregarCancion(cancion);
                }
            }
        }
    }
    private void reproducir(int index) {
        if (index < 0 || index >= model.getCanciones().size()) return;
        Cancion cancion = model.getCanciones().get(index);
        indiceActual = index;
        ejecutarAudio(cancion);
    }

    //REPRODUCIR
    private void archivorepro(Cancion cancion) {
        if (cancion == null) return;
        indiceActual = model.getCanciones().indexOf(cancion);
        ejecutarAudio(cancion);
    }

    //METODO PARA FAVORITOS
    private void archivorepro(String seleccionado) {
        String tituloLimpio = seleccionado.replace("❤️ ", "").trim();
        for (Cancion c : model.getCanciones()) {
            if (c.getTitulo().equalsIgnoreCase(tituloLimpio)) {
                archivorepro(c);
                return;
            }
        }
    }
    private void ejecutarAudio(Cancion cancion) {
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(cancion.getArchivo());
            clip = AudioSystem.getClip();
            clip.open(audio);
            ajustarVolumen();
            clip.start();
            view.getPanelReproductor().setNombreCancion(cancion.getTitulo());
            long duracionMicro = clip.getMicrosecondLength();
            int duracionSeg = (int)(duracionMicro / 1_000_000);
            view.getPanelReproductor().iniciarDuracion(duracionSeg);

            if(timerProgreso != null) timerProgreso.stop();

            timerProgreso = new Timer(500, e -> {
                long microActual = clip.getMicrosecondPosition();
                int segundosActual = (int)(microActual / 1_000_000);
                view.getPanelReproductor().actualizarProgreso(segundosActual);
            });
            timerProgreso.start();

            pausado = false;
            view.getPanelReproductor().getBtnPlay().setText("⏸");
            System.out.println("Reproduciendo: " + cancion.getTitulo());
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
            if (timerProgreso != null) {
                timerProgreso.stop();
            }
            System.out.println("Audio pausado");
        } else {
            clip.start();
            pausado = false;
            view.getPanelReproductor().getBtnPlay().setText("⏸");
            if (timerProgreso != null) {
                timerProgreso.start();
            }
            System.out.println("Audio reanudado");
        }
    }

    private void siguiente() {
        if (model.getCanciones().isEmpty()) return;
        indiceActual = (indiceActual + 1) % model.getCanciones().size();
        reproducir(indiceActual);
        view.getPanelReproductor().getLista().setSelectedIndex(indiceActual);
    }

    private void anterior() {
        if (model.getCanciones().isEmpty()) return;
        indiceActual = (indiceActual - 1 + model.getCanciones().size()) % model.getCanciones().size();
        reproducir(indiceActual);
        view.getPanelReproductor().getLista().setSelectedIndex(indiceActual);
    }
    //METODO QUE PODRIAMOS
    //IMPLEMENTARLE BD
    private void toggleFavorito() {
        int index = view.getPanelReproductor().getLista().getSelectedIndex();
        if(index < 0) index = view.getPanelInicio().getLista().getSelectedIndex();
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
        view.getPanelInicio().getLista().repaint();
    }

    private void ajustarVolumen() {
    if (clip == null) return;
    try {
        JSlider slider = view.getPanelReproductor().getSliderVolumen();
        float valorSlider = slider.getValue() / 100f;
         FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(valorSlider == 0 ? 0.0001 : valorSlider) / Math.log(10.0) * 20.0);
        if (dB < gainControl.getMinimum()) dB = gainControl.getMinimum();
        if (dB > gainControl.getMaximum()) dB = gainControl.getMaximum();
        gainControl.setValue(dB);
    } catch (Exception e) {
        System.err.println("Error al ajustar volumen: " + e.getMessage());
    }
}
}