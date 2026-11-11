# Edital A3 — Matemática Computacional Aplicada

> Versão em Markdown do edital da avaliação A3, reduzida ao que se refere ao projeto resolvido neste repositório: **Projeto 1 — Dimensionamento de uma Passarela de Pedestres (Versão A)**. Os demais projetos e a Versão B foram omitidos.

**Unidade Curricular:** Matemática Computacional Aplicada

---

## 1. Objetivo

O objetivo desta avaliação é aplicar os métodos estudados ao longo do semestre para resolver um problema real de engenharia/tecnologia, analisando a eficiência, convergência e precisão de diferentes abordagens computacionais.

## 2. Regras Gerais

- **O projeto deve ser realizado em grupos de até 5 estudantes.** Cada grupo deverá escolher um dos 6 projetos propostos e se irá resolver a **Versão A** ou a **Versão B** do projeto. **Cada grupo deverá resolver um projeto diferente.** Caso dois grupos escolham o mesmo projeto, deverão obrigatoriamente selecionar versões diferentes. **Grupos que entregarem a resolução do mesmo projeto e da mesma versão terão a nota dividida entre eles.**
- Recomenda-se o uso da linguagem de programação Java para as implementações. O uso do Excel ou até mesmo a resolução manual também é permitido, desde que o rigor matemático ao demonstrar a convergência passo a passo dos métodos seja mantido. **Não é permitida a utilização de outras linguagens de programação para a resolução.**
- **Originalidade:** os códigos e relatórios devem ser de autoria do grupo. O plágio entre grupos ou o uso de soluções prontas sem a devida análise crítica resultará na anulação da nota.

## 3. Entregáveis

Os grupos deverão entregar três componentes fundamentais:

1. **Relatório Técnico:** contendo a fundamentação teórica, scripts devidamente comentados ou memorial de cálculo (quando aplicável), e a análise crítica dos resultados (tabelas e gráficos de convergência).
2. **Apresentação em Sala:** o grupo deverá apresentar os resultados obtidos, explicando as tomadas de decisão, a eficiência dos algoritmos escolhidos e demonstrando o funcionamento dos códigos.
3. **Envio pelo ambiente Ulife:** tanto o Relatório Técnico quanto os slides da Apresentação em Sala devem ser enviados pelo Ulife. Cada membro do grupo deve realizar o envio individualmente. Alunos que não realizarem o envio dos documentos receberão nota zero.

---

## 4. Projeto 1: Dimensionamento de uma Passarela de Pedestres

O grupo atua no projeto estrutural de uma passarela metálica que será construída sobre um canal de drenagem de grande vão. Para garantir a segurança e a viabilidade da obra, será necessário modelar a distribuição das forças internas nas treliças de suporte, analisar os riscos de instabilidade por flambagem sob cargas críticas, calcular a força total exercida por ventos com perfis de pressão variáveis ao longo da altura, analisar a geometria espacial da estrutura e, por fim, estimar deflexões em pontos onde não é viável a instalação de sensores físicos.

### Parte 1 — Forças internas da treliça

Encontrar as forças internas da treliça ($F_1, F_2, F_3, F_4$) através do sistema de equilíbrio de nós. Resolva o sistema utilizando um método numérico exato e um método iterativo à sua escolha para fins de comparação, assumindo um erro tolerável de $10^{-5}$.

**Versão A:**

$$
\begin{cases}
4F_1 - F_2 = 50 \\
-F_1 + 4F_2 - F_3 = 0 \\
-F_2 + 4F_3 - F_4 = 80 \\
-F_3 + 3F_4 = 0
\end{cases}
$$

### Parte 2 — Carga crítica de flambagem

Determinar a carga crítica de flambagem para a estrutura, modelada através de uma função polinomial que descreve o perfil de instabilidade. Obtenha a raiz da função com precisão de $10^{-7}$.

**Versão A:**

$$f(x) = x^3 - 5x^2 + 8x - 5 = 0 \quad \text{(no intervalo } [2.0,\ 4.0]\text{)}$$

### Parte 3 — Força total do vento

Calcular a força total do vento $F = \int_0^H w(z)\,dz$, onde a pressão do vento $w(z)$ varia ao longo da altura $z$. Utilize a Regra de Simpson (1/3) e a Regra dos Trapézios, além de aplicar Derivação Numérica para encontrar a taxa de variação da pressão ($dw/dz$) no ponto médio da torre.

**Versão A:**

$$w(z) = 0.1z^3 - 1.5z^2 + 10z + 20 \quad \text{(com a altura total } H = 15\,m\text{)}$$

### Parte 4 — Geometria espacial

Dados os nós da treliça no espaço $\mathbb{R}^3$, calcular o vetor unitário de direção de cada barra, o ângulo de montagem utilizando o produto escalar e a área da face da torre através do produto vetorial.

**Versão A:** $A(0, 0, 0)$, $B(2, 4, 1)$, $C(4, 0, 2)$

### Parte 5 — Estimativa de deflexão

Sensores de deformação mediram a deflexão vertical ($y$, em milímetros) da viga principal em pontos específicos ($x$, em metros). Utilize um método de interpolação polinomial para estimar a deflexão num ponto intermediário onde não há sensor.

**Versão A:** estime a deflexão em $x = 3\,m$ a partir da seguinte tabela:

| Posição $x$ (m) | 0 | 2 | 4 | 6 |
| :--- | :---: | :---: | :---: | :---: |
| **Deflexão $y$ (mm)** | 0.0 | -1.2 | -2.5 | -1.8 |

---

## 5. Critérios de Avaliação

A nota final do projeto será atribuída a partir da combinação da avaliação do Relatório Escrito e da Apresentação em Sala. O foco será na qualidade da implementação, na fundamentação matemática e na clareza da exposição.

**É obrigatório que todos os membros do grupo participem ativamente da apresentação em sala.** A demonstração de domínio do conteúdo durante a apresentação afetará diretamente a avaliação.

### O que é esperado no Relatório Escrito

- Apresentação clara do memorial de cálculo, formulação correta das equações e verificação das condições matemáticas de convergência.
- Discussão sobre a escolha dos métodos numéricos para a resolução do problema.
- Estruturação lógica e técnica do documento. Deve incluir tabelas comparativas de resultados, gráficos, capturas de tela (prints) das execuções do programa e os códigos-fonte desenvolvidos (bem-organizados e comentados).

### O que é esperado na Apresentação em Sala

- Demonstração de como os dados foram inseridos e os resultados obtidos pelo grupo.
- Explicação clara das decisões tomadas. Todos os integrantes do grupo devem demonstrar domínio do assunto e estar preparados para responder as perguntas feitas pelo professor sobre qualquer etapa do projeto.
- Capacidade do grupo de sintetizar o desafio de engenharia/tecnologia e interpretar os resultados encontrados.
