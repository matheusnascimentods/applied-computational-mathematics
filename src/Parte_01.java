void main() {
    // Eliminação de Gauss
    double[][] matriz = {
        {4, -1, 0, 0, 50},
        {-1, 4, -1, 0, 0},
        {0, -1, 4, -1, 80},
        {0, 0, -1, 3, 0}
    };

    int n = matriz.length;

    imprimirMatriz(matriz, n, "Matriz Inicial");

    for (int colunaAtual = 0; colunaAtual < n; colunaAtual++) {
        for (int linhaAlvo = colunaAtual + 1; linhaAlvo < n; linhaAlvo++) {
            double pivo = matriz[linhaAlvo][colunaAtual] / matriz[colunaAtual][colunaAtual];

            for (int coluna = colunaAtual; coluna <= n; coluna++) {
                matriz[linhaAlvo][coluna] -= pivo * matriz[colunaAtual][coluna];
            }
        }
    }

    imprimirMatriz(matriz, n, "Matriz Triangular Superior");

    double[] solucao = new double[n];
    for (int i = n - 1; i >= 0; i--) {
        solucao[i] = matriz[i][n];

        for (int j = i + 1; j < n; j++) {
            solucao[i] -= matriz[i][j] * solucao[j];
        }

        solucao[i] /= matriz[i][i];
    }

    imprimirSolucao(solucao, n);

    // Método iterativo

}

void imprimirMatriz(double[][] matriz, int n, String titulo) {
    // Monta o header com as colunas F1..Fn e b
    String header = "";
    for (int j = 0; j < n; j++) {
        header += String.format("%-12s", "F" + (j + 1));
    }
    header += String.format("%-12s", "b");

    IO.println("\n" + titulo);
    IO.println("-".repeat(header.length()));

    for (int i = 0; i < n; i++) {
        String linha = "";
        for (int j = 0; j <= n; j++) {
            linha += String.format("%-12.4f", matriz[i][j]);
        }
        IO.println(linha);
    }

    IO.println("-".repeat(header.length()));
}

void imprimirSolucao(double[] solucao, int n) {
    String header = "";
    for (int i = 0; i < n; i++) {
        header += String.format("%-12s", "F" + (i + 1));
    }

    IO.println("\nIncognitas");
    IO.println("-".repeat(header.length()));
    IO.println(header);
    IO.println("-".repeat(header.length()));

    String linha = "";
    for (int i = 0; i < n; i++) {
        linha += String.format("%-12.4f", solucao[i]);
    }
    IO.println(linha);
    IO.println("-".repeat(header.length()));
}