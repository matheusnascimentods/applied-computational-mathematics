import java.util.Scanner;

public class parte3 {
    public static void executar() {
        int opcao;
        boolean continuar = true;

        while (continuar) {
            IO.println("Qual resolução você quer ver?");
            IO.println("1-Método de Simpson 1/3");
            IO.println("2-Método do trapézio");
            IO.println("3-Derivação Numérica (Taxa de Variação)");
            IO.println("4-Voltar para o inicio");
            opcao = new Scanner(System.in).nextInt();

            switch (opcao) {
                case 1 -> metodo_de_simpson();
                case 2 -> metodo_do_trapezio();
                case 3 -> derivacao_numerica();
                case 4 -> continuar = false;
                default -> IO.println("Opção invalída");
            }
        }
    }

    private static void metodo_de_simpson () {
        double h = 0.5;
        double[] subintervalos = new double[31];
        double soma_impares = 0;
        double soma_pares = 0;

        IO.println("\nResolução com Método de Simpson 1/3");
        IO.println(String.format("%-12s%-12s%-12s", "Indice", "z", "w(z)"));
        IO.println("-".repeat(36));

        for (int i = 0; i < subintervalos.length; i++) {
            subintervalos[i] = w(i * h);

            if (i > 0 && i != subintervalos.length - 1) {
                if (i%2 == 0) {
                    soma_pares += subintervalos[i];
                }
                else {
                    soma_impares += subintervalos[i];
                }
            }
            IO.println(String.format("%-12d%-12f%-12f", i, (i*h), subintervalos[i]));
        }

        double resultado_simpsoon = h/3 * (subintervalos[0] + subintervalos[subintervalos.length - 1] + 4 * soma_impares + 2 * soma_pares);
        IO.println(String.format("\nResultado (Metodo de Simpson 1/3): %f\n", resultado_simpsoon));
    }

    private static void metodo_do_trapezio () {
        double h = 0.5;
        double[] subintervalos = new double[31];
        double soma_impares = 0;
        double soma_pares = 0;

        IO.println("\nResolução com Método do Trapézio");
        IO.println(String.format("%-12s%-12s%-12s", "Indice", "z", "w(z)"));
        IO.println("-".repeat(36));

        for (int i = 0; i < subintervalos.length; i++) {
            subintervalos[i] = w(i * h);

            if (i > 0 && i != subintervalos.length - 1) {
                if (i%2 == 0) {
                    soma_pares += subintervalos[i];
                }
                else {
                    soma_impares += subintervalos[i];
                }
            }
            IO.println(String.format("%-12d%-12f%-12f", i, (i*h), subintervalos[i]));
        }

        double resultado_trapezio = h/2 * (subintervalos[0] + subintervalos[subintervalos.length - 1] + 2 * (soma_impares + soma_pares));
        IO.println(String.format("\nResultado (Metodo de trapezio): %f\n", resultado_trapezio));
    }

    private static void derivacao_numerica () {
        double h = 0.5;
        double z_medio = 7.5; // O ponto médio da torre de 15m

        IO.println("\nResolução com Derivação Numérica (Diferença Finita Centrada)");
        IO.println("-".repeat(72));

        // Fórmula da Diferença Centrada: w'(z) ≈ (w(z + h) - w(z - h)) / (2 * h)
        double w_frente = w(z_medio + h);
        double w_tras = w(z_medio - h);
        double derivada = (w_frente - w_tras) / (2 * h);

        IO.println(String.format("Calculando a taxa de variação no ponto médio z = %.1f m (com h = %.1f)", z_medio, h));
        IO.println(String.format("Pressão um passo à frente w(%.1f) = %f", (z_medio + h), w_frente));
        IO.println(String.format("Pressão um passo atrás w(%.1f) = %f", (z_medio - h), w_tras));
        IO.println(String.format("\nResultado (Taxa de variação dw/dz): %f\n", derivada));
    }

    private static double w (double z) {
        return 0.1 * Math.pow(z, 3) - 1.5 * Math.pow(z, 2) + 10 * z + 20;
    }
}
