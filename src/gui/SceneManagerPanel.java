package src.gui;

import javax.swing.*;
import java.awt.*;
import src.core.Point3D;
import src.core.Vector3D;
import src.entities.Line3D;
import src.entities.Plane3D;
import src.entities.Scene;

public class SceneManagerPanel extends JPanel {
    private Scene scene;
    private RenderCanvas canvas;
    private DefaultListModel<String> listModel;
    private JList<String> entityList;

    // ==========================================================
    // >>>>> Construtor
    // ==========================================================
    public SceneManagerPanel(Scene scene, RenderCanvas canvas) {
        this.scene = scene;
        this.canvas = canvas;
        this.listModel = new DefaultListModel<>();
        this.entityList = new JList<>(listModel);
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Controle da Cena"));

        JTabbedPane tabs = new JTabbedPane();

        // 1. Aba: Adicionar
        tabs.addTab("Adicionar", createAddPanel());
        
        // 2. Aba: Operações (Reservada para cálculos de GA)
        tabs.addTab("Operações", createOperationsPanel());
        
        // 3. Aba: Lista de Objetos
        tabs.addTab("Cena", createListPanel());

        add(tabs, BorderLayout.CENTER);
        
        // Sincroniza a lista com o que já foi criado na Main.java
        refreshList();
    }

    // ==========================================================
    // >>>>> Atualiza a lista de entidades na aba "Cena" com os nomes atuais da Scene
    // ==========================================================
    private void refreshList() {
        listModel.clear();
        for (String name : scene.getEntityNames()) {
            listModel.addElement(name);
        }
    }

    // ==========================================================
    // >>>>> Criação dos painéis para cada aba
    // ==========================================================
    private JPanel createAddPanel() {
        JPanel p = new JPanel(new GridLayout(4, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnPoint = new JButton("Novo Ponto");
        btnPoint.addActionListener(e -> showPointDialog());

        JButton btnLine = new JButton("Nova Reta");
        btnLine.addActionListener(e -> showLineDialog());

        JButton btnPlane = new JButton("Novo Plano");
        btnPlane.addActionListener(e -> showPlaneDialog());

        p.add(btnPoint);
        p.add(btnLine);
        p.add(btnPlane);
        return p;
    }

    // ==========================================================
    // >>>>> Painel reservado para operações de GA (interseção, distância, etc) - por enquanto só um placeholder
    // ==========================================================
    private JPanel createOperationsPanel() {
        JPanel p = new JPanel();
        p.add(new JLabel("Área para cálculos de GA em breve..."));
        return p;
    }

    // ==========================================================
    // >>>>> Painel para listar os objetos da cena e permitir remoção
    // ==========================================================
    private JPanel createListPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JScrollPane(entityList), BorderLayout.CENTER);

        JButton btnDelete = new JButton("Remover Selecionado");
        btnDelete.addActionListener(e -> {
            int idx = entityList.getSelectedIndex();
            if (idx != -1) {
                scene.removeEntityAt(idx);
                refreshList();
                canvas.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item na lista para remover.");
            }
        });
        p.add(btnDelete, BorderLayout.SOUTH);
        return p;
    }

    // ==========================================================
    // >>>>> Diálogos para adicionar novos objetos à cena
    // ==========================================================
    private void showPointDialog() {
        JTextField x = new JTextField("0"), y = new JTextField("0"), z = new JTextField("0");
        Object[] fields = {"X:", x, "Y:", y, "Z:", z};
        
        int res = JOptionPane.showConfirmDialog(this, fields, "Adicionar Ponto", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                scene.addPoint(new Point3D(
                    Double.parseDouble(x.getText()), 
                    Double.parseDouble(y.getText()), 
                    Double.parseDouble(z.getText())));
                refreshList();
                canvas.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Insira apenas números.");
            }
        }
    }

    // ==========================================================
    // >>>>> Diálogo para adicionar uma nova reta à cena
    // ==========================================================
    private void showLineDialog() {
        JTextField p1x = new JTextField("0"), p1y = new JTextField("0"), p1z = new JTextField("0");
        JTextField p2x = new JTextField("1"), p2y = new JTextField("1"), p2z = new JTextField("1");
        Object[] fields = { "P1 X:", p1x, "P1 Y:", p1y, "P1 Z:", p1z, "P2 X:", p2x, "P2 Y:", p2y, "P2 Z:", p2z };

        int res = JOptionPane.showConfirmDialog(this, fields, "Adicionar Reta", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                scene.addLine(new Line3D(
                    new Point3D(Double.parseDouble(p1x.getText()), Double.parseDouble(p1y.getText()), Double.parseDouble(p1z.getText())),
                    new Point3D(Double.parseDouble(p2x.getText()), Double.parseDouble(p2y.getText()), Double.parseDouble(p2z.getText()))
                ));
                refreshList();
                canvas.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Insira apenas números.");
            }
        }
    }

    // ==========================================================
    // >>>>> Diálogo para adicionar um novo plano à cena
    // ==========================================================
    private void showPlaneDialog() {
        JTextField px = new JTextField("0"), py = new JTextField("0"), pz = new JTextField("0");
        JTextField nx = new JTextField("0"), ny = new JTextField("1"), nz = new JTextField("0");
        Object[] fields = { "Ponto X:", px, "Ponto Y:", py, "Ponto Z:", pz, "Normal X:", nx, "Normal Y:", ny, "Normal Z:", nz };

        int res = JOptionPane.showConfirmDialog(this, fields, "Adicionar Plano", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                scene.addPlane(new Plane3D(
                    new Point3D(Double.parseDouble(px.getText()), Double.parseDouble(py.getText()), Double.parseDouble(pz.getText())),
                    new Vector3D(Double.parseDouble(nx.getText()), Double.parseDouble(ny.getText()), Double.parseDouble(nz.getText()))
                ));
                refreshList();
                canvas.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Insira apenas números.");
            }
        }
    }
}