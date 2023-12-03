package busca;

import java.util.Arrays;
import java.util.Random;

public class HillClimbing{
    // instância um objeto para gerar um valor aleatorio
    public int random()
    {
        //O contrutor Random cria um novo gerador de números
        Random aleatorio = new Random();
        //O método nextInt(8) gera um número uniformemente localizado entre 0 e 7
        int random = aleatorio.nextInt(8);
        return random;
    }
    // Gera o quadro e posiciona a rainha aleatoriamente
    public void gerarAleatoriamente(int[][] quadro, int[] estado)
    {
        //iteração através de índices de coluna.
        for (int i = 0; i < 8; i++)
        {
            // Obtenção de um índice de linha aleatória.
            estado[i] = random();

            // Coloca uma rainha na posição obtida no quadro.
            quadro[estado[i]][i] = 1;
        }
    }
    // Exibe o quadro
    public void exibirTabuleiroDeXadrez(int[][] quadro)
    {

        for (int i = 0; i < 8; i++)
        {
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
            for (int j = 0; j < 8; j++)
            {
                if (quadro[i][j] == 1)
                {
                    System.out.print("|  Q  ");
                }
                if (quadro[i][j] == 0)
                {
                    System.out.print("|     ");
                }
                if (quadro[i][j] == 7)
                {
                    System.out.print("|");
                }
            }
            System.out.print("\n");
        }
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
    }
    // Preenchendo a matriz “quadro” com os valores “valor”
    public void preencherTabuleiro(int[][] quadro, int valor)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                quadro[i][j] = valor;
            }
        }
    }
    // vamos calcular o valor objetivo do estado (as senhoras atacam umas às outras)
    // Função de avaliação heurística - o número de rainhas que atacam umas às outras.
    public int hDoParDeAtaque(int[][] quadro, int[] estado)
    {

        /*
        Para cada rainha em uma coluna, verificamos se alguma outra rainha cai na linha de nossa rainha atual e, se encontrarmos alguma, aumentamos a contagem da variável de ataque.
        */


        // Número de rainhas que atacam umas às outras,
        int atacando = 0;

        // O número de pares de rainhas que atacam entre si.
        int pares;

        // Variáveis que permitem indexar uma determinada linha e coluna na matriz.
        int linha;
        int coluna;

        for (int i = 0; i < 8; i++)
        {
        //Em cada coluna 'i', a rainha é colocada na linha 'estado[i]'

            //À esquerda da mesma linha
            linha = estado[i];
            coluna = i - 1;
            while (coluna >= 0 && quadro[linha][coluna] != 1)
            {
                coluna--;
            }
            if (coluna >= 0 && quadro[linha][coluna] == 1)
            {
                atacando++;
            }
            // À direita da mesma linha
            linha = estado[i];
            coluna = i + 1;
            while (coluna < 8 && quadro[linha][coluna] != 1)
            {
                coluna++;
            }
            if (coluna < 8 && linha >= 0 && quadro[linha][coluna] == 1)
            {
                atacando++;
            }
            //Diagonal para Nordeste
            linha = estado[i] - 1;
            coluna = i + 1;
            while (coluna < 8 && linha >= 0 && quadro[linha][coluna] != 1)
            {
                coluna++;
                linha--;
            }
            if (coluna < 8 && linha >= 0 && quadro[linha][coluna] == 1)
            {
                atacando++;
            }
            //Diagonal em direção Noroeste
            linha = estado[i] - 1;
            coluna = i - 1;
            while (coluna >= 0 && linha >= 0 && quadro[linha][coluna] != 1)
            {
                coluna--;
                linha--;
            }
            if (coluna >= 0 && linha >= 0 && quadro[linha][coluna] == 1)
            {
                atacando++;
            }
            //Diagonalmente em direção sudeste
            linha = estado[i] + 1;
            coluna = i + 1;
            while (coluna < 8 && linha < 8 && quadro[linha][coluna] != 1)
            {
                coluna++;
                linha++;
            }
            if (coluna < 8 && linha < 8 && quadro[linha][coluna] == 1)
            {
                atacando++;
            }
            //Diagonalmente em direção ao sudoeste
            linha = estado[i] + 1;
            coluna = i - 1;
            while (coluna >= 0 && linha < 8 && quadro[linha][coluna] != 1)
            {
                coluna--;
                linha++;
            }
            if (coluna >= 0 && linha < 8 && quadro[linha][coluna] == 1)
            {
                atacando++;
            }
        }
        pares = atacando / 2;
        return pares;
    }
    //gera uma configuração da matriz do tabuleiro com base no estado.
    public void gerandoTabuleiro(int[][] tabuleiro, int[] estado)
    {
        preencherTabuleiro(tabuleiro, 0);
        for (int i = 0; i < 8; i++){
            tabuleiro[estado[i]][i] = 1;
        }
    }
    //copia o conteúdo do estado 2 para o estado 1
    public void copia(int[] estado1, int[] estado2)
    {
        for (int i = 0; i < 8; i++)
        {
            estado1[i] = estado2[i];
        }
    }
    // Para obter o vizinho do estado atual com o menor valor objetivo entre todos os vizinhos, bem como o estado atual.
    public void getVizinho(int[][] tabuleiro, int[] estado)
    {
        // Declara e inicializa o tabuleiro e o estado ideal com o tabuleiro e o estado atual como ponto de partida.
        int[][] tabuleiroIdeal = new int[8][8];
        int[] estadoIdeal = new int[8];

        copia(estadoIdeal, estado);
        gerandoTabuleiro(tabuleiroIdeal, estadoIdeal);

        // Inicialização do valor ideal do objetivo
        int objetivoIdeal = hDoParDeAtaque(tabuleiroIdeal, estadoIdeal);

        int[][] quadroVizinho = new int[8][8];
        int[] estadoVizinho = new int[8];

        // inicializa array temporário e estado para fins de cálculo.
        copia(estadoVizinho, estado);
        gerandoTabuleiro(quadroVizinho, estadoVizinho);

        // Iteração entre todos os possíveis vizinhos da matriz (Board).
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++) {
                //Condição para pular o estado atual
                if (j != estado[i]) {

                    // Inicializa o vizinho temporário com o vizinho atual.
                    estadoVizinho[i] = j;
                    quadroVizinho[estadoVizinho[i]][i] = 1;
                    quadroVizinho[estado[i]][i] = 0;

                    // Cálculo do valor objetivo do vizinho.
                    int temp = hDoParDeAtaque(quadroVizinho, estadoVizinho);

                    /*
                      Compare os objetivos temporários e ótimos dos vizinhos e,
                      se os objetivos temporários forem inferiores aos objetivos ótimos,
                      atualize-os adequadamente.
                    */

                    if (temp <= objetivoIdeal){
                        objetivoIdeal = temp;
                        copia(estadoIdeal, estadoVizinho);
                        gerandoTabuleiro(tabuleiroIdeal, estadoIdeal);
                    }

                    //retorna à configuração base para a nova iteração.

                    quadroVizinho[estadoVizinho[i]][i] = 0;
                    estadoVizinho[i] = estado[i];
                    quadroVizinho[estado[i]][i] = 1;
                }
            }
        }

        copia(estado, estadoIdeal);
        preencherTabuleiro(tabuleiro, 0);
        gerandoTabuleiro(tabuleiro, estado);
    }
    public void hillClimbing(int[][] tabuleiro, int[] estado)
    {

        int[][] quadroVizinho = new int[8][8];
        int[] estadoVizinho = new int[8];

        // inicializa a matriz e o estado vizinhos com o tabuleiro e o estado da matriz atual
        copia(estadoVizinho, estado);
        gerandoTabuleiro(quadroVizinho, estadoVizinho);

        boolean Continue = true;

        do
        {

            /*
		    já que um vizinho se torna atual após o salto.
            copiamos o quadro matriz vizinho e o estado para
            o atual quadro matricial e estadual
			*/

            copia(estado, estadoVizinho);
            gerandoTabuleiro(tabuleiro, estado);

            // Recuperação do vizinho ideal

            getVizinho(quadroVizinho, estadoVizinho);

            // Condição verifica se os dois arrays são iguais
            if (Arrays.equals(estado, estadoVizinho)) {

                /*
				 Se o estado vizinho e o estado atual forem iguais,
                 então não há vizinho ideal e, consequentemente,
                 o resultado é exibido
				 */

                exibirTabuleiroDeXadrez(tabuleiro);

                // Muda Continue de verdadeiro para falso para sair do loop
                // O loop será interrompido
                Continue = false;
            }
            else if (hDoParDeAtaque(tabuleiro, estado) == hDoParDeAtaque(quadroVizinho, estadoVizinho))
            {
                estadoVizinho[random()] = random();
                gerandoTabuleiro(quadroVizinho, estadoVizinho);

                /*
                Se o vizinho e a corrente não forem iguais, mas seus objetivos forem iguais,
                então nos aproximamos check ou de um ótimo local. Em todos os casos, você terá que pular
                para um vizinho aleatório para escapar.
				*/
            }
        } while (Continue);
    }

}
