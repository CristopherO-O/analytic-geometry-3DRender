package src.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import src.core.*;
import src.entities.*;
import src.gui.RenderCanvas;
import src.gui.panels.SceneListPanel;

/**
 * Dialog to create a 3D line from point(s) or point+direction.
 */
public class LineDialog {
  
    /**
     * Shows line creation dialog and adds line to scene.
     * @param parent parent component
     * @param scene scene to update
     * @param canvas canvas to repaint
     * @param listPanel list panel to refresh
     */
    public static void show(Component parent, Scene scene,
                            RenderCanvas canvas,
                            SceneListPanel listPanel){

        String[] options = {"Dois Pontos", "Ponto + Vetor Diretor"};

        JComboBox<String> combo = new JComboBox<>(options);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel panelPoints = new JPanel(new GridLayout(2,6));

        JTextField p1x = new JTextField("0");
        JTextField p1y = new JTextField("0");
        JTextField p1z = new JTextField("0");

        JTextField p2x = new JTextField("1");
        JTextField p2y = new JTextField("1");
        JTextField p2z = new JTextField("1");

        panelPoints.add(new JLabel("P1 X")); panelPoints.add(p1x);
        panelPoints.add(new JLabel("P1 Y")); panelPoints.add(p1y);
        panelPoints.add(new JLabel("P1 Z")); panelPoints.add(p1z);

        panelPoints.add(new JLabel("P2 X")); panelPoints.add(p2x);
        panelPoints.add(new JLabel("P2 Y")); panelPoints.add(p2y);
        panelPoints.add(new JLabel("P2 Z")); panelPoints.add(p2z);

        JPanel panelVector = new JPanel(new GridLayout(2,6));

        JTextField px = new JTextField("0");
        JTextField py = new JTextField("0");
        JTextField pz = new JTextField("0");

        JTextField vx = new JTextField("1");
        JTextField vy = new JTextField("0");
        JTextField vz = new JTextField("0");

        panelVector.add(new JLabel("Px")); panelVector.add(px);
        panelVector.add(new JLabel("Py")); panelVector.add(py);
        panelVector.add(new JLabel("Pz")); panelVector.add(pz);

        panelVector.add(new JLabel("Vx")); panelVector.add(vx);
        panelVector.add(new JLabel("Vy")); panelVector.add(vy);
        panelVector.add(new JLabel("Vz")); panelVector.add(vz);

        cardPanel.add(panelPoints,"points");
        cardPanel.add(panelVector,"vector");

        combo.addActionListener(e -> {

            if(combo.getSelectedIndex()==0)
                cardLayout.show(cardPanel,"points");
            else
                cardLayout.show(cardPanel,"vector");
        });

        Object[] message = {"Modo de criação:",combo,cardPanel};

        int res = JOptionPane.showConfirmDialog(
                parent,
                message,
                "Nova Reta",
                JOptionPane.OK_CANCEL_OPTION
        );

        if(res == JOptionPane.OK_OPTION){

            if(combo.getSelectedIndex()==0){

                scene.addLine(new Line3D(
                        new Point3D(
                                Double.parseDouble(p1x.getText()),
                                Double.parseDouble(p1y.getText()),
                                Double.parseDouble(p1z.getText())
                        ),
                        new Point3D(
                                Double.parseDouble(p2x.getText()),
                                Double.parseDouble(p2y.getText()),
                                Double.parseDouble(p2z.getText())
                        )
                ));

            }else{

                scene.addLine(new Line3D(
                        new Point3D(
                                Double.parseDouble(px.getText()),
                                Double.parseDouble(py.getText()),
                                Double.parseDouble(pz.getText())
                        ),
                        new Vector3D(
                                Double.parseDouble(vx.getText()),
                                Double.parseDouble(vy.getText()),
                                Double.parseDouble(vz.getText())
                        )
                ));
            }

            listPanel.refreshList();
            canvas.repaint();
        }
    }
}
