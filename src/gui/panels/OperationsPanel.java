package src.gui.panels;

import javax.swing.*;
import java.awt.*;

public class OperationsPanel extends JPanel {

    // ==========================================================
    // >>>>> CONSTRUTOR
    // ==========================================================
    public OperationsPanel(){

        setBackground(new Color(45,45,48));

        JLabel label = new JLabel("Operações de GA em breve...");
        label.setForeground(Color.WHITE);

        add(label);
    }
}