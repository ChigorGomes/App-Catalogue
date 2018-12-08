package br.edu.ufam.icomp.catalogue.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufam.icomp.catalogue.Conexao.ConfigFirebase;

public class Usuario {
    private String idUsuario;
    private String nome;
    private String email;
    private String sexo;
    private String idade;
    private String senha;

    public Usuario(){}

    /*Função de salvar usuário*/
    public void salvarUsuario(){
        DatabaseReference reference= ConfigFirebase.getFirebase();
        reference.child("usuario").child(String.valueOf(getIdUsuario())).setValue(this);
    }

    /*Cria uma lista e o banco de dados chamado usuário*/
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario= new HashMap<>();
        hashMapUsuario.put("idUsuario",getIdUsuario());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("sexo",getSexo());
        hashMapUsuario.put("idade",getIdade());
        hashMapUsuario.put("senha",getSenha());

        return  hashMapUsuario;
    }



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
