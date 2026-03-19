package src.gui.panels;

import javax.swing.*;
import java.awt.*;
import src.entities.Scene;
import src.entities.Line3D;
import src.entities.Plane3D;
import src.core.Point3D;
import src.core.Vector3D;
import src.gui.RenderCanvas;

/**
 * Panel for geometric operations between selected entities.
 */
public class OperationsPanel extends JPanel {

    private Scene scene;
    private RenderCanvas canvas;

    private JComboBox<String> cbOperation;
    private JComboBox<Object> cbItem1;
    private JComboBox<Object> cbItem2;
    private JLabel lblItem1;
    private JLabel lblItem2;
    
    private JPanel dynamicPanel;
    private JTextArea txtResult; 
    
    /**
     * Creates the operations panel and UI controls.
     * @param scene scene where entities are stored
     * @param canvas canvas to show temporary lines and repaint
     */
    public OperationsPanel(Scene scene, RenderCanvas canvas) {
        this.scene = scene;
        this.canvas = canvas;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 48));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(new Color(45, 45, 48));

        JButton btnRefresh = new JButton("Atualizar Listas");
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBackground(new Color(70, 70, 75));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> updateDynamicBoxes());

        
        String[] ops = {
            "Selecione uma operação...",           
            "Ponto: Distância a Ponto",            
            "Ponto: Ponto Médio",                  
            "Ponto: Distância a Reta",             
            "Ponto: Distância a Plano",            
            "Ponto: Projetar no Plano",            
            "Reta: Distância a Reta",              
            "Reta: Ângulo com Reta",               
            "Reta: Ângulo com Plano",              
            "Reta: Interseção com Plano",          
            "Plano: Interseção com Plano",         
            "Vetores: Soma",                       
            "Vetores: Subtração",                  
            "Vetores: Produto Escalar (Dot)",      
            "Vetores: Produto Vetorial (Cross)",   
            "Vetores: Ângulo entre eles",          
            "Vetores: Projetar (V1 em V2)",        
            "Vetores: Refletir (V1 na Normal V2)"  
        };
        cbOperation = new JComboBox<>(ops);
        cbOperation.addActionListener(e -> updateDynamicBoxes());

        topPanel.add(btnRefresh, BorderLayout.NORTH);
        topPanel.add(new JLabel("Operação: "), BorderLayout.WEST);
        topPanel.add(cbOperation, BorderLayout.CENTER);

        dynamicPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        dynamicPanel.setBackground(new Color(45, 45, 48));
        
        lblItem1 = createWhiteLabel("Item 1:");
        cbItem1 = new JComboBox<>();
        lblItem2 = createWhiteLabel("Item 2:");
        cbItem2 = new JComboBox<>();

        dynamicPanel.add(lblItem1); dynamicPanel.add(cbItem1);
        dynamicPanel.add(lblItem2); dynamicPanel.add(cbItem2);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBackground(new Color(45, 45, 48));

        JButton btnCalculate = new JButton("Calcular");
        btnCalculate.setBackground(new Color(80, 140, 255));
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.setFocusPainted(false);
        btnCalculate.addActionListener(e -> calculateOperation());

        txtResult = new JTextArea(4, 20);
        txtResult.setEditable(false);
        txtResult.setBackground(new Color(30, 30, 32));
        txtResult.setForeground(new Color(80, 200, 120));
        txtResult.setFont(new Font("Consolas", Font.BOLD, 14));
        txtResult.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        bottomPanel.add(btnCalculate, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(txtResult), BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(dynamicPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        dynamicPanel.setVisible(false);
    }

    /**
     * Updates item selectors based on selected operation.
     */
    private void updateDynamicBoxes() {
        int op = cbOperation.getSelectedIndex();
        cbItem1.removeAllItems();
        cbItem2.removeAllItems();
        
        if (op == 0) { dynamicPanel.setVisible(false); return; }
        dynamicPanel.setVisible(true);

        switch (op) {
            case 1: case 2: 
                lblItem1.setText("Ponto 1:"); lblItem2.setText("Ponto 2:");
                for (Point3D p : scene.getPoints()) { cbItem1.addItem(p); cbItem2.addItem(p); }
                break;
            case 3: 
                lblItem1.setText("Ponto:"); lblItem2.setText("Reta:");
                for (Point3D p : scene.getPoints()) cbItem1.addItem(p);
                for (Line3D l : scene.getLines()) cbItem2.addItem(l);
                break;
            case 4: case 5: 
                lblItem1.setText("Ponto:"); lblItem2.setText("Plano:");
                for (Point3D p : scene.getPoints()) cbItem1.addItem(p);
                for (Plane3D pl : scene.getPlanes()) cbItem2.addItem(pl);
                break;
            case 6: case 7: 
                lblItem1.setText("Reta 1:"); lblItem2.setText("Reta 2:");
                for (Line3D l : scene.getLines()) { cbItem1.addItem(l); cbItem2.addItem(l); }
                break;
            case 8: case 9: 
                lblItem1.setText("Reta:"); lblItem2.setText("Plano:");
                for (Line3D l : scene.getLines()) cbItem1.addItem(l);
                for (Plane3D pl : scene.getPlanes()) cbItem2.addItem(pl);
                break;
            case 10: 
                lblItem1.setText("Plano 1:"); lblItem2.setText("Plano 2:");
                for (Plane3D pl : scene.getPlanes()) { cbItem1.addItem(pl); cbItem2.addItem(pl); }
                break;
            case 11: case 12: case 13: case 14: case 15: case 16: case 17: 
                lblItem1.setText(op == 17 ? "Vetor (Incidente):" : "Vetor 1:"); 
                lblItem2.setText(op == 17 ? "Normal (Reflexão):" : "Vetor 2:");
                for (Vector3D v : scene.getVectors()) { cbItem1.addItem(v); cbItem2.addItem(v); }
                break;
        }
    }

    
    
    
    /**
     * Performs the selected geometric operation and updates result.
     */
    private void calculateOperation() {
        int op = cbOperation.getSelectedIndex();
        if (op == 0) return;

        Object item1 = cbItem1.getSelectedItem();
        Object item2 = cbItem2.getSelectedItem();

        try {
            if (item1 == null || item2 == null) throw new Exception("Selecione os itens nas listas.");

            switch (op) {
                case 1: 
                    Point3D p1 = (Point3D)item1; Point3D p2 = (Point3D)item2;
                    txtResult.setText(String.format("Distância:\n%.4f", p1.distanceTo(p2)));
                    canvas.showTemporaryLine(p1, p2, 4000);
                    break;
                
                case 2: 
                    Point3D pm1 = (Point3D)item1; Point3D pm2 = (Point3D)item2;
                    Point3D mid = new Point3D((pm1.getX()+pm2.getX())/2, (pm1.getY()+pm2.getY())/2, (pm1.getZ()+pm2.getZ())/2);
                    txtResult.setText(String.format("Ponto Médio:\n%s", mid.toString()));
                    scene.addPoint(mid);
                    canvas.showTemporaryLine(pm1, pm2, 4000);
                    break;

                case 3: 
                    Point3D pt3 = (Point3D)item1; Line3D ln3 = (Line3D)item2;
                    txtResult.setText(String.format("Distância:\n%.4f", ln3.distanceTo(pt3)));
                    Vector3D w = pt3.subtract(ln3.getPoint());
                    double t = w.dot(ln3.getDirection()); 
                    Point3D projL = ln3.pointAt(t);
                    canvas.showTemporaryLine(pt3, projL, 4000);
                    break;

                case 4: 
                    Point3D pt4 = (Point3D)item1; Plane3D pl4 = (Plane3D)item2;
                    txtResult.setText(String.format("Distância Absoluta:\n%.4f", Math.abs(pl4.distanceTo(pt4))));
                    canvas.showTemporaryLine(pt4, pl4.project(pt4), 4000);
                    break;

                case 5: 
                    Point3D pt5 = (Point3D)item1; Plane3D pl5 = (Plane3D)item2;
                    Point3D projPoint = pl5.project(pt5);
                    txtResult.setText(String.format("Ponto Projetado:\n%s", projPoint.toString()));
                    scene.addPoint(projPoint); 
                    canvas.showTemporaryLine(pt5, projPoint, 4000);
                    break;

                case 6: 
                    Line3D lr1 = (Line3D)item1; Line3D lr2 = (Line3D)item2;
                    txtResult.setText(String.format("Distância entre retas:\n%.4f", lr1.distanceTo(lr2)));
                    Vector3D v1 = lr1.getDirection(); Vector3D v2 = lr2.getDirection();
                    Vector3D w0 = lr1.getPoint().subtract(lr2.getPoint());
                    double a = v1.dot(v1); double b = v1.dot(v2); double c = v2.dot(v2);
                    double d = v1.dot(w0); double e = v2.dot(w0); double denom = a * c - b * b;
                    double t1, t2;
                    if (denom < 1e-6) { t1 = 0; t2 = e / c; } 
                    else { t1 = (b * e - c * d) / denom; t2 = (a * e - b * d) / denom; }
                    canvas.showTemporaryLine(lr1.pointAt(t1), lr2.pointAt(t2), 4000); 
                    break;

                case 7: 
                    double angRadL = ((Line3D)item1).angleTo((Line3D)item2);
                    txtResult.setText(String.format("Ângulo entre Retas:\n%.2f° (%.4f rad)", Math.toDegrees(angRadL), angRadL));
                    break;

                case 8: 
                    Line3D lAng = (Line3D)item1; Plane3D pAng = (Plane3D)item2;
                    
                    double dotRP = Math.abs(lAng.getDirection().dot(pAng.getNormal()));
                    double angRP = Math.asin(dotRP);
                    txtResult.setText(String.format("Ângulo Reta/Plano:\n%.2f° (%.4f rad)", Math.toDegrees(angRP), angRP));
                    break;

                case 9: 
                    Point3D intersection = ((Plane3D)item2).intersectionWith((Line3D)item1);
                    if (intersection == null) txtResult.setText("Reta paralela ao plano.");
                    else {
                        txtResult.setText(String.format("Interseção em:\n%s", intersection.toString()));
                        scene.addPoint(intersection); canvas.repaint();
                    }
                    break;

                case 10: 
                    Plane3D plano1 = (Plane3D)item1; Plane3D plano2 = (Plane3D)item2;
                    Vector3D n1 = plano1.getNormal(); Vector3D n2 = plano2.getNormal();
                    Vector3D dirCruzada = n1.cross(n2);
                    
                    if (dirCruzada.magnitude() < 1e-6) {
                        txtResult.setText("Planos são paralelos ou coincidentes.");
                    } else {
                        double d1 = plano1.getD(); double d2 = plano2.getD();
                        double detPlano = n1.dot(n1) * n2.dot(n2) - Math.pow(n1.dot(n2), 2);
                        double c1_plano = (-d1 * n2.dot(n2) + d2 * n1.dot(n2)) / detPlano;
                        double c2_plano = (-d2 * n1.dot(n1) + d1 * n1.dot(n2)) / detPlano;
                        
                        Point3D ptNaReta = new Point3D(0,0,0).add(n1.scale(c1_plano)).add(n2.scale(c2_plano));
                        Line3D retaIntersecao = new Line3D(ptNaReta, dirCruzada);
                        
                        scene.addLine(retaIntersecao);
                        txtResult.setText(String.format("Reta de Interseção adicionada:\n%s", retaIntersecao.toString()));
                        canvas.repaint();
                    }
                    break;
                case 11:
                    Vector3D sum = ((Vector3D)item1).add((Vector3D)item2);
                    txtResult.setText(String.format("Soma Resultante:\n%s", sum.toString()));
                    scene.addVector(sum); canvas.repaint();
                    break;
                case 12:
                    Vector3D sub = ((Vector3D)item1).subtract((Vector3D)item2);
                    txtResult.setText(String.format("Subtração Resultante:\n%s", sub.toString()));
                    scene.addVector(sub); canvas.repaint();
                    break;
                case 13:
                    double dot = ((Vector3D)item1).dot((Vector3D)item2);
                    txtResult.setText(String.format("Produto Escalar (Dot):\n%.4f", dot));
                    break;
                case 14:
                    Vector3D cross = ((Vector3D)item1).cross((Vector3D)item2);
                    txtResult.setText(String.format("Produto Vetorial (Cross):\n%s", cross.toString()));
                    scene.addVector(cross); canvas.repaint();
                    break;
                case 15:
                    double angRadV = ((Vector3D)item1).angleTo((Vector3D)item2);
                    txtResult.setText(String.format("Ângulo:\n%.2f° (%.4f rad)", Math.toDegrees(angRadV), angRadV));
                    break;
                case 16:
                    Vector3D projV = ((Vector3D)item1).projectOnto((Vector3D)item2);
                    txtResult.setText(String.format("Vetor Projetado:\n%s", projV.toString()));
                    scene.addVector(projV); canvas.repaint();
                    break;
                case 17:
                    Vector3D refl = ((Vector3D)item1).reflect((Vector3D)item2);
                    txtResult.setText(String.format("Vetor Refletido:\n%s", refl.toString()));
                    scene.addVector(refl); canvas.repaint();
                    break;
            }
    
        } catch (Exception ex) {
            txtResult.setText("Erro: " + ex.getMessage());
        }
    }

    
    
    
    /**
     * Creates a label with white font.
     * @param text label text
     * @return configured JLabel
     */
    private JLabel createWhiteLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        return l;
    }
}
