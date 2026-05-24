void main () {
    int opcao;
    boolean continuar = true;

    while (continuar) {
        IO.println("Digite o número referente a opção que você deseja:");
        IO.println("1-Primeira parte");
        IO.println("2-Segunda parte");
        IO.println("3-Terceira parte");
        IO.println("4-Quarta parte");
        IO.println("5-Quinta parte");
        IO.println("6-Finalizar o programa");
        opcao = new Scanner(System.in).nextInt();

        switch (opcao) {
            case 1 -> parte1.executar();
            case 2 -> parte2.executar();
            case 3 -> parte3.executar();
            case 4 -> parte4.executar();
            case 5 -> parte5.executar();
            case 6 -> continuar = false;
            default -> IO.println("Opção invalída");
        }
    }
}