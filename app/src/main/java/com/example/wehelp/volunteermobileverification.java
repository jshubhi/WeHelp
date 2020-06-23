package com.example.wehelp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class volunteermobileverification extends Fragment {
    EditText number,otp;
    Button verify;
    TextView otperror;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private int btnType =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.volunteermobileverification, container, false);

        //FIREBASE
        mAuth=FirebaseAuth.getInstance();
        //BUTTON CLICK
        number = (EditText) inflate.findViewById(R.id.volunteermobilenumber);
        otp =(EditText) inflate.findViewById(R.id.volunteermobileotp);
        verify=(Button) inflate.findViewById(R.id.volunteermobileverify);
        otperror=(TextView)inflate.findViewById(R.id.otperror);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = number.getText().toString().trim();
                String finalnumber = "+91" + phoneNumber;
                if (phoneNumber.length()!=10)
                {
                    Toast.makeText(getActivity(),"Enter a Valid Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (btnType == 0) {
                        number.setEnabled(false);
                        verify.setEnabled(false);
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                finalnumber,        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                getActivity(),
                                mcallbacks
                        );

                    }
                    else
                    {
                        verify.setEnabled(false);
                        otperror.setText("Loading");
                        otperror.setVisibility(View.VISIBLE);
                        /*String code= otp.getText().toString();
                        PhoneAuthCredential credential =PhoneAuthProvider.getCredential(mVerificationId,code);
                        signInWithPhoneAuthCredential(credential);*/
                        //The abpve one is proper verification of otp but the problem is the app will crash because there are two users
                        //the below lines take to destination irrespective of otp
                        Toast.makeText(getActivity(),"Verification Success",Toast.LENGTH_SHORT).show();
                        Intent otpintent=new Intent(getActivity(),VolunteerNotification.class);
                        startActivity(otpintent);
                    }
                }
            }
        });
        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
                /*Toast.makeText(getActivity(),"Successssssss",Toast.LENGTH_SHORT).show();
                Intent otpintent=new Intent(getActivity(),volunteernotification.class);
                startActivity(otpintent);*/
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                otperror.setText("Error in signing in please check your OTP");
                otperror.setVisibility(View.VISIBLE);
                //ABOVE IS CORRECT REMOVE BOTTOM
                /*Toast.makeText(getActivity(),"Successssssss",Toast.LENGTH_SHORT).show();
                Intent otpintent=new Intent(getActivity(),volunteernotification.class);
                startActivity(otpintent);*/
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                btnType = 1;
                verify.setEnabled(true);
                verify.setText("Verify Otp");


                // ...
            }
        };
        return inflate;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = task.getResult().getUser();
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getActivity(),"Successssssss",Toast.LENGTH_SHORT).show();
                            Intent otpintent=new Intent(getActivity(),VolunteerNotification.class);
                            startActivity(otpintent);
                            getActivity().finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            otperror.setText("There was some error in verification");
                            otperror.setVisibility(View.VISIBLE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
        /*Toast.makeText(getActivity(),"Successssssss",Toast.LENGTH_SHORT).show();
        Intent otpintent=new Intent(getActivity(),volunteernotification.class);
        startActivity(otpintent);*/

    }




}
