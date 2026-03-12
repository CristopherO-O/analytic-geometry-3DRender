package src.entities;

import java.util.ArrayList;
import java.util.List;
import src.core.Point3D;

public class Scene {
    private List<Point3D> points = new ArrayList<>();
    private List<Line3D> lines = new ArrayList<>();
    private List<Plane3D> planes = new ArrayList<>();
    // ==========================================================
    // >>>>> Construtor
    // ==========================================================
    public Scene() {
        this.points = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.planes = new ArrayList<>();
    }

    // ==========================================================
    // >>>>> Gerenciamento de Elementos da Cena
    // ==========================================================
    public void addPoint(Point3D p) { points.add(p); }
    public void addLine(Line3D l) { lines.add(l); }
    public void addPlane(Plane3D pl) { planes.add(pl); }

    // ==========================================================
    // >>>>> Remover Elementos da Cena
    // ==========================================================
    public void removeEntityAt(int index) {
        int pSize = points.size();
        int lSize = lines.size();

        if (index < pSize) {
            points.remove(index);
        } else if (index < pSize + lSize) {
            lines.remove(index - pSize);
        } else {
            planes.remove(index - pSize - lSize);
        }
    }

    // ==========================================================
    // >>>>> Listar os nomes das entidades para o painel de gerenciamento
    // ==========================================================
    public List<String> getEntityNames() {
        List<String> names = new ArrayList<>();
        for (Point3D p : points) names.add("Ponto: " + p);
        for (Line3D l : lines) names.add("Reta: " + l);
        for (Plane3D pl : planes) names.add("Plano: " + pl);
        return names;
    }
    
    // ==========================================================
    // >>>>> Getters
    // ==========================================================
    public List<Point3D> getPoints() { return points; }
    public List<Line3D> getLines() { return lines; }
    public List<Plane3D> getPlanes() { return planes; }

    // ==========================================================
    // >>>>> Limpar a cena
    // ==========================================================
    public void clear() {
        points.clear();
        lines.clear();
        planes.clear();
    }
}