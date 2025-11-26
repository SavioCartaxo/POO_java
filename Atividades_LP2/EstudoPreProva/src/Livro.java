public class Livro {
    private String nome;
    private double preco;
    private int quantidade;
    private int ISBN;

    public Livro(String nome, double preco, int quantidade, int ISBN) {
        this.preco = preco;
        this.nome = nome;
        this.quantidade = quantidade;
        this.ISBN = ISBN;
    }

    // gets
    public String getNome() {return this.nome;}
    public double getPreco() {return this.preco;}
    public int getQuantidade() {return this.quantidade;}
    public int getISBN() {return this.ISBN;}

    // sets
    public void setNome(String nome) {this.nome = nome;}
    public void setPreco(double preco) {this.preco = preco;}
    public void setQuantidade(double preco) {this.preco = preco;}

    public boolean vendeLivro(int quantidade) {
        if (this.quantidade < quantidade) {
            return false;
        }

        this.quantidade -= quantidade;
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
