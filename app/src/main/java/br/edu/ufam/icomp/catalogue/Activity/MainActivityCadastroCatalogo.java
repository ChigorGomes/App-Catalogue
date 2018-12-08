package br.edu.ufam.icomp.catalogue.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import br.edu.ufam.icomp.catalogue.Conexao.Conexao;
import br.edu.ufam.icomp.catalogue.Entidades.Catalogacao;
import br.edu.ufam.icomp.catalogue.R;

public class MainActivityCadastroCatalogo extends AppCompatActivity {

    private ImageButton btnChoose;
    private Button btnUpload;
    private ImageView imageView;
    private EditText edtNome;
    private EditText catObs;
    private RadioButton op1, op2;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    String opcaoEscolhida = "Animal";

    // Firebase

    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private StorageReference storageReferece;
    private DatabaseReference databaseCatalogacoes;
    private FirebaseUser user;
    private String lclImagem;
    private String urlImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cadastro_catalogo);

        // Firebase init
        storage = FirebaseStorage.getInstance();
        storageReferece = storage.getReference();
        databaseCatalogacoes = FirebaseDatabase.getInstance().getReference("catalogacoes");
        auth = FirebaseAuth.getInstance();
        user = Conexao.getFirebaseUser();

        btnChoose = (ImageButton) findViewById(R.id.buttonEscolherFoto);
        btnUpload = (Button) findViewById(R.id.buttonTelaCatalogarCadastrar);
        imageView = (ImageView) findViewById(R.id.uploadImageView);
        edtNome = (EditText) findViewById(R.id.editTextNomeCat);
        catObs = (EditText) findViewById(R.id.editTextObsCat);
        op1 = (RadioButton) findViewById(R.id.radioButtonAnimal);
        op2 = (RadioButton) findViewById(R.id.radioButtonVegetal);

        btnChoose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 chooseImage();
             }
         });

        btnUpload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 checarOpcao();
            }
         });
    }

    private void checarOpcao(){
        if(!op1.isChecked() && !op2.isChecked()){
            Toast.makeText(MainActivityCadastroCatalogo.this, "Marque uma das opções", Toast.LENGTH_SHORT).show();
        }else if(edtNome.getText().toString().equals("") || catObs.getText().toString().equals("")){
            Toast.makeText(MainActivityCadastroCatalogo.this, "Informe todos os campos", Toast.LENGTH_SHORT).show();

        }else{
            if(op1.isChecked()) opcaoEscolhida = "Animal";
            else if(op2.isChecked()) opcaoEscolhida = "Vegetal";
            uploadImage();
        }
    }

    private void addCatalogacao(){
        String tipoCatalogacao = opcaoEscolhida;
        String cId = databaseCatalogacoes.push().getKey();
        String uId = user.getUid();

        Catalogacao c1 = new Catalogacao();

        c1.setcID(cId);
        c1.setuID(user.getUid());
        c1.setcName(edtNome.getText().toString());
        c1.setcTipo(opcaoEscolhida);
        c1.setcObs(catObs.getText().toString());
        c1.setImagemCaminho(lclImagem);
        c1.setImagemUrl(urlImagem);
        c1.setStatus("Aguardando resposta");

        databaseCatalogacoes.child(uId).child(cId).setValue(c1);
        Toast.makeText(MainActivityCadastroCatalogo.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MainActivityCadastroCatalogo.this, MainActivityTelaPrincipal.class);
        startActivity(i);
        finish();
    }

    private void uploadImage() {
        UploadTask uploadTask = null;
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String nomeImagem = dateFormat.format(new Date()).trim();
        lclImagem = "images/users/" + userID + "/" + nomeImagem + ".jpg";
        final StorageReference ref = storageReferece.child(lclImagem);

        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Enviando..");
            progressDialog.show();

            uploadTask = (UploadTask) ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivityCadastroCatalogo.this, "Falha ao enviar!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" + (int) progress + "%");
                        }
                    });


        }

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()) throw task.getException();
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    urlImagem = downloadUri.toString();
                    Log.d("Cadastro", "URL 2: "+ lclImagem);
                    addCatalogacao();
                }
                else{
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
