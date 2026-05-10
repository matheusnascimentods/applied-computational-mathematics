void main() {
    double a = 2.0;
    double b = 4.0;
    double tolerancia = 1e-7;

    double mAnterior = a;

    // Usando FormatadorTabela para melhorar a legibilidade e a experiência do usuário.
    String[] colunas = {"Iteração", "a", "m", "b", "f(a)", "f(m)", "f(b)", "e"};
    int[] larguras = {10, 12, 12, 12, 12, 12, 12, 12};
    
    FormatadorTabela.imprimir_cabecalho(colunas, larguras);

    for (int i = 1; true; i++) {
        double m = (a + b) / 2.0;
        double fa = funcao(a);
        double fm = funcao(m);
        double fb = funcao(b);

        String erro;
        boolean convergiu = false;
        double e = 0;

        if (i == 1) {
            erro = "N/A";
        } else {
            e = Math.abs(m - mAnterior);
            erro = String.format("%.7f", e);
            if (e < tolerancia) convergiu = true;
        }

        Object[] dados = {i, String.format("%.7f", a), String.format("%.7f", m), String.format("%.7f", b), 
                         String.format("%.7f", fa), String.format("%.7f", fm), String.format("%.7f", fb), erro};
        FormatadorTabela.imprimir_linha_dados(dados, larguras);

        if (convergiu) {
            int comprimento_linha = 10 + 7 * 12 + 7; // soma das larguras + espaços
            FormatadorTabela.imprimir_rodape(comprimento_linha);
            IO.println(String.format("Raiz encontrada: %.7f (após %d iterações)", m, i));
            break;
        }

        if (fa * fm < 0) {
            b = m;
        } else {
            a = m;
        }
        mAnterior = m;
    }
}

double funcao(double x) {
    return Math.pow(x, 3) - 5 * Math.pow(x, 2) + 8 * x - 5;
}
