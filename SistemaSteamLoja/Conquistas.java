package SistemaSteamLoja;

public class Conquistas {
    private String nome;
    private String descricao;
    private boolean conquistada;

    public Conquistas(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.conquistada = false;
    }
    public void conquistar() {
        this.conquistada = true;
    }
    public boolean isConquistada() {
        return conquistada;
    }
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
