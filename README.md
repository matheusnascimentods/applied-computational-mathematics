# Numerical Analysis - Dimensionamento Estrutural

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)

Este projeto foi desenvolvido como parte da avaliação **A3 da disciplina de Matemática Computacional**. O objetivo é aplicar métodos numéricos para resolver problemas reais de engenharia, focando no dimensionamento de uma passarela de pedestres metálica.

## 🚀 Sobre o Projeto

O sistema modela e resolve cinco desafios fundamentais no projeto estrutural:
1.  **Forças Internas:** Resolução de sistemas lineares para equilíbrio de nós.
2.  **Carga Crítica de Flambagem:** Localização de raízes para prever instabilidade.
3.  **Força do Vento:** Integração numérica para calcular a carga de pressão total.
4.  **Geometria Espacial:** Cálculo de vetores unitários, ângulos e áreas de faces.
5.  **Estimativa de Deflexão:** Interpolação polinomial para pontos sem sensores.

## 🛠️ Métodos Implementados

O software oferece múltiplas abordagens para comparação de precisão e eficiência:

*   **Sistemas Lineares:** 
    *   Método Direto: Eliminação de Gauss.
    *   Método Iterativo: Gauss-Seidel.
*   **Zeros de Funções:** Método da Bisseção.
*   **Integração Numérica:**
    *   Regra de Simpson 1/3.
    *   Regra dos Trapézios.
*   **Diferenciação Numérica:** Diferença Finita Centrada.
*   **Interpolação:** Polinômio de Lagrange.

## 💻 Como Executar

### Pré-requisitos
*   Java JDK 21 ou superior.
*   IDE de sua preferência (IntelliJ, VS Code, etc) ou terminal.

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/matheusnascimentods/numerical-analysis.git
   ```
2. Compile os arquivos:
   ```bash
   javac src/*.java -d out/
   ```
3. Execute o programa:
   ```bash
   java -cp out index
   ```

## 📄 Estrutura do Código
*   `src/index.java`: Ponto de entrada com menu interativo.
*   `src/parte1.java` a `src/parte5.java`: Implementação modular de cada desafio.
*   `Edital_A3.pdf`: Documentação com os requisitos detalhados e fórmulas matemáticas.

## ⚖️ Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).
