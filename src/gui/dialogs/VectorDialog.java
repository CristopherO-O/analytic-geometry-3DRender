package src.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import src.core.Vector3D;
import src.entities.Scene;
import src.gui.RenderCanvas;
import src.gui.panels.SceneListPanel;

public class VectorDialog {

    public static void show(Component parent, Scene scene,
                            RenderCanvas canvas,
                            SceneListPanel listPanel) {

        JTextField x = new JTextField("1");
        JTextField y = new JTextField("0");
        JTextField z = new JTextField("0");

        Object[] fields = {
            "Digite as componentes do vetor:",
            "X:", x,
            "Y:", y,
            "Z:", z
        };

        int res = JOptionPane.showConfirmDialog(
                parent,
                fields,
                "Adicionar Vetor",
                JOptionPane.OK_CANCEL_OPTION
        );

        if(res == JOptionPane.OK_OPTION){
            scene.addVector(new Vector3D(
                    Double.parseDouble(x.getText()),
                    Double.parseDouble(y.getText()),
                    Double.parseDouble(z.getText())
            ));

            listPanel.refreshList();
            canvas.repaint();
        }
    }
}