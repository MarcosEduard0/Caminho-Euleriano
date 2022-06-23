# Caminho Euleriano

Implementaçãoem Java para encontrar um Circuito Euleriano e/ou Caminho Euleriano
A estratégia abordada foi o algoritmo de Fleury.

## Implementação da solução

A estratégia abordada foi o algoritmo de Fleury:

- Verificar se o grafo é conexo e se:
  a) Tem todos os vértices com grau par (grafo de Euler)
  b) Tem 2 vértices com grau ímpar.
- Escolher um vértice para começar e assinalá-lo, no caso (a) pode ser qualquer um, já no caso (b) terá de ser um dos vértices de grau ímpar.
- Percorrer qualquer uma das arestas incidentes nesse vértice desde que esta não seja uma aresta de corte (ponte) para a parte não atravessada do grafo, isto é, de modo que o grafo formado pelas arestas que ainda faltam percorrer (e respetivos vértices) se mantenha conexo ao removermos as arestas percorridas, só atravessando a aresta de corte caso não reste outra hipótese.
- Repetir o passo anterior a partir do vértice onde chegou até que não restem mais arestas a percorrer, no caso (a) terminará no vértice onde começou, já no caso (b) terminará no outro vértice de grau ímpar.
  A implementação foi feita em Java, onde no arquivo principal fica o menu para o usuário, com 4 opções, carregar o grafo de um arquivo .txt, imprimir o grafo na tela, executar o algoritmo de Fleury e sair. Quando escolhido o algoritmo de Fleury, o usuário precisará passar o número do vértice que ele deseja que a busca inicie.
  Agora falando sobre as funções importantes implementadas, temos a função getOddVerticesCount(┤) que é responsável por fazer a verificação da quantidade de vértices ímpares no grafo, com a verificação feita podemos analisar em qual caso iremos fazer, caso seja o (b) a função getEulerPathOrigin(┤) faz uma busca pelo grafo ate encontrar o primeiro vértice de grau ímpar. Caso seja o (a), já adicionaremos na lista o vértice v_0 que o usuário escolheu que inicialize a busca. Dentro da função FleutyAlgorithm(┤) , percorremos os vizinhos do v_0 e a cada interação verificamos se a aresta do vizinho que pretendemos percorrer é uma aresta de corte ou não, através da função isBridge(┤) que verificar se existe mais de uma possibilidade de aresta, caso existe, verificamos a quantidade de vértices conectados, fazemos a remoção a aresta e refazemos uma nova verificação da quantidade de vértices e colocamos a aresta novamente, caso a segunda verificação seja menor que a primeira, então tiramos uma aresta de corte. Ao retornar para FleutyAlgorithm(┤), caso não seja uma aresta de corte iremos adicionar o vértice na lista de caminho e agora removendo definitivamente essa aresta. Com isso a função entrar na recursividade fazendo uma buscar em profundidade. Ao terminar iremos imprimir a lista de caminho, começando pelo vértice definido pelo usuário e seguido pelos demais encontrados: v_0→v_1→v_2→⋯→v_0
