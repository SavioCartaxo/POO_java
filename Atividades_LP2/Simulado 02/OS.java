import java.util.*;

public class OS {
    private int ID;
    private String cliente;
    private String roupa;
    private String telefone;
    private ArrayList<Reparo> listaReparos;
    private String status;

    public OS(String cliente, String telefone, String roupa, int ID) {
        this.cliente = cliente;
        this.telefone = telefone;
        this.roupa = roupa;
        this.ID = ID;
        this.listaReparos = new ArrayList<Reparo>();
        this.status = "Não iniciado";
    }

    public void addReparo(Reparo reparo) {
        if (this.listaReparos.size() <= 10){
            this.listaReparos.add(reparo);
        }
        throw new IllegalArgumentException("ERRO");
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCliente() {
        return this.cliente;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getRoupa() {
        return this.roupa;
    }

    public ArrayList<Reparo> getListaReparos() {
        return this.listaReparos;
    }

    public int getID() {
        return this.ID;
    }

    public double getPreco() {
        double preco = 0;
        
        for (int i = 0; i < this.listaReparos.size(); i++) {
            preco += listaReparos.get(i).getPreco();
        }
        
        return preco;
    }

    @Override
    public String toString() {
        String out = "";

        out += "#" + this.ID;
        out += "; cliente: " + this.cliente;
        out += "; roupa: " + this.roupa;
        out += "; reparos: " + this.listaReparos.size();
        out += "; total: R$";
        out += this.getPreco();

        return out;
    }

    @Override
    public boolean equals(Object O) {
        if (O == null) 
            return false;

        if (O.getClass() != this.getClass()) 
            return false;

        OS obj = (OS) O;

        return (obj.getID() == this.getID());

    }
}
