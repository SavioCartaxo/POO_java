import java.util.*;

public class RepoLivros {
    private Map<Integer, Livro> listaLivros;
    private int ISBN = 1;
    // Ultimos vendidos
    private static final int TAMANHO = 6;
    private LinkedList<Livro> ultimosLivrosVendidos;

    public RepoLivros() {
        this.listaLivros = new HashMap<>();
    }

    public void adicionaLivro(String nome, double preco, int quantidade){
        Livro livro = new Livro(nome, preco, quantidade, this.ISBN);
        this.listaLivros.put(this.ISBN,livro);
        this.ISBN++;
    }

    public boolean vendeLivro(int ISBN, int quantidade) {
        Livro livro = listaLivros.get(ISBN);

        if (livro.vendeLivro(quantidade)){
            this.ultimosLivrosVendidos.remove(livro);

            this.ultimosLivrosVendidos.addFirst(livro);

            if (this.ultimosLivrosVendidos.size() > TAMANHO) {
                this.ultimosLivrosVendidos.removeLast();
            }

            return true; // comprou o livro
        }

        return false; // nao tinha a quantidade disponivel no estoque
    }

}
