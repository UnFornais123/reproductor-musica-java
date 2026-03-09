package view;

import javax.swing.*;
import java.awt.*;
import model.Cancion;

public class ReproductorView extends JFrame {
    private Color negroSpotify = new Color(18, 18, 18);
    private Color grisOscuro = new Color(40, 40, 40);


    private PanelReproductor panelReproductor;
    private PanelInicio panelInicio;
    private PanelFavoritos panelFavoritos;
    private PanelBiblioteca panelBiblioteca;

    private JButton btnInicio;
    private JButton btnBiblioteca;
    private JButton btnFavoritos;

    private CardLayout cardLayout;
    private JPanel panelCentral;

    public ReproductorView() {
        setTitle("TecWave");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        JPanel menu = new JPanel(new GridLayout(6, 1, 10, 10));
        menu.setPreferredSize(new Dimension(200, 0));
        menu.setBackground(negroSpotify);
        menu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        btnInicio = crearBotonMenu("🏠 Inicio");
        btnBiblioteca = crearBotonMenu("📚 Biblioteca");
        btnFavoritos = crearBotonMenu("❤️ Favoritos");

        menu.add(btnInicio);
        menu.add(btnBiblioteca);
        menu.add(btnFavoritos);
        add(menu, BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(grisOscuro);
        panelInicio = new PanelInicio();
        panelBiblioteca = new PanelBiblioteca();
        panelFavoritos = new PanelFavoritos();

        panelCentral.add(panelInicio, "inicio");
        panelCentral.add(panelBiblioteca, "biblioteca");
        panelCentral.add(panelFavoritos, "favoritos");
        
        add(panelCentral, BorderLayout.CENTER);

        panelReproductor = new PanelReproductor();
        add(panelReproductor, BorderLayout.SOUTH);

        btnInicio.addActionListener(e -> mostrarInicio());
        btnBiblioteca.addActionListener(e -> mostrarBiblioteca());
        btnFavoritos.addActionListener(e -> mostrarFavoritos());
        setBackground(grisOscuro);
        setVisible(true);
    }
    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(grisOscuro);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return btn;
    }
    public void mostrarInicio() {
        cardLayout.show(panelCentral, "inicio");
    }
    public void mostrarBiblioteca() {
        cardLayout.show(panelCentral, "biblioteca");
    }
    public void mostrarFavoritos() {
        cardLayout.show(panelCentral, "favoritos");
    }
    public JButton getBtnInicio() { return btnInicio; }
    public JButton getBtnBiblioteca() { return btnBiblioteca; }
    public JButton getBtnFavoritos() { return btnFavoritos; }
    
    public PanelInicio getPanelInicio() { return panelInicio; }
    public PanelBiblioteca getPanelBiblioteca() { return panelBiblioteca; }
    public PanelFavoritos getPanelFavoritos() { return panelFavoritos; }
    public PanelReproductor getPanelReproductor() { return panelReproductor; }
}