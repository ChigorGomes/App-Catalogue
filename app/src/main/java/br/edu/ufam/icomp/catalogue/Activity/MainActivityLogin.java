package br.edu.ufam.icomp.catalogue.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ufam.icomp.catalogue.Conexao.Conexao;
import br.edu.ufam.icomp.catalogue.R;
import br.edu.ufam.icomp.catalogue.Entidades.Usuario;

public class MainActivityLogin extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button entrar;
    private FirebaseAuth autenticacao;
    private Button cadastrar;
    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        email = (EditText) findViewById(R.id.editTextEmail);
        senha = (EditText) findViewById(R.id.editTextSenha);
        entrar = (Button) findViewById(R.id.buttonTelaLoginEntrar);
        cadastrar = (Button) findViewById(R.id.buttonTelaLoginCadastrar);

        eventosClick();
    }

     protected void onStart(){
        super.onStart();
        auth = Conexao.getFirebaseAuth();
     }

    private void eventosClick() {
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityLogin.this, MainActivityCadastroUsuario.class);
                startActivity(intent);
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInserido = email.getText().toString().trim();
                String senhaInserida = senha.getText().toString().trim();
                login(emailInserido, senhaInserida);
            }
        });
    }

    private void login(String email, String senha){
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivityLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(MainActivityLogin.this, MainActivityTelaPrincipal.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivityLogin.this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



/*
    public void verifica(View view){
       if (email.getText().toString().equals("")){
           Toast.makeText(this,"Preencha os campo e-mail", Toast.LENGTH_SHORT).show();


       }else if(senha.getText().toString().equals("")){
           Toast.makeText(this,"Preencha os campo senha", Toast.LENGTH_SHORT).show();

       }else{

       }
    }
*/

