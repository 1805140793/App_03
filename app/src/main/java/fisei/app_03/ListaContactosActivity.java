package fisei.app_03;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaContactosActivity extends AppCompatActivity {

    private ListView lv_datos;
    private ArrayList<String> listaContactos;       //contiene los nombres de los contactos de la tabla
    private ArrayAdapter adaptador;                 //sirve como un auxiliar para llenar el list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        lv_datos=findViewById(R.id.lv_datos);

        //recuperar los datos de la tabla
        listaContactos = selectContactos();

        //Crear Adapter
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaContactos);

        //asociar el adaptador con los datos al ListView
        //mostrar los datos
        lv_datos.setAdapter(adaptador);
    }

    private ArrayList<String> selectContactos(){
        //crear la base datos
        PersistirDatos persistirDatos = new PersistirDatos(this,"OCTAVODB",null,1);

        //abrir la base de datos
        SQLiteDatabase database = persistirDatos.getReadableDatabase();     //lectura

        //recuperar los datos de la tabla
        //String select = "SELECT * FROM Contactos";          //Consume mas recursos del ordenador. Es decir que el rendimiento baja cuando el numero de datos crecen a un millon
        String select1 = "SELECT Codigo, Nombre, Apellido, Telefono, Correo FROM Contactos";    //Rendimiento mejor para recuperar los datos. Ocupa menos recursos que la primera opcion

        ArrayList<String> listContactos = new ArrayList<String>();

        Cursor cursor = database.rawQuery(select1,null);
        if (cursor.moveToFirst()){
            do {
                listContactos.add(cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2));
            }while (cursor.moveToNext());
        }
        return listContactos;
    }
}