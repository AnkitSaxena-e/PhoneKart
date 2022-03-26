package com.example.phonekart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminFHShowActivity extends AppCompatActivity {

    private EditText FiFhone, SeFhone, ThFhone, FoFhone, FvFhone, SiFhone;
    private Button AddItt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_f_h_show);

        FiFhone = findViewById(R.id.FirstF);
        SeFhone = findViewById(R.id.SecondS);
        ThFhone = findViewById(R.id.ThirdT);
        FoFhone = findViewById(R.id.ForthF);
        FvFhone = findViewById(R.id.FiveF);
        SiFhone = findViewById(R.id.SixS);

        AddItt = findViewById(R.id.AddFhoneF);

        UpdateAll();

        AddItt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckIt();

            }
        });

    }

    private void UpdateAll() {

        DatabaseReference reffff = FirebaseDatabase.getInstance().getReference().child("FFhones");

        reffff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    FiFhone.setText(snapshot.child("Fi").getValue().toString(), TextView.BufferType.EDITABLE);
                    SeFhone.setText(snapshot.child("Se").getValue().toString(), TextView.BufferType.EDITABLE);
                    ThFhone.setText(snapshot.child("Th").getValue().toString(), TextView.BufferType.EDITABLE);
                    FoFhone.setText(snapshot.child("Fo").getValue().toString(), TextView.BufferType.EDITABLE);
                    FvFhone.setText(snapshot.child("Fv").getValue().toString(), TextView.BufferType.EDITABLE);
                    SiFhone.setText(snapshot.child("Si").getValue().toString(), TextView.BufferType.EDITABLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CheckIt() {

        String Fi = FiFhone.getText().toString();
        String Se = SeFhone.getText().toString();
        String Th = ThFhone.getText().toString();
        String Fo = FoFhone.getText().toString();
        String Fv = FvFhone.getText().toString();
        String Si = SiFhone.getText().toString();

        if(TextUtils.isEmpty(Fi)){
            FiFhone.setError("Please Enter");
        }if(TextUtils.isEmpty(Se)){
            SeFhone.setError("Please Enter");
        }if(TextUtils.isEmpty(Th)){
            Th = "A";
        }if(TextUtils.isEmpty(Fo)){
            Fo = "A";
        }if(TextUtils.isEmpty(Fv)){
            Fv = "A";
        }if(TextUtils.isEmpty(Si)){
            Si = "A";
        }

            GoAdd(Fi, Se, Th, Fo, Fv, Si);

    }

    private void GoAdd(String fi, String se, String th, String fo, String fv, String si) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("FFhones");

        HashMap<String, Object> ff = new HashMap<>();

        ff.put("Fi", fi);
        ff.put("Se", se);
        ff.put("Th", th);
        ff.put("Fo", fo);
        ff.put("Fv", fv);
        ff.put("Si", si);

        ref.updateChildren(ff).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminFHShowActivity.this, "Done", Toast.LENGTH_SHORT).show();

            }
        });

    }
}




