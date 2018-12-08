package br.edu.ufam.icomp.catalogue.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ufam.icomp.catalogue.Conexao.Conexao;
import br.edu.ufam.icomp.catalogue.Conexao.ConfigFirebase;
import br.edu.ufam.icomp.catalogue.Entidades.Usuario;
import br.edu.ufam.icomp.catalogue.Helper.Base64Custom;
import br.edu.ufam.icomp.catalogue.Helper.PreferenciasAndroid;
import br.edu.ufam.icomp.catalogue.R;

public class MainActivityCadastroUsuario extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private Spinner sexo;
    private EditText idade;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cadastro_usuario);

        nome = (EditText) findViewById(R.id.editTextNome);
        email = (EditText) findViewById(R.id.editTextEmail);
        sexo = (Spinner) findViewById(R.id.spinnerSexo);
        idade = (EditText) findViewById(R.id.editTextIdade);
        senha = (EditText) findViewById(R.id.editTextSenha);
        botaoCadastrar = (Button) findViewById(R.id.buttonTelaCadastroCadastrar);

        eventoClick();

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.escolherSexo, android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(arrayAdapter);
    }

    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    private void eventoClick() {
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailObtido = email.getText().toString().trim();
                String senhaObtida = senha.getText().toString().trim();
                criarUser(emailObtido, senhaObtida);
            }
        });
    }

    private void criarUser(String email, String senha){
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivityCadastroUsuario.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivityCadastroUsuario.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivityCadastroUsuario.this, MainActivityTelaPrincipal.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(MainActivityCadastroUsuario.this, "Erro no cadastro!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}



        /*botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = sexo.getSelectedItem().toString(); //string com o item
                int posicao = sexo.getSelectedItemPosition(); //posição

                if (nome.getText().toString().equals("")) {
                    Toast.makeText(MainActivityCadastroUsuario.this, "Preencha o campo nome!", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().equals("")) {
                    Toast.makeText(MainActivityCadastroUsuario.this, "Preencha o campo email!", Toast.LENGTH_SHORT).show();

                } else if (posicao == 0) {
                    Toast.makeText(MainActivityCadastroUsuario.this, "Selecione sexo!", Toast.LENGTH_SHORT).show();

                } else if (idade.getText().toString().equals("")) {
                    Toast.makeText(MainActivityCadastroUsuario.this, "Preencha o campo idade!", Toast.LENGTH_SHORT).show();

                } else if (senha.getText().toString().equals("")) {
                    Toast.makeText(MainActivityCadastroUsuario.this, "Preencha o campo senha!", Toast.LENGTH_SHORT).show();

                } else {
                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSexo(item);
                    usuario.setIdade(idade.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                }
            }
        }*/
