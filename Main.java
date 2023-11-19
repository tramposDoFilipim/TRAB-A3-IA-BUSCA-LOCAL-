import busca.HillClimbing;

public class Main {

        public static void main(String[] args){
            busca.HillClimbing buscaLocal = new HillClimbing();
                int[] estado = new int[8];
                int[][] tabuleiro = new int[8][8];
                // Gere uma situação inicial configurando a matriz do tabuleiro aleatoriamente.
                buscaLocal.gerarAleatoriamente(tabuleiro, estado);
                System.out.println("_________________________________________________");
                System.out.println("\t\t Configuração inicial");
                System.out.println("-------------------------------------------------");
                // exibição da matriz do tabuleiro gerado aleatoriamente
                buscaLocal.exibirTabuleiroDeXadrez(tabuleiro);

                // mostra a solução
                System.out.println("_________________________________________________");
                System.out.println("\t\t Configuração final");
                System.out.println("-------------------------------------------------");

                buscaLocal.hillClimbing(tabuleiro, estado);
        }


}
