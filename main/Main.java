package main;
import controller.ReproductorController;
import model.ReproductorModel;
import view.ReproductorView;
public class Main {
    public static void main(String[] args) {

        ReproductorView view = new ReproductorView();
        ReproductorModel model = new ReproductorModel();
        new ReproductorController(view, model);
    }
}