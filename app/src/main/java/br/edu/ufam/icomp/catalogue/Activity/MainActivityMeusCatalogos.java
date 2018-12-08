package br.edu.ufam.icomp.catalogue.Activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import br.edu.ufam.icomp.catalogue.Conexao.Conexao;
import br.edu.ufam.icomp.catalogue.Entidades.Catalogacao;
import br.edu.ufam.icomp.catalogue.R;

public class MainActivityMeusCatalogos extends AppCompatActivity {
    private List<Catalogacao>  listaCatalogos;
    private ListView listView;

    DatabaseReference dbReference;
    private FirebaseUser userFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_meus_catalogos);

        userFB = Conexao.getFirebaseUser();

        listaCatalogos = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView_Catalogos);
    }

    protected void onStart(){
        super.onStart();
        dbReference = FirebaseDatabase.getInstance().getReference();
        dbReference.child("catalogacoes").child(userFB.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCatalogos.clear();
                if(dataSnapshot.getChildrenCount() != 0) {

                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Catalogacao c = postSnapShot.getValue(Catalogacao.class);
                        listaCatalogos.add(c);
                    }

                    CatalogoList catalogoAdapter = new CatalogoList(MainActivityMeusCatalogos.this, listaCatalogos);
                    listView.setAdapter(catalogoAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
