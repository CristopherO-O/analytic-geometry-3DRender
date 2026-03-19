package src.gui.dialogs;

import javax.swing.*;
import java.awt.*;
import src.core.*;
import src.entities.*;
import src.gui.RenderCanvas;
import src.gui.panels.SceneListPanel;

/**
 * Dialog to create a plane entity in the scene.
 */
public class PlaneDialog {

    /**
     * Shows plane dialog and adds plane to scene.
     * @param parent parent component
     * @param scene scene to update
     * @param canvas canvas to repaint
     * @param listPanel list panel to refresh
     */
    public static void show(Component parent, Scene scene,
                            RenderCanvas canvas,
                            SceneListPanel listPanel){

        String[] options = {"Ponto + Vetor Normal","Três Pontos"};

        JComboBox<String> combo = new JComboBox<>(options);

        CardLayout layout = new CardLayout();
        JPanel card = new JPanel(layout);

        JPanel normalPanel = new JPanel(new GridLayout(2,6));

        JTextField px=new JTextField("0");
        JTextField py=new JTextField("0");
        JTextField pz=new JTextField("0");

        JTextField nx=new JTextField("0");
        JTextField ny=new JTextField("1");
        JTextField nz=new JTextField("0");

        normalPanel.add(new JLabel("Px")); normalPanel.add(px);
        normalPanel.add(new JLabel("Py")); normalPanel.add(py);
        normalPanel.add(new JLabel("Pz")); normalPanel.add(pz);

        normalPanel.add(new JLabel("Nx")); normalPanel.add(nx);
        normalPanel.add(new JLabel("Ny")); normalPanel.add(ny);
        normalPanel.add(new JLabel("Nz")); normalPanel.add(nz);

        JPanel pointsPanel=new JPanel(new GridLayout(3,6));

        JTextField ax=new JTextField("1");
        JTextField ay=new JTextField("0");
        JTextField az=new JTextField("0");

        JTextField bx=new JTextField("0");
        JTextField by=new JTextField("1");
        JTextField bz=new JTextField("0");

        JTextField cx=new JTextField("0");
        JTextField cy=new JTextField("0");
        JTextField cz=new JTextField("1");

        pointsPanel.add(new JLabel("Ax")); pointsPanel.add(ax);
        pointsPanel.add(new JLabel("Ay")); pointsPanel.add(ay);
        pointsPanel.add(new JLabel("Az")); pointsPanel.add(az);

        pointsPanel.add(new JLabel("Bx")); pointsPanel.add(bx);
        pointsPanel.add(new JLabel("By")); pointsPanel.add(by);
        pointsPanel.add(new JLabel("Bz")); pointsPanel.add(bz);

        pointsPanel.add(new JLabel("Cx")); pointsPanel.add(cx);
        pointsPanel.add(new JLabel("Cy")); pointsPanel.add(cy);
        pointsPanel.add(new JLabel("Cz")); pointsPanel.add(cz);

        card.add(normalPanel,"normal");
        card.add(pointsPanel,"points");

        combo.addActionListener(e -> {

            if(combo.getSelectedIndex()==0)
                layout.show(card,"normal");
            else
                layout.show(card,"points");
        });

        Object[] msg={"Modo de criação:",combo,card};

        int res = JOptionPane.showConfirmDialog(
                parent,
                msg,
                "Novo Plano",
                JOptionPane.OK_CANCEL_OPTION
        );

        if(res==JOptionPane.OK_OPTION){

            if(combo.getSelectedIndex()==0){

                scene.addPlane(new Plane3D(
                        new Point3D(
                                Double.parseDouble(px.getText()),
                                Double.parseDouble(py.getText()),
                                Double.parseDouble(pz.getText())
                        ),
                        new Vector3D(
                                Double.parseDouble(nx.getText()),
                                Double.parseDouble(ny.getText()),
                                Double.parseDouble(nz.getText())
                        )
                ));

            }else{

                scene.addPlane(new Plane3D(
                        new Point3D(
                                Double.parseDouble(ax.getText()),
                                Double.parseDouble(ay.getText()),
                                Double.parseDouble(az.getText())
                        ),
                        new Point3D(
                                Double.parseDouble(bx.getText()),
                                Double.parseDouble(by.getText()),
                                Double.parseDouble(bz.getText())
                        ),
                        new Point3D(
                                Double.parseDouble(cx.getText()),
                                Double.parseDouble(cy.getText()),
                                Double.parseDouble(cz.getText())
                        )
                ));
            }

            listPanel.refreshList();
            canvas.repaint();
        }
    }
}
