package com.kalyan.rajshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class Experement extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private static final int REQUEST_CAMERA = 1;
    private String status = "hello";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        scannerView.resumeCameraPreview(Experement.this);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(), "Permission already granted!", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }
    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }
    @Override
    public void onBackPressed() {
        FirstActivity.items.setText(" "+FirstActivity.arrayAdapter.getCount()+ " Items");
        FirstActivity.rupees.setText(String.valueOf(FirstActivity.total123));
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    @Override
    public void handleResult(final Result result) {
        Toast.makeText(this,result.getText().toString(),Toast.LENGTH_LONG).show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://marketing-51489.firebaseio.com/random/items");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Upload upload = snapshot.getValue(Upload.class);
                    if (result.getText().equals(upload.getUid())){
                        Total.sum1(Integer.valueOf(upload.getPrice()));
                        FirstActivity.arrayList.add(upload.getType() + "                                                         " + upload.getPrice());
                        int j=0;
                        for(String m :FirstActivity.itemName)
                        {
                            if(!m.equals(upload.getType()))
                            {
                                j++;
                            }
                        }
                        if(j==FirstActivity.itemName.size())
                        {
                            FirstActivity.itemName.add(upload.getType());
                        }
                        FirstActivity.name.add(upload.getType());
                        FirstActivity.price.add(upload.getPrice());
                        FirstActivity.subItem.add(upload.getPrice()+"*1");
                        FirstActivity.sampleAdapter.notifyDataSetChanged();
                        FirstActivity.arrayAdapter.notifyDataSetChanged();
                        databaseReference.child(snapshot.getKey()).removeValue();
                    }
                }
                /*for (DataSnapshot Snapshot:dataSnapshot.getChildren())
                {
                    GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {};
                    Map<String, String> map = Snapshot.getValue(genericTypeIndicator );
                    String type = map.get("type");
                    String uid = map.get("uid");
                    String price = map.get("price");
                    String status = map.get("status");
                    if (uid.equals(result.getText().toString()) && status.equals("Avail"))
                    {
                        FirstActivity.arrayList.add(result.getText());
                        Toast.makeText(Experement.this,Snapshot.getKey(),Toast.LENGTH_LONG).show();
                        databaseReference.child(Snapshot.getKey()).child("status").setValue("Not Avail");
                        FirstActivity.arrayAdapter.notifyDataSetChanged();
                    }
                }*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                scannerView.resumeCameraPreview(Experement.this);
            }
        }, 500);
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(Experement.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
}
