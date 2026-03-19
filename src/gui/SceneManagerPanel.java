package src.gui;

import javax.swing.*;
import java.awt.*;
import src.entities.Scene;
import src.gui.panels.*;

/**
 * Scene manager panel with tabs to add entities and run operations.
 */
public class SceneManagerPanel extends JPanel {

    /**
     * Creates the scene manager with entity add and operation tabs.
     * @param scene scene containing entities
     * @param canvas render canvas to repaint
     */
    public SceneManagerPanel(Scene scene, RenderCanvas canvas){

        setLayout(new BorderLayout());
        setBackground(new Color(45,45,48));

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80,80,85)),
                "Cena"
        ));

        SceneListPanel listPanel = new SceneListPanel(scene, canvas);
        AddEntityPanel addPanel = new AddEntityPanel(scene, canvas, listPanel);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Adicionar", addPanel);
        tabs.addTab("Operações", new OperationsPanel(scene, canvas));
        tabs.addTab("Cena", listPanel);

        add(tabs, BorderLayout.CENTER);

        JButton btnToggleProj = new JButton("Modo: Perspectiva");
        btnToggleProj.setFocusPainted(false);
        btnToggleProj.setBackground(new Color(80, 140, 255)); 
        btnToggleProj.setForeground(Color.WHITE);
        btnToggleProj.setFont(new Font("Segoe UI", Font.BOLD, 13));
        
        btnToggleProj.addActionListener(e -> {
            canvas.getCamera().toggleProjection();
            
            
            if (canvas.getCamera().isPerspective()) {
                btnToggleProj.setText("Modo: Perspectiva");
                btnToggleProj.setBackground(new Color(80, 140, 255));
            } else {
                btnToggleProj.setText("Modo: Ortogonal");
                btnToggleProj.setBackground(new Color(220, 80, 80)); 
            }
            
            canvas.repaint(); 
        });

        
        add(btnToggleProj, BorderLayout.SOUTH);
    }
}
