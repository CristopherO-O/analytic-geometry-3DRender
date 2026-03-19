package src;

import src.core.Point3D;
import src.entities.Scene;
import src.gui.MainFrame;
import src.camera.Camera;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        
        Camera cam = new Camera(new Point3D(0, 0, 0), 15.0);

        
        Scene scene = new Scene();
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(cam, scene);
            frame.setVisible(true);
        });
        
        System.out.println("GARender rodando... Use o mouse para orbitar e o scroll para zoom.");
    }
}
