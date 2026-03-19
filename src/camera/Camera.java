package src.camera;

import src.core.Point3D;
import src.core.Vector3D;

/**
 * Representa uma camera 3D com suporte a projecao perspectiva e ortografica.
 * 
 * Permite movimentacao orbital em torno de um ponto alvo,
 * alem de operacoes de zoom e projecao de pontos do espaco 3D
 */
public class Camera {
    private Point3D position;
    private Point3D target;
    private Vector3D up;
    private Vector3D u, v, n;

    
    private double theta = Math.PI / 4;
    private double phi = Math.PI / 4;
    private double radius = 15.0; 

    
    private double d = 800.0; 

    private boolean isPerspective = true;

    /**
     * Construtor da camera.
     * Define o ponto alvo e a distancia até ele (raio da orbita).
     *
     * @param target ponto que a camera ira observar
     * @param radius distância da camera até o alvo
     */
    public Camera(Point3D target, double radius) {
        this.target = target;
        this.radius = radius;
        this.up = new Vector3D(0, 1, 0);
        updatePositionFromSpherical(); 
    }

    
    /**
     * Move a camera orbitando o ponto alvo
     * 
     * @param deltaTheta variacao do angulo horizontal (RADS)
     * @param deltaPhi variacao do angulo vertical (RADS)
     */
    public void orbit(double deltaTheta, double deltaPhi) {
        this.theta += deltaTheta;
        this.phi += deltaPhi;

        
        double limit = Math.PI / 2 - 0.1;
        if (this.phi > limit) this.phi = limit;
        if (this.phi < -limit) this.phi = -limit;

        updatePositionFromSpherical();
    }
    
    
    /**
     * Ajusta o zoom da camera alterando a distância até o alvo.
     * O valor é limitado para evitar que a câmera se aproxime demais.
     *
     * @param amount intensidade do zoom (valores positivos aproximam, negativos afastam)
     */
    public void zoom(double amount) {

        this.d = Math.max(50.0, this.d - (amount * 50.0));
    }


    /**
     * Atualiza a posição da câmera a partir de coordenadas esféricas
     * (theta, phi e radius).
     */
    private void updatePositionFromSpherical() {
        
        double x = radius * Math.cos(phi) * Math.cos(theta);
        double y = radius * Math.sin(phi);
        double z = radius * Math.cos(phi) * Math.sin(theta);
        
        
        this.position = new Point3D(x, y, z);
        computeAxes();
    }

    /**
     * Calcula a base ortonormal da câmera.
     * 
     * n: direcao da camera (do alvo para a posicao)
     * u: eixo lateral (direita)
     * v: eixo vertical ajustado
     * 
     * Os vetores são normalizados para manter um sistema ortogonal.
     */
    private void computeAxes(){
        
        n = position.subtract(target).normalize();
        
        u = up.cross(n).normalize();
        
        v = n.cross(u).normalize();
    }

    
    /**
     * Alterna entre projecao perspectiva e ortografica.
     */
    public void toggleProjection() {
        this.isPerspective = !this.isPerspective;
    }

    /**
     * Retorna se a camera esta usando projecao perspectiva.
     *
     * @return true se o modo perspectiva esta ativo, false caso contrario
     */
    public boolean isPerspective() {
        return isPerspective;
    }

    /**
     * Projeta um ponto do mundo para coordenadas de tela.
     * Escolhe automaticamente entre projecao perspectiva e ortografica.
     *
     * @param worldPoint ponto no espaco 3D
     * @return array com coordenadas 2D [x, y]
     */
    public double[] project(Point3D worldPoint) {
        if (isPerspective) {
            return projectPerspective(worldPoint);
        } else {
            return projectOrtographic(worldPoint);
        }
    }

    /**
     * Projeta um ponto usando projecao perspectiva.
     * Objetos mais distantes aparecem menores conforme a profundidade.
     *
     * @param worldPoint ponto no espaco 3D
     * @return coordenadas 2D projetadas [x, y]
     */
    public double[] projectPerspective(Point3D worldPoint){
        Vector3D w = worldPoint.subtract(position);

        
        double xCam = w.dot(u);
        double yCam = w.dot(v);
        double zCam = w.dot(n); 

        
        if (zCam >= 0) zCam = -0.1;

        
        
        double screenX = (xCam * d) / Math.abs(zCam);
        double screenY = (yCam * d) / Math.abs(zCam);

        return new double[]{screenX, screenY};
    }

    
    /**
     * Projeta um ponto usando projecao ortografica.
     * Nao ha distorcao de profundidade.
     *
     * @param worldPoint ponto no espaco 3D
     * @return coordenadas 2D projetadas [x, y]
     */
    public double[] projectOrtographic(Point3D worldPoint){
        Vector3D w = worldPoint.subtract(position);
        
        double scale = d / radius; 
        
        double screenX = w.dot(u) * scale;
        double screenY = w.dot(v) * scale;
        return new double[]{screenX, screenY};
    }

    // GETTERS
    public Point3D getPosition() { return position; }
    public Point3D getTarget() { return target; }
    public Vector3D getUp() { return up; }
    public Vector3D getU() { return u; }
    public Vector3D getV() { return v; }
    public Vector3D getN() { return n; }
    public double getRadius() { return radius; }

    // SETTERS
    public void setPosition(Point3D position) { this.position = position; computeAxes(); }
    public void setTarget(Point3D target) { this.target = target; computeAxes(); }
    public void setUp(Vector3D up) { this.up = up; computeAxes(); }

}
