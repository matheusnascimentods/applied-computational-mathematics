# Resolução do Edital A3 — Matemática Computacional Aplicada

> Versão em Markdown do Relatório Técnico entregue na avaliação A3. Os nomes e RAs dos integrantes foram omitidos, e os trechos sobre a implementação foram atualizados para refletir o código atual do repositório. O enunciado completo está em [`notice.md`](notice.md).

**IDE utilizada:** IntelliJ IDEA

**Projeto escolhido:** Projeto 1 — Dimensionamento de uma Passarela de Pedestres (Versão A)

O grupo atua no projeto estrutural de uma passarela metálica sobre um canal de drenagem. O desafio envolve modelar forças internas em treliças, analisar flambagem, calcular forças de vento, geometria espacial e estimar deflexões.

## Visão Geral dos Métodos Utilizados

| Parte | Problema | Método(s) Escolhido(s) |
| :---: | :--- | :--- |
| 1 | Sistema linear (forças na treliça) | Eliminação de Gauss + Gauss-Seidel |
| 2 | Raiz de função polinomial (flambagem) | Método da Bisseção |
| 3 | Integral numérica (força do vento) e Taxa de Variação | Simpson 1/3 + Trapézios + Derivação Numérica |
| 4 | Geometria vetorial (nós da treliça) | Produto Escalar + Produto Vetorial |
| 5 | Interpolação (deflexão da viga) | Interpolação de Lagrange |

---

## Parte 1 — Forças Internas da Treliça (Sistemas Lineares)

### O que o edital pede

Encontrar as forças internas da treliça ($F_1, F_2, F_3, F_4$) através do sistema de equilíbrio de nós. Resolver utilizando um método numérico exato e um método iterativo, com erro tolerável de $10^{-5}$.

**Sistema da Versão A:**

$$
\begin{cases}
4F_1 - F_2 = 50 \\
-F_1 + 4F_2 - F_3 = 0 \\
-F_2 + 4F_3 - F_4 = 80 \\
-F_3 + 3F_4 = 0
\end{cases}
$$

### Métodos escolhidos

#### 1. Eliminação de Gauss (Método Exato)

**Por que esse método?**

- É um método direto (não iterativo), ou seja, chega na resposta exata em um número finito de operações.
- Funciona através do escalonamento da matriz aumentada até a forma triangular superior, seguido de substituição retroativa.
- Ideal para sistemas pequenos/médios onde se quer uma solução de referência para comparar com o método iterativo.
- É o método exato mais clássico e didático, perfeito para demonstrar o funcionamento passo a passo.

#### 2. Gauss-Seidel (Método Iterativo)

**Por que esse método e não Jacobi?**

- O Gauss-Seidel converge mais rápido que o método de Jacobi porque utiliza os valores já atualizados da iteração corrente (e não da anterior).
- O sistema é diagonalmente dominante ($|4| > |-1|$, $|4| > |-1| + |-1|$, etc.), o que garante a convergência do Gauss-Seidel.
- É mais eficiente em memória: não precisa manter dois vetores (atual e anterior) completos como no Jacobi.
- O critério de parada utilizado é o erro relativo entre iterações consecutivas para cada variável: $\dfrac{|F_{novo} - F_{antigo}|}{|F_{novo}|} < \text{tolerância}$.

### Como adaptamos para Java

```java
// Matriz aumentada [A|b] declarada como array 2D
double[][] matriz = {
    {4, -1, 0, 0, 50},
    {-1, 4, -1, 0, 0},
    {0, -1, 4, -1, 80},
    {0, 0, -1, 3, 0}
};
```

**Decisões de implementação e cálculo:**

- A matriz aumentada foi representada como `double[][]` — a última coluna é o vetor de termos independentes, evitando a necessidade de arrays separados.
- O escalonamento é feito com dois loops aninhados: o externo percorre as colunas-pivô e o interno as linhas abaixo do pivô.
- O programa exibe a matriz inicial e a matriz triangular superior obtida após o escalonamento.
- A substituição retroativa é feita com um laço que percorre as linhas de baixo para cima, então funciona para um sistema de qualquer tamanho $n$, e não só para as 4 variáveis do problema.
- No Gauss-Seidel, cada variável é recalculada sequencialmente dentro do mesmo loop, usando imediatamente os valores atualizados — essa é a essência do método e o que o diferencia do Jacobi. Partindo de $F = (0, 0, 0, 0)$, o método converge em 9 iterações.
- Resultado da Eliminação de Gauss: $F_1 = 14{,}967320$, $F_2 = 9{,}869281$, $F_3 = 24{,}509804$, $F_4 = 8{,}169935$. O Gauss-Seidel chega a $F_1 = 14{,}967309$, $F_2 = 9{,}869273$, $F_3 = 24{,}509800$, $F_4 = 8{,}169933$, dentro da tolerância de $10^{-5}$.
- O programa oferece um submenu para o usuário escolher qual resolução visualizar.

---

## Parte 2 — Carga Crítica de Flambagem (Raízes de Equações)

### O que o edital pede

Determinar a carga crítica de flambagem modelada por uma função polinomial. Obter a raiz com precisão de $10^{-7}$ no intervalo $[2, 4]$.

**Função da Versão A:**

$$f(x) = x^3 - 5x^2 + 8x - 5$$

### Método escolhido: Bisseção

**Por que Bisseção e não Newton-Raphson ou Secante?**

- A Bisseção é o método mais robusto e confiável para encontrar raízes: se a raiz existe no intervalo e a função é contínua, ele sempre converge.
- Não precisa calcular derivadas (como Newton-Raphson), simplificando a implementação.
- O edital já fornece o intervalo $[2, 4]$, o que é exatamente o input que a Bisseção precisa.
- Embora seja mais lento que Newton-Raphson (convergência linear vs. quadrática), a precisão de $10^{-7}$ é atingida em 25 iterações, o que é perfeitamente aceitável.
- É o método ideal quando se tem um intervalo definido e se quer garantia de convergência sem depender de uma boa estimativa inicial.

**Como funciona:**

1. Calcula o ponto médio $m = \dfrac{a + b}{2}$
2. Avalia $f(a)$ e $f(m)$
3. Se $f(a) \times f(m) < 0$, a raiz está em $[a, m]$; senão, está em $[m, b]$
4. Repete até o erro $|m_{atual} - m_{anterior}| < 10^{-7}$

### Como adaptamos para Java

```java
private static double funcao(double x) {
    return Math.pow(x, 3) - 5 * Math.pow(x, 2) + 8 * x - 5;
}
```

**Decisões de implementação e cálculo:**

- A função $f(x)$ foi encapsulada em um método separado (`funcao()`), facilitando alterações e testes.
- Utiliza `Math.pow()` para potências — nativo do Java, sem necessidade de bibliotecas externas.
- O critério de parada compara a diferença absoluta entre pontos médios consecutivos ($|m - m_{anterior}|$), não o valor de $f(m)$, garantindo precisão na localização da raiz.
- Na primeira iteração o erro é marcado como "N/A", pois não há ponto médio anterior.
- A saída é uma tabela formatada com `String.format()` mostrando $a$, $m$, $b$, $f(a)$, $f(m)$, $f(b)$ e o erro a cada iteração — essencial para o memorial de cálculo exigido pelo edital.
- Resultado: raiz $x \approx 2{,}7548776$, encontrada após 25 iterações.

---

## Parte 3 — Força Total do Vento (Integração e Derivação Numérica)

### O que o edital pede

Calcular a força total do vento integrando a pressão $w(z)$ ao longo da altura. Utilizar a Regra de Simpson 1/3 e a Regra dos Trapézios. Aplicar Derivação Numérica para encontrar a taxa de variação no ponto médio.

**Função da Versão A:**

$$w(z) = 0.1z^3 - 1.5z^2 + 10z + 20$$

**Limites de integração:** de $z = 0$ até $z = 15$ (altura total da estrutura).

### Métodos escolhidos

#### 1. Regra de Simpson 1/3

**Por que Simpson?**

- Tem precisão superior aos Trapézios: aproxima a curva por parábolas (polinômios de grau 2) em vez de retas.
- Para uma mesma quantidade de subintervalos, o erro do Simpson é de ordem $O(h^4)$ contra $O(h^2)$ dos Trapézios.
- O edital exige explicitamente a comparação entre os dois métodos, e o Simpson serve como a referência mais precisa.

**Decisões de implementação e cálculo:**

- Inicialmente, ao abstrair as informações do enunciado, havia 15 intervalos no total, o que seria insuficiente para a aplicação do método de Simpson 1/3, tendo em vista que há a exigência da quantidade de intervalos ser par. Por isso, foi necessário aumentar a quantidade de intervalos para 30, o que, consequentemente, diminuiu o valor do passo ($h$) de 1 para 0,5. E assim se tornou possível a aplicação do método.
- Aplicando a função da pressão do vento $w(z) = 0{,}1z^3 - 1{,}5z^2 + 10z + 20$ nos pontos de altura da torre, obtivemos os resultados de cada subintervalo, organizados na tabela abaixo:

| Índice | $z$ | $w(z)$ |
| :---: | :---: | :---: |
| 0 | 0 | 20 |
| 1 | 0,5 | 24,6375 |
| 2 | 1 | 28,6 |
| 3 | 1,5 | 31,9625 |
| 4 | 2 | 34,8 |
| 5 | 2,5 | 37,1875 |
| 6 | 3 | 39,2 |
| 7 | 3,5 | 40,9125 |
| 8 | 4 | 42,4 |
| 9 | 4,5 | 43,7375 |
| 10 | 5 | 45 |
| 11 | 5,5 | 46,2625 |
| 12 | 6 | 47,6 |
| 13 | 6,5 | 49,0875 |
| 14 | 7 | 50,8 |
| 15 | 7,5 | 52,8125 |
| 16 | 8 | 55,2 |
| 17 | 8,5 | 58,0375 |
| 18 | 9 | 61,4 |
| 19 | 9,5 | 65,3625 |
| 20 | 10 | 70 |
| 21 | 10,5 | 75,3875 |
| 22 | 11 | 81,6 |
| 23 | 11,5 | 88,7125 |
| 24 | 12 | 96,8 |
| 25 | 12,5 | 105,9375 |
| 26 | 13 | 116,2 |
| 27 | 13,5 | 127,6625 |
| 28 | 14 | 140,4 |
| 29 | 14,5 | 154,4875 |
| 30 | 15 | 170 |

- Vale ressaltar que, em funções cúbicas (polinômio de terceiro grau), o método de Simpson 1/3 é considerado exato, como neste contexto. Segue a resolução:

$$
\begin{aligned}
&\frac{h}{3} \left[ f(x_0) + f(x_n) + 4\left(f(x_1) + f(x_3) + \dots\right) + 2\left(f(x_2) + f(x_4) + \dots\right) \right] \\
&= \frac{0{,}5}{3} \left[ 20 + 170 + 4 \times 1002{,}1875 + 2 \times 910 \right] \\
&= \frac{0{,}5}{3} \left[ 4198{,}75 + 1820 \right] = \frac{0{,}5}{3} \times 6018{,}75 = 1003{,}125
\end{aligned}
$$

**Fórmula:**

$$\int \approx \frac{h}{3} \left[ f(x_0) + f(x_n) + 4\sum f(\text{ímpares}) + 2\sum f(\text{pares}) \right]$$

#### 2. Regra dos Trapézios

**Por que Trapézios?**

- Método mais simples de integração numérica, servindo como base de comparação com o Simpson.
- Aproxima a curva por segmentos de reta entre os pontos.
- Permite discutir a diferença de precisão entre os métodos.

**Fórmula:**

$$\int \approx \frac{h}{2} \left[ f(x_0) + f(x_n) + 2\sum f(\text{intermediários}) \right]$$

#### Como adaptamos para Java

```java
private static double w(double z) {
    return 0.1 * Math.pow(z, 3) - 1.5 * Math.pow(z, 2) + 10 * z + 20;
}
```

**Decisões de implementação e cálculo:**

- A função $w(z)$ foi separada em um método dedicado, reaproveitado por ambos os métodos de integração.
- Trapézios usa $h = 15/6 = 2{,}5$ com 6 intervalos, propositalmente com menos pontos para ilustrar a diferença de precisão.
- Diferente de Simpson 1/3, a Regra dos Trapézios não exige paridade na quantidade de intervalos. Aplicamos a função da pressão do vento com menos intervalos, o que faz com que o passo $h$ tenha o valor de 2,5, e obtivemos os valores de cada ponto descritos na tabela abaixo:

| Índice | $z$ | $w(z)$ |
| :---: | :---: | :---: |
| 0 | 0 | 20 |
| 1 | 2,5 | 37,1875 |
| 2 | 5 | 45 |
| 3 | 7,5 | 52,8125 |
| 4 | 10 | 70 |
| 5 | 12,5 | 105,9375 |
| 6 | 15 | 170 |

- Obtidos esses valores, aplica-se a fórmula dos trapézios para chegar ao resultado aproximado:

$$
\begin{aligned}
&\frac{h}{2} \left[ w_0 + 2\left(w_1 + w_2 + w_3 + \dots\right) + w_n \right] \\
&= \frac{2{,}5}{2} \left[ 20 + 2 \times 310{,}9375 + 170 \right] \\
&= \frac{2{,}5}{2} \left[ 20 + 621{,}875 + 170 \right] = \frac{2{,}5}{2} \times 811{,}875 \approx 1014{,}84375
\end{aligned}
$$

- Um submenu permite ao usuário escolher entre Simpson 1/3, Trapézios e Derivação Numérica.
- As tabelas exibem o índice, o valor de $z$ e $w(z)$ para cada subintervalo, permitindo acompanhamento passo a passo.

#### 3. Derivação Numérica

**Por que Derivação Numérica?**

- Permite encontrar a taxa de variação de uma função em um ponto sem derivar analiticamente.
- Usa valores da própria função em pontos vizinhos para aproximar a derivada.

**Por que a Diferença Centrada?**

- Entre as diferenças finitas (progressiva, regressiva e centrada), a centrada é a mais precisa: seu erro é de ordem $O(h^2)$, contra $O(h)$ das outras duas.
- Ela compensa os erros das diferenças progressiva e regressiva, traçando uma reta diretamente do ponto de trás até o ponto da frente. Como a distância entre esses dois pontos é $2h$, o divisor é dobrado.

**Fórmula:**

$$w'(z) \approx \frac{w(z + h) - w(z - h)}{2h}$$

**Decisões de implementação e cálculo:**

- Na função `derivacao_numerica()`, o algoritmo define dois valores fundamentais:
  - `z_medio = 7.5`: o ponto de interesse, ponto médio da torre de 15 m ($15/2 = 7{,}5$ metros).
  - `h = 0.5`: o "passo", ou incremento espacial. Ele determina a distância usada para avaliar a curva antes e depois do ponto alvo. Um valor pequeno de $h$ garante a precisão da estimativa; com 0,5, os pontos avaliados ficam próximos do ponto médio: 7,0 e 8,0.
- O programa calcula a função $w(z)$ um passo à frente e um passo atrás:
  - `w_frente`: $w(7{,}5 + 0{,}5) = w(8{,}0)$.
  - `w_tras`: $w(7{,}5 - 0{,}5) = w(7{,}0)$.

```java
double w_frente = w(z_medio + h);
double w_tras = w(z_medio - h);
double derivada = (w_frente - w_tras) / (2 * h);
```

- Aplicando esses valores na função $w(z) = 0{,}1z^3 - 1{,}5z^2 + 10z + 20$:

$$
\begin{aligned}
w(7{,}0) &= 0{,}1 \times 7^3 - 1{,}5 \times 7^2 + 10 \times 7 + 20 = 34{,}3 - 73{,}5 + 90 = 50{,}8 \\
w(8{,}0) &= 0{,}1 \times 8^3 - 1{,}5 \times 8^2 + 10 \times 8 + 20 = 51{,}2 - 96 + 80 + 20 = 55{,}2
\end{aligned}
$$

- **Resultado (Diferença Centrada):** $\dfrac{55{,}2 - 50{,}8}{2 \times 0{,}5} = 4{,}4$

**Comparação com as outras diferenças finitas (cálculo manual):** para avaliar a escolha, as diferenças progressiva e regressiva foram calculadas à mão com o mesmo $h$, usando também $w(7{,}5) = 0{,}1 \times 7{,}5^3 - 1{,}5 \times 7{,}5^2 + 10 \times 7{,}5 + 20 = 52{,}8125$. Elas não são calculadas pelo programa.

| Diferença | Fórmula | Resultado |
| :--- | :--- | :---: |
| Progressiva | $\dfrac{w(z+h) - w(z)}{h} = \dfrac{55{,}2 - 52{,}8125}{0{,}5}$ | $4{,}775$ |
| Regressiva | $\dfrac{w(z) - w(z-h)}{h} = \dfrac{52{,}8125 - 50{,}8}{0{,}5}$ | $4{,}025$ |
| **Centrada (implementada)** | $\dfrac{w(z+h) - w(z-h)}{2h} = \dfrac{55{,}2 - 50{,}8}{1}$ | $\mathbf{4{,}4}$ |

Como $w(z)$ é cúbica, a derivada exata em $z = 7{,}5$ é $w'(7{,}5) = 0{,}3 \times 7{,}5^2 - 3 \times 7{,}5 + 10 = 4{,}375$: a diferença centrada (4,4) é a que mais se aproxima.

---

## Parte 4 — Geometria Vetorial dos Nós da Treliça

### O que o edital pede

Dados os nós $A(0,0,0)$, $B(2,4,1)$, $C(4,0,2)$: calcular o vetor unitário de direção de cada barra, o ângulo de montagem via produto escalar e a área da face via produto vetorial.

Para a análise das barras da passarela, os vetores são definidos a partir da diferença entre as coordenadas dos nós:

- **Vetor AB:** $B - A = (2-0,\ 4-0,\ 1-0) = (2, 4, 1)$
- **Vetor AC:** $C - A = (4-0,\ 0-0,\ 2-0) = (4, 0, 2)$

O módulo é necessário para normalizar os vetores e calcular a relação angular:

- $|AB| = \sqrt{2^2 + 4^2 + 1^2} = \sqrt{21}$
- $|AC| = \sqrt{4^2 + 0^2 + 2^2} = \sqrt{20}$

**Fórmula:** $|V| = \sqrt{x^2 + y^2 + z^2}$

### Vetor unitário (direção da barra)

O vetor unitário isola a direção e o sentido da barra AB, sendo fundamental para a decomposição de forças:

$$u_{AB} = \frac{AB}{|AB|} = \frac{(2, 4, 1)}{\sqrt{21}} = \left( \frac{2}{\sqrt{21}},\ \frac{4}{\sqrt{21}},\ \frac{1}{\sqrt{21}} \right)$$

### Métodos escolhidos

#### 1. Produto Escalar (para o ângulo)

Utiliza-se o produto escalar para encontrar a abertura angular entre as barras AB e AC:

- **Produto escalar:** $(2 \cdot 4) + (4 \cdot 0) + (1 \cdot 2) = 8 + 0 + 2 = 10$
- **Cálculo do ângulo:** $\cos(\theta) = \dfrac{AB \cdot AC}{|AB| \cdot |AC|} = \dfrac{10}{\sqrt{21} \cdot \sqrt{20}}$
- **Resultado:** $\theta = \cos^{-1}(\dots) \approx 60{,}8°$

**Fórmula:** $AB \cdot AC = (x_1 \cdot x_2) + (y_1 \cdot y_2) + (z_1 \cdot z_2)$

**Por que o produto escalar?**

- É a forma mais direta de calcular o ângulo entre dois vetores: $\cos(\theta) = \dfrac{AB \cdot AC}{|AB| \times |AC|}$.
- Não é uma escolha — é o método matemático correto para esse tipo de problema. O edital pede explicitamente.

#### 2. Produto Vetorial (para a área)

Para determinar a área da face triangular da torre, aplica-se o produto vetorial:

- **Produto vetorial** $(AB \times AC)$: $(8, 0, -16)$
- **Módulo do produto vetorial:** $|(8, 0, -16)| = \sqrt{8^2 + 0^2 + (-16)^2} = \sqrt{320}$
- **Área da face:** $\text{Área} = \dfrac{|AB \times AC|}{2} = \dfrac{\sqrt{320}}{2} \approx 8{,}94$ u.a. (unidades de área)

**Por que o produto vetorial?**

- O módulo do produto vetorial $|AB \times AC|$ dá a área do paralelogramo formado pelos vetores. Dividindo por 2, obtemos a área do triângulo.
- Além disso, a direção do produto vetorial dá o vetor normal à superfície — informação essencial em engenharia estrutural.

### Como adaptamos para Java

```java
double ab[] = {(b[0] - a[0]), (b[1] - a[1]), (b[2] - a[2])};
double ac[] = {(c[0] - a[0]), (c[1] - a[1]), (c[2] - a[2])};
```

**Decisões de implementação e cálculo:**

- Vetores representados como `double[]` de 3 posições ($x$, $y$, $z$) — simples e direto, sem necessidade de classes de vetores.
- O módulo é calculado com `Math.sqrt(x² + y² + z²)` usando `Math.pow()`.
- O vetor unitário é obtido dividindo cada componente pelo módulo.
- O ângulo usa `Math.acos()` para o arco-cosseno e `Math.toDegrees()` para converter de radianos para graus.
- O produto vetorial é calculado componente a componente usando a regra do determinante: $i = AB_y \times AC_z - AB_z \times AC_y$, etc.
- A saída é organizada em 3 tabelas separadas:
  1. Vetores, componentes, unitários e módulos
  2. Dados do produto escalar e ângulo
  3. Produto vetorial e área

---

## Parte 5 — Deflexão da Viga (Interpolação Polinomial)

### O que o edital pede

Sensores mediram a deflexão vertical em pontos específicos. Utilizar interpolação polinomial para estimar a deflexão em $x = 3\,m$.

**Dados da Versão A:**

| Posição $x$ (m) | 0 | 2 | 4 | 6 |
| :--- | :---: | :---: | :---: | :---: |
| **Deflexão $y$ (mm)** | 0.0 | -1.2 | -2.5 | -1.8 |

### Método escolhido: Interpolação de Lagrange

**Por que Lagrange e não Newton ou Splines?**

- A Interpolação de Lagrange não precisa montar um sistema de equações nem calcular diferenças divididas — calcula o polinômio interpolador diretamente a partir dos pontos.
- Para 4 pontos (polinômio de grau 3), Lagrange é simples, eficiente e não tem overhead desnecessário.
- Produz o mesmo polinômio que Newton ou qualquer outro método de interpolação (o polinômio interpolador é único pelo Teorema da Unicidade).
- É mais intuitivo: cada "peso" $L_i(x)$ representa a contribuição do ponto $i$ para o valor interpolado.

**Fórmula:**

$$P(x) = \sum y_i \times L_i(x), \qquad L_i(x) = \prod_{j \neq i} \frac{x - x_j}{x_i - x_j}$$

### Cálculo

O objetivo do cálculo foi determinar o valor interpolado para $x = 3$. Inicialmente, foram calculados os polinômios base:

| Polinômio base | Expressão | Valor em $x = 3$ | Contribuição $y_i \times L_i(3)$ |
| :--- | :--- | :---: | :---: |
| $L_0(x)$ | $\dfrac{(x - 2)(x - 4)(x - 6)}{(0 - 2)(0 - 4)(0 - 6)}$ | $-0{,}0625$ | $0$ (pois $y_0 = 0$) |
| $L_1(x)$ | $\dfrac{(x - 0)(x - 4)(x - 6)}{(2 - 0)(2 - 4)(2 - 6)}$ | $0{,}5625$ | $(-1{,}2)(0{,}5625) = -0{,}675$ |
| $L_2(x)$ | $\dfrac{(x - 0)(x - 2)(x - 6)}{(4 - 0)(4 - 2)(4 - 6)}$ | $0{,}5625$ | $(-2{,}5)(0{,}5625) = -1{,}40625$ |
| $L_3(x)$ | $\dfrac{(x - 0)(x - 2)(x - 4)}{(6 - 0)(6 - 2)(6 - 4)}$ | $-0{,}0625$ | $(-1{,}8)(-0{,}0625) = 0{,}1125$ |

Por fim, realizou-se a soma de todos os termos:

$$P(3) = 0 + (-0{,}675) + (-1{,}40625) + 0{,}1125 \approx -1{,}97\ \text{mm}$$

Portanto, a deflexão estimada da viga principal no ponto de 3 m é aproximadamente **-1,97 mm** (o programa exibe $-1{,}9687$ mm), indicando deslocamento vertical descendente da estrutura.

### Como adaptamos para Java

```java
double[] pontosSensores = {0, 2, 4, 6};
double[] deflexoes = {0.0, -1.2, -2.5, -1.8};
```

**Decisões de implementação e cálculo:**

- Os pontos dos sensores e as deflexões são representados como arrays paralelos (`pontosSensores[]` e `deflexoes[]`).
- Para calcular $L_i(x)$, os outros pontos ($j \neq i$) são filtrados usando `Arrays.stream().filter().toArray()` — uma abordagem funcional do Java 8+ que evita loops complexos com condicionais.
- O numerador e o denominador de cada $L_i$ são calculados em um loop separado, multiplicando termo a termo.
- A saída mostra cada peso $L_i(x)$ individualmente, permitindo acompanhar o cálculo.
- O resultado final mostra a fórmula completa expandida: $P(3) = (y_0 \times L_0) + (y_1 \times L_1) + (y_2 \times L_2) + (y_3 \times L_3)$.

---

## Estrutura do Projeto em Java

### Arquitetura

```text
src/
├── index.java             → Menu principal (ponto de entrada)
├── parte1.java            → Sistemas lineares (Gauss + Gauss-Seidel)
├── parte2.java            → Raízes (Bisseção)
├── parte3.java            → Integração (Simpson + Trapézios) e derivação
├── parte4.java            → Álgebra vetorial
└── parte5.java            → Interpolação (Lagrange)
```

### Decisões Arquiteturais

| Decisão | Justificativa |
| :--- | :--- |
| Uma classe por parte | Separação de responsabilidades: cada arquivo resolve um problema independente |
| Método `executar()` estático | Permite chamada direta pelo menu sem instanciar objetos |
| Submenus nas Partes 1 e 3 | A Parte 1 compara dois métodos para o mesmo sistema e a Parte 3 reúne Simpson, Trapézios e derivação; o submenu facilita a demonstração |
| `String.format()` com `%-Ns` | Alinhamento à esquerda com largura fixa para tabelas legíveis no console |
| `IO.println()` | Classe `java.lang.IO` do Java 25, que simplifica a escrita no console sem `System.out` |
| Uso de `Math.pow()` e `Math.sqrt()` | Operações matemáticas nativas do Java — sem dependências externas |

### Adaptação ao Java — Pontos-Chave

1. **Sem bibliotecas externas:** todo o projeto usa apenas `java.util.Scanner`, `java.util.Arrays`, `java.lang.Math` e `java.lang.IO`. Isso demonstra que os métodos foram implementados "do zero", como o edital exige.
2. **Arrays nativos vs. classes:** optamos por `double[]` e `double[][]` em vez de criar classes `Vetor` ou `Matriz`. Para o escopo do projeto (sistemas 4×4, vetores 3D), a simplicidade é vantajosa.
3. **Streams API (Java 8+):** usada na Parte 5 para filtrar pontos de interpolação — demonstra conhecimento de Java moderno sem comprometer a legibilidade.
4. **Switch expressions (Java 14+):** o uso de `case ->` em vez de `case: break` torna o código mais limpo e evita fall-through acidental.
5. **Arquivo-fonte compacto e `main` de instância (Java 25):** o `index.java` usa `void main()` sem classe declarada, recurso moderno do Java que simplifica o ponto de entrada. Por isso, e pelo uso de `java.lang.IO`, o projeto exige o JDK 25.

## Resumo: Por que cada método?

| Parte | Método | Razão principal |
| :--- | :--- | :--- |
| 1 (Exato) | Eliminação de Gauss | Solução exata, referência para comparação |
| 1 (Iterativo) | Gauss-Seidel | Converge mais rápido que Jacobi; sistema é diagonalmente dominante |
| 2 | Bisseção | Garantia de convergência no intervalo dado; não precisa de derivada |
| 3 | Simpson 1/3 + Trapézios + Diferença Centrada | Exigência do edital; Simpson como referência, Trapézios para comparação e diferença centrada como derivada mais precisa |
| 4 | Produto Escalar + Vetorial | Métodos canônicos para ângulo entre vetores e área/normal de superfície |
| 5 | Lagrange | Cálculo direto sem sistemas de equações; ideal para poucos pontos |
