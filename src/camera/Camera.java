package src.camera;

import src.core.Point3D;
import src.core.Vector3D;

public class Camera {
    private Point3D position;
    private Point3D target;
    private Vector3D up;
    private Vector3D u, v, n;

    // Campos para controle de órbita.
    private double theta = Math.PI / 4;
    private double phi = Math.PI / 4;
    private double radius = 15.0;

    // ==========================================================
    // Construtor.
    // ==========================================================
    public Camera(Point3D target, double radius) {
        this.target = target;
        this.radius = radius;
        this.up = new Vector3D(0, 1, 0);
        updatePositionFromSpherical(); // Calcula a posição inicial.
    }

    // ==========================================================
    // Controle da orbita da câmera.
    // ==========================================================
    public void orbit(double deltaTheta, double deltaPhi) {
        this.theta += deltaTheta;
        this.phi += deltaPhi;

        // Limita o ângulo vertical para não "capotar" a câmera (singularidade).
        double limit = Math.PI / 2 - 0.1;
        if (this.phi > limit) this.phi = limit;
        if (this.phi < -limit) this.phi = -limit;

        updatePositionFromSpherical();
    }
    
    // ==========================================================
    // Controle do zoom da camera.
    // ==========================================================
    public void zoom(double amount) {
        this.radius = Math.max(1.0, this.radius + amount);
        updatePositionFromSpherical();
    }


    // ==========================================================
    // Atualiza a posição da câmera com base nos ângulos.
    // ==========================================================
    private void updatePositionFromSpherical() {
        // Conversão de Coordenadas Esféricas para Cartesianas.
        double x = radius * Math.cos(phi) * Math.cos(theta);
        double y = radius * Math.sin(phi);
        double z = radius * Math.cos(phi) * Math.sin(theta);
        
        // Atualiza a posição e recalcula os eixos ortonormais.
        this.position = new Point3D(x, y, z);
        computeAxes();
    }

    // ==========================================================
    // Calcula os eixos.
    // ==========================================================
    private void computeAxes(){
        // N (Z da camera) = normalize(pos-target).
        n = position.subtract(target).normalize();
        // U (X da camera) = normalize(up x n).
        u = up.cross(n).normalize();
        // V (Y da camera) = normalize(n x u).
        v = n.cross(u).normalize();
    }

    // ==========================================================
    // Projecao ortografica (converte 3d pra 2d da tela).
    // ==========================================================
    public double[] projectOrtographic(Point3D worldPoint){
        Vector3D w = worldPoint.subtract(position);

        // Projeta o vetor do ponto nos eixos U e V da camera.
        double screenX = w.dot(u);
        double screenY = w.dot(v);
        return new double[]{screenX, screenY};
    }

    // ==========================================================
    // Getters.
    // ==========================================================
    public Point3D getPosition() { return position; }
    public Point3D getTarget() { return target; }
    public Vector3D getUp() { return up; }
    public Vector3D getU() { return u; }
    public Vector3D getV() { return v; }
    public Vector3D getN() { return n; }
    public double getRadius() { return radius; }

    // ==========================================================
    // Setters.
    // ==========================================================
    public void setPosition(Point3D position) {
        this.position = position;
        computeAxes();
    }

    public void setTarget(Point3D target) {
        this.target = target;
        computeAxes();
    }

    public void setUp(Vector3D up) {
        this.up = up;
        computeAxes();
    }

}
