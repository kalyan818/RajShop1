package com.kalyan.rajshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class FirstActivity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    Button scan,checkout;
    ListView listView;
    public static TextView items,rupees;
    public static ArrayList<String> arrayList = new ArrayList<>();
    public static ArrayAdapter arrayAdapter;
    public static SampleAdapter sampleAdapter;
    RecyclerView recyclerView;
    public static int total123=0;
    public static ArrayList<String> itemName = new ArrayList<>();
    public static ArrayList<String> subItem = new ArrayList<>();
    public static ArrayList<String> price =  new ArrayList<>();
    public static ArrayList<String> name =  new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Upload1 upload1;
    public static int total=0;
    int yu=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        total123 =0;
        for (int i = 0;i<name.size();i++)
        {
            total123 = total123 + Integer.valueOf(price.get(i));
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://marketing-51489.firebaseio.com/random/items");
        scan = (Button)findViewById(R.id.scan);
        items = (TextView)findViewById(R.id.items);
        rupees = (TextView)findViewById(R.id.rupees);
        checkout = (Button)findViewById(R.id.checkout);
        recyclerView =(RecyclerView)findViewById(R.id.relative);
        sampleAdapter = new SampleAdapter(itemName,subItem,price,getApplicationContext());
        recyclerView.setAdapter(sampleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission,STORAGE_CODE);
                    }else {
                        if(name.size()!=0 || price.size()!=0)
                        savepdf(name,price);
                    }
                }else {
                        if(name.size()!=0 || price.size()!=0)
                        savepdf(name,price);
                }


            }
        });







        listView = (ListView)findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter(FirstActivity.this,android.R.layout.simple_list_item_1,arrayList);
        arrayAdapter.notifyDataSetChanged();
        sampleAdapter.notifyDataSetChanged();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this,Experement.class));
            }
        });
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Upload upload = new Upload(upload1.getType(), upload1.getPrice(), upload1.getUid(),upload1.getStatus());
                databaseReference.child(id).setValue(upload);*/
                try {
                    Class<?> cls = Class.forName("com.kalyan.rajshop.Experement");
                    Field field = cls.getDeclaredField("total");
                    field.setAccessible(true);
                    Object object = field.get(new Experement());
                    total=(int)object;
                    //Toast.makeText(FirstActivity.this,res,Toast.LENGTH_LONG).show();
                }catch (Exception e){

                }
                arrayList.remove(i);
                arrayAdapter.notifyDataSetChanged();
                items.setText(" "+arrayAdapter.getCount()+"Items");
            }
        });
    }

    private void savepdf(ArrayList<String> name,ArrayList<String> price) {

        Document document = new Document();
        String mFileName = new SimpleDateFormat("yyyymmdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";
        try
        {
            PdfWriter.getInstance(document,new FileOutputStream(mFilePath));

            document.open();


            document.addAuthor("Kalyan sai");

            int total234 =0;
            for (int i = 0;i<name.size();i++)
            {
                document.add(new Paragraph(name.get(i)+"                                        "+price.get(i)));
                total234 = total234 + Integer.valueOf(price.get(i));
            }
            document.addHeader("kalyan","hello this is kalyan");
            document.add(new Paragraph("                                        " +"total:"+ String.valueOf(total234)));


            document.close();




            Toast.makeText(this,mFileName+".pdf\n is saved to \n" + mFilePath,Toast.LENGTH_LONG).show();






        }catch (Exception e){
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(name.size()!=0 || price.size()!=0)
                    savepdf(name,price);
                }
            }
        }
    }
}
