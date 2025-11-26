import java.util.*;

public class RepareBem {
    
    private ArrayList<Reparo> listaReparo;
    private ArrayList<OS> listaOS;

    public RepareBem() {
        this.listaReparo = new ArrayList<Reparo>();
        this.listaOS = new ArrayList<OS>();
    }

    public void cadastrarReparo(String id, String descricao, double preco) {
        if (this.listaReparo.size() < 100)
            this.listaReparo.add(new Reparo(id, descricao, preco));
        else
            throw new IllegalArgumentException("ERRO"); 
        
    }

    public void reajustarPrecoReparo(String idReparo, double percentual) {
        for (int i = 0; i < this.listaReparo.size(); i++) {
            if (this.getIdReparo(i).equals(idReparo)) {
                this.listaReparo.get(i).reajustarPreco(percentual);
                return;
            }
        }

        throw new IllegalArgumentException("ERRO");
    }

    public void mudarStatusOrdemDeServico( int idOS, String status ) {
        if (idOS < listaOS.size()){
            this.listaOS.get(idOS - 1).setStatus(status);
        } else {
            throw new IllegalArgumentException("ERRO");
        }
    }

    public double obterValorOrdemServico(int idOS) {
        if (idOS < listaOS.size()){
            return this.listaOS.get(idOS - 1).getPreco();
        } else {
            throw new IllegalArgumentException("ERRO");
        }
    }

    public String listarOrdemDeServico() {
        String out = "";

        for (int i = 0; i < listaOS.size(); i++) {
            out += this.listaOS.get(i).toString();
            out += "\n";
        }

        return out;
    }

    public String listarOrdemDeServico(String status) {
        String out = "Ordens de Serviço - ";
        out += status + "\n";

        for (int i = 0; i < listaOS.size(); i++) {
            if (this.listaOS.get(i).getStatus().equals(status)) {    
                out += this.listaOS.get(i).toString();
                out += "\n";
            }
        }

        if (out.equals("Ordens de Serviço - " + status + "\n")) 
            out = "Não há ordens de serviço do tipo " + status;
        
        return out;
    }

    public int cadastrarOrdemDeServico(String cliente, String telefone, String roupa) {
        this.listaOS.add(new OS(cliente, telefone, roupa, this.listaOS.size() - 1));

        return this.listaOS.size();
    }

    public String exibirOrdemDeServico( int IDOS ) {
        if (IDOS < this.listaOS.size()) {
            return this.listaOS.get(IDOS - 1).toString();
        }
        throw new IllegalArgumentException("ERRO");
    }

    public void incluirReparoOrdemDeServico( int idOS, String idReparo ) {
        if (idOS <= this.listaOS.size()) {
            this.listaOS.get(idOS -1).addReparo(this.pegaReparoComOID(idReparo));
        }

        throw new IllegalArgumentException("ERRO");
    }

    private Reparo pegaReparoComOID(String idReparo) {
        for (int i = 0; i < this.listaReparo.size(); i++) {
            if (this.listaReparo.get(i).getId().equals(idReparo)) {
                return this.listaReparo.get(i);
            }
        }

        throw new IllegalArgumentException("ERRO");
    }

    private String getIdReparo(int posicao) {
        if (posicao < this.listaReparo.size()) {
            return this.listaReparo.get(posicao).getId();
        }

        throw new IllegalArgumentException("ERRO");
    }

}
