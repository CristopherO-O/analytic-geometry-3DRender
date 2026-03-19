package src.gui.panels;

import javax.swing.*;
import java.awt.*;
import src.entities.Scene;
import src.gui.RenderCanvas;
import src.gui.dialogs.*;


public class AddEntityPanel extends JPanel {
    
    /**
     * Creates panel with add entity buttons.
     * @param scene scene to modify
     * @param canvas canvas to repaint
     * @param listPanel list panel to refresh
     */
    public AddEntityPanel(Scene scene, RenderCanvas canvas, SceneListPanel listPanel){
        
        setLayout(new GridLayout(3,1,10,10));
        setBackground(new Color(45,45,48));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JButton btnPoint = createButton("Novo Ponto");
        JButton btnLine = createButton("Nova Reta");
        JButton btnPlane = createButton("Novo Plano");
        JButton btnVector = createButton("Novo Vetor");

        btnPoint.addActionListener(e ->
        PointDialog.show(this, scene, canvas, listPanel)
        );

        btnLine.addActionListener(e ->
            LineDialog.show(this, scene, canvas, listPanel)
        );

        btnPlane.addActionListener(e ->
            PlaneDialog.show(this, scene, canvas, listPanel)
        );

        btnVector.addActionListener(e ->
            VectorDialog.show(this, scene, canvas, listPanel)
        );

        add(btnPoint);
        add(btnLine);
        add(btnPlane);
        add(btnVector);
    }

    /**
     * Creates a styled button.
     * @param text button text
     * @return configured JButton
     */
    private JButton createButton(String text){

        JButton b = new JButton(text);

        b.setFocusPainted(false);
        b.setBackground(new Color(70,70,75));
        b.setForeground(Color.WHITE);
        b.setBorder(BorderFactory.createEmptyBorder(8,15,8,15));

        return b;
    }
}
