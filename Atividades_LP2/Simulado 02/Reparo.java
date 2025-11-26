public class Reparo {
    private String id;
    private String descricao;
    private double preco;

    public Reparo(String id, String descricao, double preco) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
    }

    // Essa porcentagem deve ser um double do AUMENTO percentual do preço, ex: 0,2 (que é 20%)
    public void reajustarPreco(double porcentagem) {
        double porcentagemPreco = this.preco * porcentagem;
        this.preco = this.preco + porcentagemPreco;
    }

    public String getId(){
        return this.id;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public double getPreco(){
        return this.preco;
    }

    @Override
    public boolean equals(Object O){
        if (O == null) return false;
        
        if (this.getClass() != O.getClass()) return false;
        
        Reparo ob = (Reparo) O;
        
        return (ob.getId() == this.getId());
    }
}
