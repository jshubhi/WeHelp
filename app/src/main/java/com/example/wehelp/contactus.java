package com.example.wehelp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class contactus extends Fragment {
    EditText csName,csContact,csSubject,csMessage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.contactus, container, false);

         csName = (EditText) inflate.findViewById(R.id.csName);
         csContact = (EditText) inflate.findViewById(R.id.csContact);
         csSubject = (EditText) inflate.findViewById(R.id.csSubject);
         csMessage = (EditText) inflate.findViewById(R.id.csMessage);
        Button csSubmit=(Button)inflate.findViewById(R.id.csSubmit);
        csSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mailing();

            }
        });


        return inflate;
    }
    public void mailing()
    {

        String subject= csSubject.getText().toString();
        String name= csName.getText().toString();
        String contact= csContact.getText().toString();
        String message= csMessage.getText().toString();
        String All="NAME:"+name+"\nCONTACT:"+contact+"\n\n"+message;
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"fighterhanuman@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,All);



        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an Email Client"));
    }

}
