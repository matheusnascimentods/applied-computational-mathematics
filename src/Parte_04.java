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
    String h1 = String.format("%-6s %-14s %-28s %-12s", "Vetor", "Componentes", "Vetor Unitario", "Modulo");
    IO.println("-".repeat(h1.length()));
    IO.println(h1);
    IO.println("-".repeat(h1.length()));
    IO.println(String.format("%-6s %-14s %-28s %-12s",
            "AB",
            String.format("(%.0f, %.0f, %.0f)", ab[0], ab[1], ab[2]),
            String.format("(%.4f, %.4f, %.4f)", uab[0], uab[1], uab[2]),
            String.format("%.4f", modulo_ab)
    ));
    IO.println(String.format("%-6s %-14s %-28s %-12s",
            "AC",
            String.format("(%.0f, %.0f, %.0f)", ac[0], ac[1], ac[2]),
            String.format("(%.4f, %.4f, %.4f)", uac[0], uac[1], uac[2]),
            String.format("%.4f", modulo_ac)
    ));
    IO.println("-".repeat(h1.length()));

    // --- Tabela de ângulo ---
    String h2 = String.format("%-20s %-20s %-20s %-15s", "AB · AC", "|AB| × |AC|", "cos(θ)", "θ (graus)");
    IO.println("\n" + "-".repeat(h2.length()));
    IO.println(h2);
    IO.println("-".repeat(h2.length()));
    double produto_escalar = (ab[0] * ac[0]) + (ab[1] * ac[1]) + (ab[2] * ac[2]);
    IO.println(String.format("%-20.4f %-20.4f %-20.4f %-15.4f",
            produto_escalar,
            modulo_ab * modulo_ac,
            produto_escalar / (modulo_ab * modulo_ac),
            angulo
    ));
    IO.println("-".repeat(h2.length()));

    // --- Tabela do produto vetorial e área ---
    String h3 = String.format("%-25s %-20s %-15s", "AB × AC", "|AB × AC|", "Área (m²)");
    IO.println("\n" + "-".repeat(h3.length()));
    IO.println(h3);
    IO.println("-".repeat(h3.length()));
    IO.println(String.format("%-25s %-20.4f %-15.4f",
            String.format("(%.0f, %.0f, %.0f)", i, j, k),
            modulo_produto_vetorial,
            area_do_triangulo
    ));
    IO.println("-".repeat(h3.length()));
}