package src.entities;

import java.util.ArrayList;
import java.util.List;

import src.core.Point3D;
import src.core.Vector3D;

/**
 * Representa uma cena contendo entidades geometricas 3D.
 * Permite armazenar e manipular pontos, retas, planos e vetores.
 */
public class Scene {

    private final List<Point3D> points = new ArrayList<>();
    private final List<Line3D> lines = new ArrayList<>();
    private final List<Plane3D> planes = new ArrayList<>();
    private final List<Vector3D> vectors = new ArrayList<>();

    /**
     * Cria uma cena vazia.
     */
    public Scene() {}

    // ADD

    public void addPoint(Point3D p) { points.add(p); }

    public void addLine(Line3D l) { lines.add(l); }

    public void addPlane(Plane3D pl) { planes.add(pl); }

    public void addVector(Vector3D v) { vectors.add(v); }

    /**
     * Remove uma entidade com base em um indice global.
     * A ordem segue: pontos, retas, planos, vetores.
     */
    public void removeEntityAt(int index) {
        int total = points.size() + lines.size() + planes.size() + vectors.size();

        if (index < 0 || index >= total) {
            throw new IndexOutOfBoundsException("Indice invalido: " + index);
        }

        int pSize = points.size();
        int lSize = lines.size();
        int plSize = planes.size();

        if (index < pSize) {
            points.remove(index);

        } else if (index < pSize + lSize) {
            lines.remove(index - pSize);

        } else if (index < pSize + lSize + plSize) {
            planes.remove(index - pSize - lSize);

        } else {
            vectors.remove(index - pSize - lSize - plSize);
        }
    }
    /**
     * Retorna nomes descritivos das entidades da cena.
     */
    public List<String> getEntityNames() {
        List<String> names = new ArrayList<>();

        for (Point3D p : points) names.add("Ponto: " + p);
        for (Line3D l : lines) names.add("Reta: " + l);
        for (Plane3D pl : planes) names.add("Plano: " + pl);
        for (Vector3D v : vectors) names.add("Vetor: " + v);

        return names;
    }

    // GETTERS

    public List<Point3D> getPoints() { return points; }

    public List<Line3D> getLines() { return lines; }

    public List<Plane3D> getPlanes() { return planes; }

    public List<Vector3D> getVectors() { return vectors; }

    /**
     * Remove todas as entidades da cena.
     */
    public void clear() {
        points.clear();
        lines.clear();
        planes.clear();
        vectors.clear();
    }
}