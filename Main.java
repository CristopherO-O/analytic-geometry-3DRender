package src;
import java.util.HashSet;
import java.util.Set;
// Não é necessário importar Objects pois o uso já está nas classes Point3D e Vector3D

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Testes de Geometria Analítica 3D ---\n");

        // ----------------------------------------------------
        // 1. Instanciação e Getters
        // ----------------------------------------------------
        System.out.println("--- 1. Instanciação e Getters ---");
        Point3D p1 = new Point3D(1.0, 2.0, 3.0);
        Point3D p2 = new Point3D(4.0, 6.0, 8.0);
        Vector3D u = new Vector3D(1.0, 0.0, 0.0); // Vetor unitário no eixo X
        Vector3D v = new Vector3D(0.0, 5.0, 12.0); // Vetor comum

        System.out.println("P1: " + p1);
        System.out.println("Vetor u: " + u);
        System.out.printf("Tolerância (EPSILON): %.10f%n", SpatialBase.getEpsilon());
        System.out.println("----------------------------------------\n");


        // ----------------------------------------------------
        // 2. Testes de Ponto (Point3D)
        // ----------------------------------------------------
        System.out.println("--- 2. Testes de Ponto (Point3D) ---");
        double dist = p1.distanceTo(p2);
        System.out.printf("Distância P1 para P2: %.2f%n", dist); // Deve ser sqrt(3^2 + 4^2 + 5^2) = sqrt(50) ≈ 7.07

        Vector3D p1_vec = p1.toVector();
        System.out.println("P1 como vetor: " + p1_vec);
        System.out.println("----------------------------------------\n");


        // ----------------------------------------------------
        // 3. Testes de Operações Vetoriais Básicas
        // ----------------------------------------------------
        System.out.println("--- 3. Testes de Operações Vetoriais ---");
        Vector3D u_plus_v = u.add(v);
        System.out.println("u + v: " + u_plus_v); // Deve ser (1, 5, 12)

        Vector3D v_scaled = v.scale(0.5);
        System.out.println("v * 0.5: " + v_scaled); // Deve ser (0, 2.5, 6.0)

        // Norma/Magnitude
        double mag_v = v.magnitude();
        System.out.printf("Magnitude ||v||: %.2f%n", mag_v); // Deve ser sqrt(0^2 + 5^2 + 12^2) = 13.0

        // Normalização
        Vector3D v_norm = v.normalize();
        System.out.println("Vetor v normalizado: " + v_norm); // Deve ser (0, 5/13, 12/13) ≈ (0, 0.38, 0.92)
        System.out.printf("Magnitude do normalizado: %.2f%n", v_norm.magnitude());

        System.out.println("----------------------------------------\n");


        // ----------------------------------------------------
        // 4. Testes de Produtos Vetoriais e Projeção
        // ----------------------------------------------------
        System.out.println("--- 4. Produtos e Projeção ---");
        Vector3D w = new Vector3D(2.0, 3.0, 4.0);
        Vector3D z = new Vector3D(5.0, 6.0, 7.0);

        // Produto Escalar (Dot)
        double dot_result = w.dot(z);
        // 2*5 + 3*6 + 4*7 = 10 + 18 + 28 = 56
        System.out.println("Produto Escalar (w . z): " + dot_result); 

        // Produto Vetorial (Cross)
        Vector3D cross_result = w.cross(z);
        // (3*7 - 4*6, 4*5 - 2*7, 2*6 - 3*5) = (21-24, 20-14, 12-15) = (-3, 6, -3)
        System.out.println("Produto Vetorial (w x z): " + cross_result); 

        // Ângulo
        double angle_rad = w.angleTo(z);
        double angle_deg = Math.toDegrees(angle_rad);
        System.out.printf("Ângulo entre w e z: %.2f rad (%.2f graus)%n", angle_rad, angle_deg);

        // Projeção
        Vector3D proj_w_onto_z = w.projectOnto(z);
        System.out.println("Projeção de w sobre z: " + proj_w_onto_z); 
        
        System.out.println("----------------------------------------\n");


        // ----------------------------------------------------
        // 5. Testes de Igualdade (equals) e HashCode
        // ----------------------------------------------------
        System.out.println("--- 5. Testes de Igualdade (EPSILON) e HashCode ---");
        Point3D p_exact = new Point3D(5.0, 5.0, 5.0);
        // Ponto ligeiramente diferente (dentro da tolerância)
        Point3D p_close = new Point3D(5.0 + SpatialBase.getEpsilon() / 2, 5.0, 5.0);
        Point3D p_far = new Point3D(5.0 + 10 * SpatialBase.getEpsilon(), 5.0, 5.0); // Fora da tolerância

        System.out.println("p_exact: " + p_exact);
        System.out.println("p_close: " + p_close);

        System.out.println("p_exact equals p_exact? " + p_exact.equals(p_exact)); // true
        System.out.println("p_exact equals p_close? " + p_exact.equals(p_close)); // true (devido ao EPSILON)
        System.out.println("p_exact equals p_far?   " + p_exact.equals(p_far)); // false

        // Teste de HashCode em HashSet
        Set<Point3D> pointSet = new HashSet<>();
        pointSet.add(p_exact);
        pointSet.add(p_close); // Deve ser ignorado porque equals(p_exact) é true

        System.out.println("\nTamanho do HashSet: " + pointSet.size()); // 1 (Confirma que equals/hashCode funcionam com EPSILON)
        System.out.println("HashCode de p_exact: " + p_exact.hashCode());
        System.out.println("HashCode de p_close: " + p_close.hashCode());
        
        System.out.println("----------------------------------------");
    }
}