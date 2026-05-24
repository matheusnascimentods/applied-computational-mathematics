import java.util.Arrays;

public class parte5 {
    public static void executar() {
        double xAlvo = 3;
        double[] pontosSensores = {0, 2, 4, 6};
        double[] deflexoes = {0.0, -1.2, -2.5, -1.8};
        double[] pesos = new double[pontosSensores.length];

        IO.println("Resolução com Interpolação de Lagrange");
        IO.println(String.format("\n%-10s%-12s%-16s",
                "i", "yi (mm)", "Li(x)"));
        IO.println("-".repeat(38));

        for (int i = 0; i < pontosSensores.length; i++) {
            double numerador = 1;
            double denominador = 1;
            double xiAtual = pontosSensores[i];
            double[] outrosPontos = Arrays.stream(pontosSensores)
                    .filter(ponto -> ponto != xiAtual)
                    .toArray();

            for (int j = 0; j < outrosPontos.length; j++) {
                numerador   *= (xAlvo   - outrosPontos[j]);
                denominador *= (xiAtual - outrosPontos[j]);
            }

            pesos[i] = numerador / denominador;

            IO.println(String.format("%-10d%-12.1f%-16.6f",
                    i, deflexoes[i], pesos[i]));
        }

        double deflexaoEstimada = 0;
        for (int i = 0; i < pontosSensores.length; i++) {
            deflexaoEstimada += deflexoes[i] * pesos[i];
        }

        IO.println(String.format("\nP(3) = (%.1f * %.4f) + (%.1f * %.4f) + (%.1f * %.4f) + (%.1f * %.4f) = %.4f mm",
                deflexoes[0], pesos[0], deflexoes[1], pesos[1], deflexoes[2], pesos[2], deflexoes[3], pesos[3], deflexaoEstimada));
        IO.println(String.format("\nDeflexão estimada em x = %.1fm: %.4f mm", xAlvo, deflexaoEstimada));
    }
}