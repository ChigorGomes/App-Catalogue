package br.edu.ufam.icomp.catalogue.Conexao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static DatabaseReference databaseReference;
    private  static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFirebase(){
        if(databaseReference==null){
            databaseReference= FirebaseDatabase.getInstance().getReference();

        }
        return  databaseReference;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(firebaseAuth==null){
            firebaseAuth= FirebaseAuth.getInstance();

        }
        return  firebaseAuth;
    }

}
