package SistemaSteamLoja;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        // Carregar dados persistidos
        ArrayList<Usuario> usuarios = Persistencia.carregarUsuarios();
        ArrayList<Jogo> jogos = Persistencia.carregarJogos();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (usuarioLogado == null) {
                // Menu inicial
                System.out.println("\n=== Sistema Steam Simulado ===");
                System.out.println("1. Cadastrar Usuário");
                System.out.println("2. Fazer Login");
                System.out.println("3. Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do usuário: ");
                        String nomeUsuario = scanner.nextLine();

                        System.out.print("Digite o email: ");
                        String email = scanner.nextLine();

                        System.out.print("Digite a senha: ");
                        String senha = scanner.nextLine();

                        System.out.print("Digite a idade: ");
                        int idade = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Esse usuário será administrador? (s/n): ");
                        String respostaAdmin = scanner.nextLine();
                        boolean isAdministrador = respostaAdmin.equalsIgnoreCase("s");
                        
                        usuarios.add(new Usuario(nomeUsuario, email, senha, idade, isAdministrador));
                        
                    break;

                    case 2:
                        System.out.print("Digite seu email: ");
                        String emailLogin = scanner.nextLine();

                        System.out.print("Digite sua senha: ");
                        String senhaLogin = scanner.nextLine();

                        boolean encontrado = false;
                        for (Usuario u : usuarios) {
                            if (u.verificarCredenciais(emailLogin, senhaLogin)) {
                                usuarioLogado = u;
                                System.out.println("Login bem-sucedido! Bem-vindo, " + u.getNome() + "!");
                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            System.out.println("Credenciais inválidas. Tente novamente.");
                        }
                    break;

                    case 3:
                        // Salvar dados antes de encerrar
                        Persistencia.salvarUsuarios(usuarios);
                        Persistencia.salvarJogos(jogos);
                        System.out.println("Encerrando o sistema...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Opção inválida.");
                }
            } else {
                // Menu do usuário logado
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1. Cadastrar Jogo");
                System.out.println("2. Listar Jogos Disponíveis");
                System.out.println("3. Comprar Jogo");
                System.out.println("4. Ver Minha Biblioteca");
                System.out.println("5. Recarga de Saldo");
                System.out.println("6. Buscar Jogos");
                System.out.println("7. Atualizar Perfil");
                System.out.println("8. Adicionar Amigo");
                System.out.println("9. Listar Amigos");
                System.out.println("10. Enviar Mensagem");
                System.out.println("11. Visualizar Mensagens");
                System.out.println("12. Exibir Estatísticas");
                System.out.println("13. Exibir Conquistas");
                System.out.println("14. Visualizar Lista de Desejos");
                System.out.println("15. Desconectar");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do jogo: ");
                        String nomeJogo = scanner.nextLine();

                        System.out.print("Digite a descrição do jogo: ");
                        String descricao = scanner.nextLine();

                        System.out.print("Digite o preço: ");
                        double preco = scanner.nextDouble();
                        scanner.nextLine();

                        System.out.print("Digite a categoria (ex.: RPG, Ação, Estratégia): ");
                        String categoria = scanner.nextLine();

                        jogos.add(new Jogo(nomeJogo, descricao, preco, categoria));
                        System.out.println("Jogo cadastrado com sucesso!");
                        break;

                    case 2:
                        if (jogos.isEmpty()) {
                            System.out.println("Nenhum jogo disponível.");
                        } else {
                            System.out.println("\n=== Lista de Jogos ===");
                            for (Jogo jogo : jogos) {
                                jogo.exibirDetalhes();
                                System.out.println("------------------");
                            }
                        }
                        break;

                    case 3:
                        if (jogos.isEmpty()) {
                            System.out.println("Nenhum jogo disponível para compra.");
                        } else {
                            System.out.println("\n=== Lista de Jogos Disponíveis ===");
                            for (int i = 0; i < jogos.size(); i++) {
                                System.out.println((i + 1) + ". " + jogos.get(i).getNome() + " - R$ " + jogos.get(i).getPreco());
                            }

                            System.out.print("Digite o número do jogo que deseja comprar: ");
                            int escolha = scanner.nextInt() - 1;

                            if (escolha >= 0 && escolha < jogos.size()) {
                                Jogo jogoEscolhido = jogos.get(escolha);
                                if (usuarioLogado.getSaldo() >= jogoEscolhido.getPreco()) {
                                    usuarioLogado.adicionarJogo(jogoEscolhido);
                                    usuarioLogado.reduzirSaldo(jogoEscolhido.getPreco());
                                    System.out.println("Compra realizada com sucesso! " + jogoEscolhido.getNome() + " foi adicionado à sua biblioteca.");
                                } else {
                                    System.out.println("Saldo insuficiente.");
                                }
                            } else {
                                System.out.println("Opção inválida.");
                            }
                        }
                        break;

                    case 4:
                        usuarioLogado.exibirBiblioteca();
                        break;

                    case 5:
                        System.out.print("Digite o valor para recarga: ");
                        double valorRecarga = scanner.nextDouble();
                        usuarioLogado.adicionarSaldo(valorRecarga);
                        System.out.println("Saldo recarregado com sucesso!");
                        break;

                    case 6:
                        System.out.println("\n=== Busca por Jogos ===");
                        System.out.println("1. Buscar por Nome");
                        System.out.println("2. Buscar por Categoria");
                        System.out.print("Escolha uma opção: ");
                        int opcaoBusca = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoBusca) {
                            case 1:
                                System.out.print("Digite o nome do jogo: ");
                                String nomeBusca = scanner.nextLine();
                                boolean encontradoNome = false;
                                for (Jogo jogo : jogos) {
                                    if (jogo.getNome().equalsIgnoreCase(nomeBusca)) {
                                        jogo.exibirDetalhes();
                                        encontradoNome = true;
                                    }
                                }
                                if (!encontradoNome) {
                                    System.out.println("Nenhum jogo encontrado com esse nome.");
                                }
                                break;

                            case 2:
                                System.out.print("Digite a categoria: ");
                                String categoriaBusca = scanner.nextLine();
                                boolean encontradoCategoria = false;
                                for (Jogo jogo : jogos) {
                                    if (jogo.getCategoria().equalsIgnoreCase(categoriaBusca)) {
                                        jogo.exibirDetalhes();
                                        encontradoCategoria = true;
                                    }
                                }
                                if (!encontradoCategoria) {
                                    System.out.println("Nenhum jogo encontrado com essa categoria.");
                                }
                                break;
                        }
                        break;

                    case 7: 
                        System.out.println("\n=== Atualizar Perfil ===");
                        System.out.print("Digite o novo nome(Ou deixe em branco para manter): ");
                        String novoNome = scanner.nextLine();

                        System.out.print("Digite o novo email(ou deixe em branco para manter):");
                        String novoEmail = scanner.nextLine();

                        System.out.print("Digite a nova senha(ou deixe em branco para manter):");
                        String novaSenha = scanner.nextLine();

                        usuarioLogado.atualizarDados(
                            novoNome.isEmpty() ? null : novoNome, 
                            novoEmail.isEmpty() ? null : novoEmail,
                            novaSenha.isEmpty() ? null : novaSenha
                            );
                    break;

                    case 8:
                        System.out.println("\n=== Adicionar Amigo ===");
                        System.out.print("Digite o nome do usuário que deseja adicionar:");
                        String nomeAmigo = scanner.nextLine();
                        Usuario amigoEncontrado = null;
                        for (Usuario usuario : usuarios) {
                            if (usuario.getNome().equalsIgnoreCase(nomeAmigo)) {
                                amigoEncontrado = usuario;
                                break;
                            }
                        }
                        if (amigoEncontrado != null) {
                            usuarioLogado.adicionarAmigo(amigoEncontrado);
                        } else {
                            System.out.println("Usuário não encontrado.");
                        }
                    break;

                    case 9:
                        usuarioLogado.listarAmigos();
                    break;

                    case 10:
                        System.out.println("\n=== Enviar Mensagem ===");
                        System.out.print("Digite o nome do amigo:");
                        String nomeDestinatario = scanner.nextLine();
                        Usuario destinatario = null;
                        for (Usuario amigo : usuarioLogado.getAmigos()) {
                            if (amigo.getNome().equalsIgnoreCase(nomeDestinatario)) {
                                destinatario = amigo;
                                break;
                            }
                        }
                        
                        if (destinatario != null) {
                            System.out.print("Digite a mensagem:");
                            String mensagem = scanner.nextLine();
                            usuarioLogado.enviarMensagem(destinatario, mensagem);
                        } else {
                            System.out.println("Amigo não encontrado.");
                        }
                    break;

                    case 11:
                        System.out.println("\n=== Visualizar Mensagens ===");
                        System.out.print("Digite o nome do amigo: ");
                        String nomeAmigoMensagem = scanner.nextLine();
                        Usuario amigoMensagens = null;
                        
                        for (Usuario amigo : usuarioLogado.getAmigos()) {
                            if (amigo.getNome().equalsIgnoreCase(nomeAmigoMensagem)) { // Utilizando nomeAmigoMensagem
                                amigoMensagens = amigo;
                                break;
                            }
                        }
                        
                        if (amigoMensagens != null) {
                            usuarioLogado.visualizarMensagens(amigoMensagens);
                        } else {
                            System.out.println("Amigo não encontrado.");
                        }
                    break;
                    
                    case 12:
                        usuarioLogado.exibirEstatisticas();
                    break;

                    case 13:
                        usuarioLogado.exibirConquistas();
                    break;

                    case 14: // Visualizar Wishlist
                        usuarioLogado.visualizarListaDesejos();
                        System.out.print("Deseja comprar um jogo da wishlist? (s/n): ");
                        String respostaWishlist = scanner.nextLine();

                        if (respostaWishlist.equalsIgnoreCase("s")) {
                            System.out.print("Digite o nome do jogo: ");
                            String nomeJogoWishlist = scanner.nextLine();

                            Jogo jogoDesejado = null;
                            for (Jogo jogo : jogos) {
                                if (jogo.getNome().equalsIgnoreCase(nomeJogoWishlist)) {
                                    jogoDesejado = jogo;
                                    break;
                                }
                            }

                            if (jogoDesejado != null) {
                                usuarioLogado.comprarListaDesejos(jogoDesejado);
                            } else {
                                System.out.println("Jogo não encontrado.");
                            }
                        }
                    break;
 
                    case 15:
                        System.out.println("Desconectando " + usuarioLogado.getNome() + "...");
                        usuarioLogado = null;
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            }
        }
    }
}
