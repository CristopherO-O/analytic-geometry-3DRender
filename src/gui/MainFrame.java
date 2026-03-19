package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.camera.Camera;
import src.entities.Scene;

/**
 * Main application window.
 * Contains the 3D render canvas and scene manager panel.
 * Handles camera orbit and zoom interactions.
 */
public class MainFrame extends JFrame {

    private Point lastMousePos;

    /**
     * Creates the main frame with camera and scene.
     * @param cam camera used for projection and controls
     * @param scene scene containing entities to draw
     */
    public MainFrame(Camera cam, Scene scene) {

        
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("TabbedPane.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("List.font", new Font("Segoe UI", Font.PLAIN, 13));

        setTitle("GARender - Geometria Analítica 3D (UFSJ)");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        RenderCanvas canvas = new RenderCanvas(cam, scene);
        SceneManagerPanel manager = new SceneManagerPanel(scene, canvas);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, manager, canvas);
        splitPane.setDividerLocation(300);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(6);
        splitPane.setBorder(null);

        getContentPane().add(splitPane);

    
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePos = e.getPoint();
            }
        });

    
        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (lastMousePos != null) {

                    int dx = e.getX() - lastMousePos.x;
                    int dy = e.getY() - lastMousePos.y;

                    double sensitivity = 0.005;

                    cam.orbit(dx * sensitivity, dy * sensitivity);

                    lastMousePos = e.getPoint();

                    canvas.repaint();
                }
            }
        });

        canvas.addMouseWheelListener(e -> {

            double zoomAmount = e.getWheelRotation() * 1.5;

            cam.zoom(zoomAmount);

            canvas.repaint();
        });

        canvas.setFocusable(true);
        canvas.requestFocusInWindow();
    }
}
