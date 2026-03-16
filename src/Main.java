package src;

import src.core.Point3D;
import src.core.Vector3D;
import src.entities.Plane3D;
import src.entities.Scene;
import src.gui.MainFrame;
import src.camera.Camera;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. Inicialização da Câmera (Alvo na origem, raio de órbita 15).
        Camera cam = new Camera(new Point3D(0, 0, 0), 15.0);

        // 2. Configuração da Cena Inicial (Objetos padrão para GA).
        Scene scene = new Scene();
        
        // Plano de exemplo.
        scene.addPlane(new Plane3D(new Point3D(0, 0, 0), new Vector3D(0, 1, 0)));

        // 3. Lançamento da Interface Gráfica.
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(cam, scene);
            frame.setVisible(true);
        });
        
        System.out.println("GARender rodando... Use o mouse para orbitar e o scroll para zoom.");
    }
}