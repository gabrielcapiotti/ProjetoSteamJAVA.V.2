package SistemaSteamLoja;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {
    // Método para verificar e criar arquivos vazios se necessário
    private static void verificarArquivo(String nomeArquivo) {
        File file = new File(nomeArquivo);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Arquivo criado: " + nomeArquivo);
            } catch (IOException e) {
                System.out.println("Erro ao criar " + nomeArquivo + ": " + e.getMessage());
            }
        }
    }

    // Salvar lista de usuários
    public static void salvarUsuarios(ArrayList<Usuario> usuarios) {
        verificarArquivo("usuarios.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"))) {
            oos.writeObject(usuarios);
            System.out.println("Usuários salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    // Carregar lista de usuários
    public static ArrayList<Usuario> carregarUsuarios() {
        verificarArquivo("usuarios.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("usuarios.dat"))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum usuário encontrado. Criando lista vazia.");
        }
        return new ArrayList<>();
    }

    // Salvar lista de jogos
    public static void salvarJogos(ArrayList<Jogo> jogos) {
        verificarArquivo("jogos.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("jogos.dat"))) {
            oos.writeObject(jogos);
            System.out.println("Jogos salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar jogos: " + e.getMessage());
        }
    }

    // Carregar lista de jogos
    public static ArrayList<Jogo> carregarJogos() {
        verificarArquivo("jogos.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("jogos.dat"))) {
            return (ArrayList<Jogo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum jogo encontrado. Criando lista vazia.");
        }
        return new ArrayList<>();
    }
}
