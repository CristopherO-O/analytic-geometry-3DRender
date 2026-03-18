package src.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import src.entities.Scene;
import src.entities.Line3D;
import src.entities.Plane3D;
import src.core.Point3D;
import src.core.Vector3D;
import src.gui.RenderCanvas;

public class SceneListPanel extends JPanel {

    private Scene scene;
    private RenderCanvas canvas;
    private DefaultListModel<String> listModel;
    private JList<String> entityList;

    public SceneListPanel(Scene scene, RenderCanvas canvas){
        this.scene = scene;
        this.canvas = canvas;

        setLayout(new BorderLayout());
        setBackground(new Color(45,45,48));

        listModel = new DefaultListModel<>();
        entityList = new JList<>(listModel);

        entityList.setBackground(new Color(37,37,38));
        entityList.setForeground(Color.WHITE);
        
        entityList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                canvas.setSelectedIndices(entityList.getSelectedIndices());
            }
        });

        entityList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int index = entityList.locationToIndex(e.getPoint());
                
                // Limpa a seleção se clicar no vazio.
                if (index != -1) {
                    Rectangle cellBounds = entityList.getCellBounds(index, index);
                    if (cellBounds != null && !cellBounds.contains(e.getPoint())) {
                        entityList.clearSelection();
                        return; // Sai se clicou no vazio.
                    }
                }

                // LÓGICA DO BOTÃO DIREITO (MENU DE CONTEXTO).
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Se clicou com o direito num item que não estava selecionado, seleciona só ele.
                    if (index != -1 && !entityList.isSelectedIndex(index)) {
                        entityList.setSelectedIndex(index);
                    }
                    showContextMenu(e.getX(), e.getY());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(entityList);

        JButton btnDelete = new JButton("Remover Selecionados");
        btnDelete.addActionListener(e -> {
            int[] indices = entityList.getSelectedIndices();
            if(indices.length > 0){
                for (int i = indices.length - 1; i >= 0; i--) {
                    scene.removeEntityAt(indices[i]);
                }
                canvas.setSelectedIndices(new int[0]); 
                refreshList();
                canvas.repaint();
            }
        });

        add(scroll, BorderLayout.CENTER);
        add(btnDelete, BorderLayout.SOUTH);

        refreshList();
    }

    public void refreshList(){
        listModel.clear();
        for(String name : scene.getEntityNames())
            listModel.addElement(name);
    }

    // ==========================================================
    // MÉTODO AUXILIAR PARA PEGAR O OBJETO PELO ÍNDICE.
    // ==========================================================
    private Object getEntityAt(int index) {
        int p = scene.getPoints().size();
        int l = scene.getLines().size();
        int pl = scene.getPlanes().size();

        if (index < p) return scene.getPoints().get(index);
        if (index < p + l) return scene.getLines().get(index - p);
        if (index < p + l + pl) return scene.getPlanes().get(index - p - l);
        return scene.getVectors().get(index - p - l - pl);
    }

    // ==========================================================
    // GERA O MENU DE CONTEXTO DINÂMICO.
    // ==========================================================
    // ==========================================================
    // GERA O MENU DE CONTEXTO DINÂMICO.
    // ==========================================================
    private void showContextMenu(int x, int y) {
        int[] indices = entityList.getSelectedIndices();
        JPopupMenu popup = new JPopupMenu();

        // >>> OPERAÇÕES PARA TRÊS ENTIDADES SELECIONADAS <<<
        if (indices.length == 3) {
            Object o1 = getEntityAt(indices[0]);
            Object o2 = getEntityAt(indices[1]);
            Object o3 = getEntityAt(indices[2]);

            // 3 PONTOS
            if (o1 instanceof Point3D && o2 instanceof Point3D && o3 instanceof Point3D) {
                Point3D p1 = (Point3D) o1; Point3D p2 = (Point3D) o2; Point3D p3 = (Point3D) o3;
                
                addMenuItem(popup, "Criar Plano (Usando 3 Pontos)", () -> {
                    try {
                        scene.addPlane(new Plane3D(p1, p2, p3));
                        refreshList(); canvas.repaint();
                        showMessage("Plano criado com sucesso a partir dos pontos!");
                    } catch (Exception ex) {
                        showMessage("Erro ao criar Plano: " + ex.getMessage());
                    }
                });
            } else {
                JMenuItem empty = new JMenuItem("Nenhuma operação para essa combinação de 3 itens");
                empty.setEnabled(false);
                popup.add(empty);
            }
        }
        
        // >>> OPERAÇÕES PARA DUAS ENTIDADES SELECIONADAS <<<
        else if (indices.length == 2) {
            Object o1 = getEntityAt(indices[0]);
            Object o2 = getEntityAt(indices[1]);

            // 1. PONTO & PONTO.
            if (o1 instanceof Point3D && o2 instanceof Point3D) {
                Point3D p1 = (Point3D) o1; Point3D p2 = (Point3D) o2;
                
                // Opções de Criação
                addMenuItem(popup, "Criar Reta (Passando por P1 e P2)", () -> {
                    try {
                        scene.addLine(new Line3D(p1, p2));
                        refreshList(); canvas.repaint();
                        showMessage("Reta criada com sucesso!");
                    } catch (Exception ex) {
                        showMessage("Erro ao criar Reta: " + ex.getMessage());
                    }
                });
                
                addMenuItem(popup, "Criar Vetor (Direção P1 -> P2)", () -> {
                    Vector3D v = p2.subtract(p1);
                    scene.addVector(v);
                    refreshList(); canvas.repaint();
                    showMessage("Vetor criado com sucesso!\n" + v.toString());
                });

                popup.addSeparator(); // Separa a criação das operações matemáticas
                
                // Opções Matemáticas
                addMenuItem(popup, "Calcular Distância", () -> {
                    showMessage("Distância: " + String.format("%.4f", p1.distanceTo(p2)));
                    canvas.showTemporaryLine(p1, p2, 4000);
                });
                
                addMenuItem(popup, "Achar Ponto Médio", () -> {
                    Point3D mid = new Point3D((p1.getX()+p2.getX())/2, (p1.getY()+p2.getY())/2, (p1.getZ()+p2.getZ())/2);
                    scene.addPoint(mid);
                    refreshList();
                    canvas.showTemporaryLine(p1, p2, 4000);
                    showMessage("Ponto Médio adicionado à cena!\n" + mid.toString());
                });
            }
            
            // 2. PONTO & VETOR
            else if ((o1 instanceof Point3D && o2 instanceof Vector3D) || (o1 instanceof Vector3D && o2 instanceof Point3D)) {
                Point3D pt = (Point3D) (o1 instanceof Point3D ? o1 : o2);
                Vector3D vec = (Vector3D) (o1 instanceof Vector3D ? o1 : o2);
                
                addMenuItem(popup, "Criar Reta (Ponto + Vetor Diretor)", () -> {
                    try {
                        scene.addLine(new Line3D(pt, vec));
                        refreshList(); canvas.repaint();
                        showMessage("Reta criada com sucesso!");
                    } catch (Exception ex) {
                        showMessage("Erro: " + ex.getMessage());
                    }
                });
                
                addMenuItem(popup, "Criar Plano (Ponto + Vetor Normal)", () -> {
                    try {
                        scene.addPlane(new Plane3D(pt, vec));
                        refreshList(); canvas.repaint();
                        showMessage("Plano criado com sucesso!");
                    } catch (Exception ex) {
                        showMessage("Erro: " + ex.getMessage());
                    }
                });
            }
            
            // 3. PONTO & RETA.
            else if ((o1 instanceof Point3D && o2 instanceof Line3D) || (o1 instanceof Line3D && o2 instanceof Point3D)) {
                Point3D pt = (Point3D) (o1 instanceof Point3D ? o1 : o2);
                Line3D ln = (Line3D) (o1 instanceof Line3D ? o1 : o2);
                
                addMenuItem(popup, "Distância: Ponto à Reta", () -> {
                    double dist = ln.distanceTo(pt);
                    Vector3D w = pt.subtract(ln.getPoint());
                    double t = w.dot(ln.getDirection()); 
                    Point3D projL = ln.pointAt(t);
                    canvas.showTemporaryLine(pt, projL, 4000);
                    showMessage("Distância: " + String.format("%.4f", dist));
                });
            }

            // 4. PONTO & PLANO.
            else if ((o1 instanceof Point3D && o2 instanceof Plane3D) || (o1 instanceof Plane3D && o2 instanceof Point3D)) {
                Point3D pt = (Point3D) (o1 instanceof Point3D ? o1 : o2);
                Plane3D pl = (Plane3D) (o1 instanceof Plane3D ? o1 : o2);
                
                addMenuItem(popup, "Distância: Ponto ao Plano", () -> {
                    double dist = Math.abs(pl.distanceTo(pt));
                    canvas.showTemporaryLine(pt, pl.project(pt), 4000);
                    showMessage("Distância Absoluta: " + String.format("%.4f", dist));
                });

                addMenuItem(popup, "Projetar Ponto no Plano", () -> {
                    Point3D proj = pl.project(pt);
                    scene.addPoint(proj);
                    refreshList();
                    canvas.showTemporaryLine(pt, proj, 4000);
                    showMessage("Projeção adicionada!\n" + proj.toString());
                });
            }

            // 5. RETA & RETA.
            else if (o1 instanceof Line3D && o2 instanceof Line3D) {
                Line3D l1 = (Line3D) o1; Line3D l2 = (Line3D) o2;
                
                addMenuItem(popup, "Distância entre Retas", () -> {
                    double dist = l1.distanceTo(l2);
                    showMessage("Distância: " + String.format("%.4f", dist));
                    Vector3D v1 = l1.getDirection(); Vector3D v2 = l2.getDirection();
                    Vector3D w0 = l1.getPoint().subtract(l2.getPoint());
                    double denom = v1.dot(v1)*v2.dot(v2) - v1.dot(v2)*v1.dot(v2);
                    if (denom > 1e-6) {
                        double t1 = (v1.dot(v2)*v2.dot(w0) - v2.dot(v2)*v1.dot(w0)) / denom;
                        double t2 = (v1.dot(v1)*v2.dot(w0) - v1.dot(v2)*v1.dot(w0)) / denom;
                        canvas.showTemporaryLine(l1.pointAt(t1), l2.pointAt(t2), 8000);
                    }
                });
                
                addMenuItem(popup, "Ângulo entre Retas", () -> {
                    double ang = l1.angleTo(l2);
                    showMessage(String.format("Ângulo: %.2f° (%.4f rad)", Math.toDegrees(ang), ang));
                });
            }

            // 6. RETA & PLANO.
            else if ((o1 instanceof Line3D && o2 instanceof Plane3D) || (o1 instanceof Plane3D && o2 instanceof Line3D)) {
                Line3D ln = (Line3D) (o1 instanceof Line3D ? o1 : o2);
                Plane3D pl = (Plane3D) (o1 instanceof Plane3D ? o1 : o2);
                
                addMenuItem(popup, "Interseção Reta/Plano", () -> {
                    Point3D intersection = pl.intersectionWith(ln);
                    if (intersection == null) showMessage("Reta paralela ao plano.");
                    else {
                        scene.addPoint(intersection);
                        refreshList(); canvas.repaint();
                        showMessage("Interseção adicionada!\n" + intersection.toString());
                    }
                });
            }

            // 7. VETOR & VETOR.
            else if (o1 instanceof Vector3D && o2 instanceof Vector3D) {
                Vector3D v1 = (Vector3D) o1; Vector3D v2 = (Vector3D) o2;
                
                addMenuItem(popup, "Somar Vetores", () -> {
                    Vector3D result = v1.add(v2);
                    scene.addVector(result); refreshList(); canvas.repaint();
                });
                addMenuItem(popup, "Produto Vetorial (Cross)", () -> {
                    Vector3D result = v1.cross(v2);
                    scene.addVector(result); refreshList(); canvas.repaint();
                });
                addMenuItem(popup, "Ângulo entre eles", () -> {
                    double ang = v1.angleTo(v2);
                    showMessage(String.format("Ângulo: %.2f°", Math.toDegrees(ang)));
                });
            }
        } 
        
        // >>> OPERAÇÕES PARA UMA ENTIDADE SELECIONADA <<<
        else if (indices.length == 1) {
            Object o = getEntityAt(indices[0]);
            if (o instanceof Vector3D) {
                addMenuItem(popup, "Normalizar Vetor", () -> {
                    Vector3D norm = ((Vector3D)o).normalize();
                    scene.addVector(norm); refreshList(); canvas.repaint();
                });
            } else {
                JMenuItem empty = new JMenuItem("Selecione combinações de itens para operar");
                empty.setEnabled(false);
                popup.add(empty);
            }
        } else {
            JMenuItem empty = new JMenuItem("Selecione itens para gerar um menu");
            empty.setEnabled(false);
            popup.add(empty);
        }

        if (popup.getComponentCount() > 0) {
            popup.show(entityList, x, y);
        }
    }

    // Método utilitário para criar os botões do menu mais limpos.
    private void addMenuItem(JPopupMenu menu, String text, Runnable action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(e -> action.run());
        menu.add(item);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
}