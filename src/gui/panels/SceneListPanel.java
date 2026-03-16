package src.gui.panels;

import javax.swing.*;
import java.awt.*;
import src.entities.Scene;
import src.gui.RenderCanvas;

public class SceneListPanel extends JPanel {

    private Scene scene;

    private DefaultListModel<String> listModel;
    private JList<String> entityList;

    // ==========================================================
    // >>>>> CONSTRUTOR
    // ==========================================================
    public SceneListPanel(Scene scene, RenderCanvas canvas){

        this.scene = scene;

        setLayout(new BorderLayout());
        setBackground(new Color(45,45,48));

        listModel = new DefaultListModel<>();
        entityList = new JList<>(listModel);

        entityList.setBackground(new Color(37,37,38));
        entityList.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(entityList);

        JButton btnDelete = new JButton("Remover");

        btnDelete.addActionListener(e -> {

            int idx = entityList.getSelectedIndex();

            if(idx != -1){

                scene.removeEntityAt(idx);
                refreshList();
                canvas.repaint();
            }
        });

        add(scroll, BorderLayout.CENTER);
        add(btnDelete, BorderLayout.SOUTH);

        refreshList();
    }

    // ==========================================================
    // >>>>> ATUALIZA A LISTA DE ENTIDADES
    // ==========================================================
    public void refreshList(){

        listModel.clear();

        for(String name : scene.getEntityNames())
            listModel.addElement(name);
    }
}