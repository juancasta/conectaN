Estructuras de datos utilizadas:
Lista de pilas para almacenar las fichas metidas en el tablero.
Grafo direccional no ponderado, uno para cada color.
Funcionamiento:

1. He separado dos tableros, uno para el rojo y otro para el amarillo.
2. Al ir metiendo cada ficha, la ficha la meto en el tablero general que es una lista de pilas (una por columna). A parte, la ficha irá a su su grafo particular.
3. Al insertar cada ficha, vuelvo a construir todos los enlaces o aristas entre las fichas (Vértices en el grafo), pues cada nueva ficha puede alterar las aristas de muchos vértices.
VÉRTICE: será una ficha.
ARISTA: enlace entre dos fichas, cuando éstas están juntas horizontal, vertical, o diagonalmente.
4. Posteriormente, para evaluar el ganador, he creado un algoritmo recursivo que hace una búsqueda en profundidad del grafo que recibe varios parámetros:
Condición de jugada: Horizontal, Vertical, Diagonal derecha, diagonal izquierda. Dependiendo de la condición, se ejecutará una validación en particular.
Ficha de partida, a partir de la cual iremos buscando sucesivas fichas que cumplan con la condición anteriormente dicha.
Lista ganadora, en la que vamos metiendo las fichas ganadoras.
5. Para la ficha que estamos tratando, obtenemos sus aristas, que nos conducen a vértices destino. Cuando se cumple la condición de jugada entre ficha origen y destino, entonces llamamos recursivamente al algoritmo, pasándole la ficha destino como ficha fuente y sumamos más 1 al score. De esta forma, cubro todas las fichas del grafo.

6. Finalmente, para mapear de mi lista de posiciones de la lista ganadora a un array de posiciones, he usado una función lambda stream mapper de Java 8.