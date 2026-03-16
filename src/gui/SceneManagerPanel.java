package src.gui;

import javax.swing.*;
import java.awt.*;
import src.entities.Scene;
import src.gui.panels.*;

public class SceneManagerPanel extends JPanel {

    // ==========================================================
    // CONSTRUTOR.
    // ==========================================================
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
    }
}