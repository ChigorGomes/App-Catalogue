package br.edu.ufam.icomp.catalogue.Activity;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import br.edu.ufam.icomp.catalogue.R;

public class MainActivityTelaPrincipal extends AppCompatActivity {

    private ImageButton btnCadastro;
    private ImageButton btnStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tela_principal);

        btnCadastro = (ImageButton) findViewById(R.id.buttonTelaPrincipalCatalogar);
        btnStatus = (ImageButton) findViewById(R.id.buttonTelaPrincipalStatus);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivityTelaPrincipal.this, MainActivityCadastroCatalogo.class);
                startActivity(intent);
            }
        });

        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivityTelaPrincipal.this, MainActivityMeusCatalogos.class);
                startActivity(i);
            }
        });
    }
}
