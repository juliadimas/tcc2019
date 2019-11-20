package model;

/**
 * Created by feliz on 06/10/2019.
 */

public class Usuario {
    private String uid;
    private String nome;

    private String email;
    private String senha;
    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Usuario() {

    }

    public Usuario(String uid, String nome, String email, String senha) {
        this.uid = uid;
        this.nome = nome;

        this.email = email;
        this.senha = senha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
