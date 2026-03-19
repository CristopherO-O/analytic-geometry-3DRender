package src.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import src.core.Point3D;
import src.entities.Scene;
import src.gui.RenderCanvas;
import src.gui.panels.SceneListPanel;

/**
 * Dialog helper to add a new point to the scene.
 */
public class PointDialog {

    /**
     * Shows point input dialog and adds point to scene.
     * @param parent parent component
     * @param scene scene to update
     * @param canvas canvas to repaint
     * @param listPanel panel list to refresh
     */
    public static void show(Component parent, Scene scene,
                            RenderCanvas canvas,
                            SceneListPanel listPanel){

        JTextField x = new JTextField("0");
        JTextField y = new JTextField("0");
        JTextField z = new JTextField("0");

        Object[] fields = {"X:",x,"Y:",y,"Z:",z};

        int res = JOptionPane.showConfirmDialog(
                parent,
                fields,
                "Adicionar Ponto",
                JOptionPane.OK_CANCEL_OPTION
        );

        if(res == JOptionPane.OK_OPTION){

            scene.addPoint(new Point3D(
                    Double.parseDouble(x.getText()),
                    Double.parseDouble(y.getText()),
                    Double.parseDouble(z.getText())
            ));

            listPanel.refreshList();
            canvas.repaint();
        }
    }
}
