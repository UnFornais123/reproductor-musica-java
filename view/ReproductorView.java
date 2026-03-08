package view;
import javax.swing.*;
import java.awt.*;
import model.Cancion;
public class ReproductorView extends JFrame {
    Color negroSpotify = new Color(18,18,18);
    Color grisOscuro = new Color(40,40,40);
    private JList<Cancion> lista;
    private DefaultListModel<Cancion> listModel;
    private PanelReproductor panelReproductor;
    private PanelFavoritos panelFavoritos;
    public ReproductorView() { setTitle("TecWave");
        setSize(900,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel menu = new JPanel(new GridLayout(5,1,10,10));
        menu.setPreferredSize(new Dimension(170,0));
        menu.setBackground(negroSpotify);
        menu.add(new JButton("🏠 Inicio"));
        menu.add(new JButton("📚 Biblioteca"));
        JButton btnFavoritos = new JButton("❤️ Favoritos");
        menu.add(btnFavoritos);
        add(menu, BorderLayout.WEST);
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(grisOscuro);
        listModel = new DefaultListModel<>();
        lista = new JList<>(listModel);
        lista.setBackground(grisOscuro);
        lista.setForeground(Color.WHITE);
        centro.add(new JScrollPane(lista),
                BorderLayout.CENTER); add(centro,
                BorderLayout.CENTER);
                panelReproductor = new PanelReproductor();
                add(panelReproductor, BorderLayout.SOUTH);
                panelFavoritos = new PanelFavoritos();
                btnFavoritos.addActionListener(e -> mostrarfav());
                
                setVisible(true);
    }
    public JList<Cancion> getLista(){
        return lista;
    }
    public PanelReproductor getPanelReproductor(){
        return panelReproductor;
    }
    public void agregarCancion(Cancion c) {
        listModel.addElement(c);
    }

    public PanelFavoritos getPanelFavoritos(){
        return panelFavoritos;
    }
    public void mostrarfav(){
    listModel.clear();
    for(Cancion c : panelFavoritos.getFavoritos()){
        listModel.addElement(c);
    }

}
    
    
}