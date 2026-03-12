package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.camera.Camera;
import src.entities.Scene;

public class MainFrame extends JFrame {
    private Point lastMousePos;

    // ==========================================================
    // >>>>> Construtor
    // ==========================================================
    public MainFrame(Camera cam, Scene scene) {
        setTitle("GARender - Geometria Analítica 3D (UFSJ)");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Inicialização dos componentes
        RenderCanvas canvas = new RenderCanvas(cam, scene);
        SceneManagerPanel manager = new SceneManagerPanel(scene, canvas);

        // 2. Organização do Layout com SplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, manager, canvas);
        splitPane.setDividerLocation(300);
        splitPane.setContinuousLayout(true);
        this.getContentPane().add(splitPane);

        // 3. Lógica de Órbita com o Mouse (Clique e Arraste)
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePos = e.getPoint(); // Salva onde o clique começou
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastMousePos != null) {
                    // Calcula o deslocamento do mouse desde o último movimento
                    int dx = e.getX() - lastMousePos.x;
                    int dy = e.getY() - lastMousePos.y;

                    // Sensibilidade para rotação (ajuste se estiver rápido demais)
                    double sensitivity = 0.005;

                    // Aplica a órbita na câmera
                    // DX rotaciona horizontalmente (Theta), DY rotaciona verticalmente (Phi)
                    cam.orbit(dx * sensitivity, dy * sensitivity);

                    lastMousePos = e.getPoint(); // Atualiza a posição de referência
                    canvas.repaint(); // Redesenha a cena imediatamente
                }
            }
        });

        // 4. Lógica de Zoom com o Mouse Wheel
        canvas.addMouseWheelListener(e -> {
            // e.getWheelRotation() é -1 para frente e 1 para trás
            // Multiplicamos por um fator para o zoom ser mais perceptível
            double zoomAmount = e.getWheelRotation() * 1.5;
            cam.zoom(zoomAmount);
            canvas.repaint();
        });

        // Garante que o canvas possa receber foco
        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
    }
}