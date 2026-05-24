public class parte2{
    public static void executar() {
        double a = 2.0;
        double b = 4.0;
        double tolerancia = 1e-7;

        double mAnterior = a;

        IO.println("Resolução com Método da Bisseção");
        IO.println(String.format("\n%-10s%-12s%-12s%-12s%-12s%-12s%-12s%-12s", "Iteração", "a", "m", "b", "f(a)", "f(m)", "f(b)", "e"));
        IO.println("-".repeat(94));

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

            IO.println(String.format("%-10d%-12.7f%-12.7f%-12.7f%-12.7f%-12.7f%-12.7f%-12s", i, a, m, b, fa, fm, fb, erro));

            if (convergiu) {
                IO.println(String.format("\nRaiz encontrada: %.7f (após %d iterações)", m, i));
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

    private static double funcao(double x) {
        return Math.pow(x, 3) - 5 * Math.pow(x, 2) + 8 * x - 5;
    }

}