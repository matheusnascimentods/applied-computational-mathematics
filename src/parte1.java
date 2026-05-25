import java.util.Scanner;

public class parte1 {
    public static void executar() {
        int opcao;
        boolean continuar = true;

        while (continuar) {
            IO.println("Qual resolução você quer ver?");
            IO.println("1-Eliminação de Gauss");
            IO.println("2-Gauss Seidel");
            IO.println("3-Voltar para o inicio");
            opcao = new Scanner(System.in).nextInt();

            switch (opcao) {
                case 1 -> eliminacao_de_gauss();
                case 2 -> gauss_seidel();
                case 3 -> continuar = false;
                default -> IO.println("Opção invalída");
            }
        }
    }

    private static void eliminacao_de_gauss () {
        IO.println("\nResolução com eliminação de Gauss");

        double[][] matriz = {
                {4, -1, 0, 0, 50},
                {-1, 4, -1, 0, 0},
                {0, -1, 4, -1, 80},
                {0, 0, -1, 3, 0}
        };

        int n = matriz.length;

        IO.println("\nMatriz Inicial");
        IO.println("-".repeat(60));
        for (int i = 0; i < n; i++) {
            String linha = "";
            for (int j = 0; j <= n; j++) {
                linha += String.format("%-12.0f", matriz[i][j]);
            }
            IO.println(linha);
        }

        for (int colunaAtual = 0; colunaAtual < n; colunaAtual++) {
            for (int linhaAlvo = colunaAtual + 1; linhaAlvo < n; linhaAlvo++) {
                double pivo = matriz[linhaAlvo][colunaAtual] / matriz[colunaAtual][colunaAtual];

                for (int coluna = colunaAtual; coluna <= n; coluna++) {
                    matriz[linhaAlvo][coluna] -= pivo * matriz[colunaAtual][coluna];
                }
            }
        }

        IO.println("\nMatriz Triangular Superior");
        IO.println("-".repeat(60));
        for (int i = 0; i < n; i++) {
            String linha = "";
            for (int j = 0; j <= n; j++) {
                linha += String.format("%-12.2f", matriz[i][j]);
            }
            IO.println(linha);
        }
        IO.println("");

        double[] f = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            f[i] = matriz[i][n];
            for (int j = i + 1; j < n; j++) {
                f[i] -= matriz[i][j] * f[j];
            }
            f[i] /= matriz[i][i];
        }

        IO.println("Incognitas");
        IO.println("-".repeat(60));
        IO.println(String.format("%-12s%-12s%-12s%-12s", "F1", "F2", "F3", "F4"));
        IO.println(String.format("%-12f%-12f%-12f%-12f\n", f[0], f[1], f[2], f[3]));
    }

    private static void gauss_seidel () {
        IO.println("\nResolução com Gauss Seidel");
        IO.println("-".repeat(60));

        double[] f = {0, 0, 0, 0};
        double[] f_anterior = {0, 0, 0, 0};
        boolean pode_parar = false;
        double tolerancia = 1e-5;
        double erro = 0;

        IO.println(String.format("%-12s%-12s%-12s%-12s%-12s", "Iteração", "F1", "F2", "F3", "F4"));

        for (int i = 1; !pode_parar; i++) {

            f_anterior[0] = f[0];
            f[0] = (50 + f[1]) / 4;

            f_anterior[1] = f[1];
            f[1] = (0 + f[0] + f[2]) / 4;

            f_anterior[2] = f[2];
            f[2] = (80 + f[1] + f[3]) / 4;

            f_anterior[3] = f[3];
            f[3] = (0 + f[2]) / 3;

            pode_parar = true;
            for (int j = 0; j < f.length; j++) {
                erro = Math.abs((f[j] - f_anterior[j]) / f[j]);
                if (erro > tolerancia) {
                    pode_parar = false;
                    break;
                }
            }

            IO.println(String.format("%-12d%-12f%-12f%-12f%-12f", i, f[0], f[1], f[2], f[3]));
        }

        IO.println(String.format("\n%-12s%-12s%-12s%-12s", "F1", "F2", "F3", "F4"));
        IO.println(String.format("%-12f%-12f%-12f%-12f\n", f[0], f[1], f[2], f[3]));
    }

}