package SistemaSteamLoja;

public class Jogo {
    private String nome;
    private String descricao;
    private double preco;
    private String categoria;

    public Jogo(String nome, String descricao, double preco, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void exibirDetalhes() {
        System.out.println("\n=== Jogo ===");
        System.out.println("Nome: " + nome);
        System.out.println("Descrição: " + descricao);
        System.out.printf("Preço: R$ %.2f\n", preco);
        System.out.println("Categoria: " + categoria);
    }
}
