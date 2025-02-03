package SistemaSteamLoja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Usuario {
    private boolean isAdministrador; // Define se o usuário é administrador.
    private String nome;
    private String email;
    private String senha;
    private int idade;
    private double saldo;
    private double totalGasto;
    private ArrayList<Jogo> biblioteca;
    private ArrayList<Jogo> listaDesejos = new ArrayList<>();
    private ArrayList<Usuario> amigos;
    private HashMap<Usuario, ArrayList<String>> mensagens;
    private ArrayList<Conquistas> conquistas;

    public Usuario(String nome, String email, String senha, int idade, boolean isAdministrador) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.saldo = 0.0; // Saldo inicial padrão
        this.totalGasto = 0.0;
        this.biblioteca = new ArrayList<>();
        this.amigos = new ArrayList<>();
        this.mensagens = new HashMap<>();
        this.conquistas = new ArrayList<>();
        this.isAdministrador = isAdministrador;
        inicializarConquistas();
    }

    public boolean isAdministrador() {
        return this.isAdministrador;
    }

    private void inicializarConquistas() {
        conquistas.add(new Conquistas("Primeiro Jogo Comprado", "Compre seu primeiro jogo."));
        conquistas.add(new Conquistas("Biblioteca com 10 jogos", "Adicione 10 jogos à sua biblioteca."));
        conquistas.add(new Conquistas("Primeira avaliação de jogo", "Avalie um jogo pela primeira vez."));
    }

    public void verificarConquistas() {
        for (Conquistas conquista : conquistas) {
            if (!conquista.isConquistada()) {
                if (conquista.getNome().equals("Primeiro Jogo Comprado") && biblioteca.size() >= 1) {
                    conquista.conquistar();
                    System.out.println("Parabéns! Você desbloqueou a conquista: " + conquista.getNome());
                }
                if (conquista.getNome().equals("Biblioteca com 10 jogos") && biblioteca.size() >= 10) {
                    conquista.conquistar();
                    System.out.println("Parabéns! Você desbloqueou a conquista: " + conquista.getNome());
                }
            }
        }
    }

    public void exibirConquistas() {
        System.out.println("\n=== Conquistas ===");
        for (Conquistas conquista : conquistas) {
            String status = conquista.isConquistada() ? "[Conquistada]" : "[Não Conquistada]";
            System.out.println(status + " " + conquista.getNome() + ": " + conquista.getDescricao());
        }
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void adicionarSaldo(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Saldo adicionado com sucesso! Saldo atual: R$" + saldo);
        } else {
            System.out.println("Valor inválido.");
        }
    }

    public void reduzirSaldo(double valor) {
        saldo -= valor;
    }

    public void adicionarJogo(Jogo jogo) {
        biblioteca.add(jogo);
        totalGasto += jogo.getPreco();
        verificarConquistas();
    }

    public void adicionarAmigo(Usuario amigo) {
        if (!amigos.contains(amigo)) {
            amigos.add(amigo);
            mensagens.put(amigo, new ArrayList<>());
            System.out.println(amigo.getNome() + " foi adicionado como amigo!");
        } else {
            System.out.println("Esse usuário já é seu amigo.");
        }
    }

    public void listarAmigos() {
        if (amigos.isEmpty()) {
            System.out.println("Você ainda não tem amigos.");
        } else {
            System.out.println("\n=== Lista de Amigos ===");
            for (Usuario amigo : amigos) {
                System.out.println("- " + amigo.getNome());
            }
        }
    }

    public void visualizarMensagens(Usuario amigo) {
        if (amigos.contains(amigo)) {
            System.out.println("\n=== Mensagens com " + amigo.getNome() + " ===");
            ArrayList<String> conversas = mensagens.get(amigo);
            if (conversas.isEmpty()) {
                System.out.println("Nenhuma mensagem.");
            } else {
                for (String mensagem : conversas) {
                    System.out.println("- " + mensagem);
                }
            }
        } else {
            System.out.println("Esse usuário não está na sua lista de amizades.");
        }
    }

    public void exibirBiblioteca() {
        if (biblioteca.isEmpty()) {
            System.out.println("Sua biblioteca está vazia.");
        } else {
            System.out.println("\n=== Minha Biblioteca ===");
            for (Jogo jogo : biblioteca) {
                jogo.exibirDetalhes();
                System.out.println("- " + jogo.getNome());
            }
        }
    }

    public void atualizarDados(String novoNome, String novoEmail, String novaSenha) {
        if (novoNome != null && novoNome.length() >= 3) {
            this.nome = novoNome;
        }
        if (novoEmail != null && novoEmail.contains("@")) {
            this.email = novoEmail;
        }
        if (novaSenha != null && novaSenha.length() >= 6) {
            this.senha = novaSenha;
        }
        System.out.println("Dados atualizados com sucesso!");
    }

    public void exibirEstatisticas() {
        System.out.println("\n=== Estatísticas do Usuário ===");
        System.out.println("Nome: " + nome);
        System.out.println("Total gasto em jogos: R$" + totalGasto);
        System.out.println("Número de jogos na biblioteca: " + biblioteca.size());
    }

    public boolean verificarCredenciais(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    public ArrayList<Usuario> getAmigos() {
        return amigos;
    }

    public void adicionarListaDesejos(Jogo jogo) {
        if (!listaDesejos.contains(jogo)) {
            listaDesejos.add(jogo);
            System.out.println(jogo.getNome() + " foi adicionado à sua lista de desejos.");
        } else {
            System.out.println("Este jogo já está na sua lista de desejos.");
        }
    }

    public void visualizarListaDesejos() {
        if (listaDesejos.isEmpty()) {
            System.out.println("Sua lista de desejos está vazia.");
        } else {
            System.out.println("\n=== Minha lista de desejos ===");
            for (Jogo jogo : listaDesejos) {
                jogo.exibirDetalhes();
            }
        }
    }

    public void exibirSugestoes(ArrayList<Jogo> jogos) {
        System.out.println("Baseado nos jogos da sua biblioteca e lista de desejos, você pode gostar de:");
        for (Jogo jogo : jogos) {
            if (!biblioteca.contains(jogo) && !listaDesejos.contains(jogo)) {
                System.out.println("-" + jogo.getNome() + "(" + jogo.getCategoria() + ")");
            }
        }
    }

    public void comprarListaDesejos(Jogo jogo) {
        if (listaDesejos.contains(jogo)) {
            if (saldo >= jogo.getPreco()) {
                adicionarJogo(jogo);
                reduzirSaldo(jogo.getPreco());
                listaDesejos.remove(jogo);
                System.out.println("Jogo comprado e removido da lista de desejos: " + jogo.getNome());
            } else {
                System.out.println("Saldo insuficiente para comprar esse jogo.");
            }
        } else {
            System.out.println("Esse jogo não está na sua lista de desejos.");
        }
    }

    public void buscarJogosAvancado(ArrayList<Jogo> jogos, double precoMin, double precoMax, String categoria) {
        System.out.println("\n=== Resultados da Busca Avançada ===");
        for (Jogo jogo : jogos) {
            if (jogo.getPreco() >= precoMin && jogo.getPreco() <= precoMax && (categoria == null || jogo.getCategoria().equalsIgnoreCase(categoria))) {
                jogo.exibirDetalhes();
            }
        }
    }

    public void enviarMensagem(Usuario amigo, String mensagem) {
        if (amigos.contains(amigo)) {
            mensagens.get(amigo).add(mensagem);
            System.out.println("Mensagem enviada para " + amigo.getNome() + "!");
        } else {
            System.out.println("Esse usuário não está na sua lista de amigos.");
        }
    }
    
    // Método para mensagens ricas (com anexos)
    public void enviarMensagemRica(Usuario amigo, String mensagem, String anexo) {
        if (amigos.contains(amigo)) {
            String mensagemCompleta = mensagem + (anexo != null ? " [Anexo: " + anexo + "]" : "");
            mensagens.get(amigo).add(mensagemCompleta);
            System.out.println("Mensagem enviada para " + amigo.getNome() + "!");
        } else {
            System.out.println("Esse usuário não está na sua lista de amigos.");
        }
    }

    public void verificarAdministrador() {
        if (isAdministrador) {
            System.out.println(nome + "É um administrador.");
        } else {
            System.out.println(nome + "Não é um administrador.");
        }
    }
}
