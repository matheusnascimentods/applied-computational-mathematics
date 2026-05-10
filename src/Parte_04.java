void main() {
    double a[] = {0, 0, 0};
    double b[] = {2, 4, 1};
    double c[] = {4, 0, 2};

    double ab[] = {(b[0] - a[0]), (b[1] - a[1]), (b[2] - a[2])};
    double ac[] = {(c[0] - a[0]), (c[1] - a[1]), (c[2] - a[2])};

    double modulo_ab = Math.sqrt(Math.pow(ab[0], 2) + Math.pow(ab[1], 2) + Math.pow(ab[2], 2));
    double[] uab = {ab[0] / modulo_ab, ab[1] / modulo_ab, ab[2] / modulo_ab};

    double modulo_ac = Math.sqrt(Math.pow(ac[0], 2) + Math.pow(ac[1], 2) + Math.pow(ac[2], 2));
    double[] uac = {ac[0] / modulo_ac, ac[1] / modulo_ac, ac[2] / modulo_ac};

    double angulo = Math.toDegrees(Math.acos(
            ((ab[0] * ac[0]) + (ab[1] * ac[1]) + (ab[2] * ac[2])) / (modulo_ab * modulo_ac)
    ));

    double i = (ab[1] * ac[2]) - (ab[2] * ac[1]);
    double j = -((ab[0] * ac[2]) - (ab[2] * ac[0]));
    double k = (ab[0] * ac[1]) - (ab[1] * ac[0]);

    double modulo_produto_vetorial = Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2) + Math.pow(k, 2));
    double area_do_triangulo = modulo_produto_vetorial / 2;

    // --- Tabela de vetores e módulos ---
    String[] colunas_vetores = {"Vetor", "Componentes", "Vetor Unitario", "Modulo"};
    int[] larguras_vetores = {6, 14, 28, 12};
    
    Object[][] dados_vetores = {
        {"AB", String.format("(%.0f, %.0f, %.0f)", ab[0], ab[1], ab[2]), 
         String.format("(%.4f, %.4f, %.4f)", uab[0], uab[1], uab[2]), 
         String.format("%.4f", modulo_ab)},
        {"AC", String.format("(%.0f, %.0f, %.0f)", ac[0], ac[1], ac[2]), 
         String.format("(%.4f, %.4f, %.4f)", uac[0], uac[1], uac[2]), 
         String.format("%.4f", modulo_ac)}
    };
    
    FormatadorTabela.imprimir_tabela_completa(colunas_vetores, dados_vetores, larguras_vetores);

    // --- Tabela de ângulo ---
    IO.println();
    String[] colunas_angulo = {"AB · AC", "|AB| × |AC|", "cos(θ)", "θ (graus)"};
    int[] larguras_angulo = {20, 20, 20, 15};
    
    double produto_escalar = (ab[0] * ac[0]) + (ab[1] * ac[1]) + (ab[2] * ac[2]);
    Object[][] dados_angulo = {{
        String.format("%.4f", produto_escalar),
        String.format("%.4f", modulo_ab * modulo_ac),
        String.format("%.4f", produto_escalar / (modulo_ab * modulo_ac)),
        String.format("%.4f", angulo)
    }};
    
    FormatadorTabela.imprimir_tabela_completa(colunas_angulo, dados_angulo, larguras_angulo);

    // --- Tabela do produto vetorial e área ---
    IO.println();
    String[] colunas_produto = {"AB × AC", "|AB × AC|", "Área (m²)"};
    int[] larguras_produto = {25, 20, 15};
    
    Object[][] dados_produto = {{
        String.format("(%.0f, %.0f, %.0f)", i, j, k),
        String.format("%.4f", modulo_produto_vetorial),
        String.format("%.4f", area_do_triangulo)
    }};
    
    FormatadorTabela.imprimir_tabela_completa(colunas_produto, dados_produto, larguras_produto);
}