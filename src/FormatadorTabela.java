public class FormatadorTabela {
    
    public static void imprimir_linha_separadora(int comprimento) {
        IO.println("-".repeat(comprimento));
    }
    
    public static void imprimir_cabecalho(String[] colunas, int[] larguras) {
        String formato = criar_formato_colunas(larguras);
        String cabecalho = String.format(formato, (Object[]) colunas);
        imprimir_linha_separadora(cabecalho.length());
        IO.println(cabecalho);
        imprimir_linha_separadora(cabecalho.length());
    }
    
    public static void imprimir_linha_dados(Object[] dados, int[] larguras) {
        String formato = criar_formato_colunas(larguras);
        IO.println(String.format(formato, dados));
    }
    
    public static void imprimir_rodape(int comprimento) {
        imprimir_linha_separadora(comprimento);
    }
    
    private static String criar_formato_colunas(int[] larguras) {
        StringBuilder formato = new StringBuilder();
        for (int largura : larguras) {
            formato.append("%-").append(largura).append("s ");
        }
        return formato.toString().trim();
    }
    
    public static void imprimir_tabela_completa(String[] colunas, Object[][] dados, int[] larguras) {
        String formato = criar_formato_colunas(larguras);
        String cabecalho = String.format(formato, (Object[]) colunas);
        
        imprimir_linha_separadora(cabecalho.length());
        IO.println(cabecalho);
        imprimir_linha_separadora(cabecalho.length());
        
        for (Object[] linha : dados) {
            IO.println(String.format(formato, linha));
        }
        
        imprimir_linha_separadora(cabecalho.length());
    }
}
