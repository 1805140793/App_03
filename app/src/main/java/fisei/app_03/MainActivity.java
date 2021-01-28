package fisei.app_03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fisei.app_03.DAL.ContactoDAL;
import fisei.app_03.Entities.Contacto;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo;
    private EditText et_nombre;
    private EditText et_apellido;
    private EditText et_telefono;
    private EditText et_correo;

    private Button btn_agregar;
    private Button btn_buscar;
    private Button btn_modificar;
    private Button btn_eliminar;
    private Button btn_limpiar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //butter knife inserta codigo a las vistas
        et_codigo = findViewById(R.id.et_codigo);
        et_nombre = findViewById(R.id.et_nombre);
        et_apellido = findViewById(R.id.et_apellido);
        et_telefono = findViewById(R.id.et_telefono);
        et_correo = findViewById(R.id.et_correo);

        btn_agregar = findViewById(R.id.btn_ingresar);
        btn_buscar = findViewById(R.id.btn_buscar);
        btn_modificar = findViewById(R.id.btn_modificar);
        btn_eliminar = findViewById(R.id.btn_eliminar);
        btn_limpiar = findViewById(R.id.btn_limpiar);

        //asociar el menu contextual a una vista
        registerForContextMenu(et_apellido);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //return super.onContextItemSelected(item);
        switch (item.getItemId()){
            case R.id.mnu_contextual_cortar:
                Toast.makeText(this,"Cortar",Toast.LENGTH_LONG).show();
                break;

            case R.id.mnu_contextual_copiar:
                Toast.makeText(this,"Copiar",Toast.LENGTH_LONG).show();
                break;

            case R.id.mnu_contextual_pegar:
                Toast.makeText(this,"Pegar",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void clickIngresar(View parametro){
        //insertarSinClases();
        insertarConClases();
    }

    private void insertarSinClases() {
        //crear la base datos
        PersistirDatos persistirDatos = new PersistirDatos(this,"OCTAVODB",null,1);

        //abrir la base de datos
        //SQLiteDatabase database = persistirDatos.getReadableDatabase();    //LECTURA
        SQLiteDatabase database = persistirDatos.getWritableDatabase();     //ESCRITURA

        //capturar los datos para el: INSERT
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String telefono = et_telefono.getText().toString();
        String correo = et_correo.getText().toString();

        //enviar los datos a la base de datos
        //database.execSQL("INSERT INTO Contactos(Nombre, Apellido, Telefono, Correo) VALUES('x','y','123','xy@gmail.com')");     //agregar datos constantes

        //FORMA INCORRECTA -->  Funciona si pero es un mal habito porque es vulnerable existe problemas de SEGURIDAD como  la inyeccion SQL
        // database.execSQL("INSERT INTO Contactos(Nombre, Apellido, Telefono, Correo) VALUES('"+nombre+"','"+apellido+"','"+telefono+"','"+correo+"')");

        //FORMA CORRECTA
        ContentValues fila = new ContentValues();
        fila.put("Nombre",nombre);
        fila.put("Apellido",apellido);
        fila.put("Telefono",telefono);
        fila.put("Correo",correo);

        //enviar a la base de datos el INSERT
        long cantidad = database.insert("Contactos",null,fila);
        if (cantidad>0){
            Toast.makeText(this,"Contacto agregado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"El contacto no se pudo agregar",Toast.LENGTH_LONG).show();
        }

        //cerrar la base de datos
        database.close();

        //Limpiar los controles
        limpiarControles();
    }

    private void insertarConClases() {
        ContactoDAL dal = new ContactoDAL(this);

        //capturar los datos para el: INSERT
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String telefono = et_telefono.getText().toString();
        String correo = et_correo.getText().toString();

        Contacto contacto = new Contacto(0,nombre,apellido,telefono,correo);

        //enviar a la base de datos el INSERT
        long cantidad = dal.insert(contacto);
        if (cantidad>0){
            Toast.makeText(this,"Contacto agregado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"El contacto no se pudo agregar",Toast.LENGTH_LONG).show();
        }

        //Limpiar los controles
        limpiarControles();
    }

    public void limpiarControles(){
        et_codigo.setText("");
        et_nombre.setText("");
        et_apellido.setText("");
        et_telefono.setText("");
        et_correo.setText("");
    }

    public void clickBuscar(View parametro){
        //buscarSinClases();

    }

    private void buscarSinClases() {
        //crear la base datos
        PersistirDatos persistirDatos = new PersistirDatos(this,"OCTAVODB",null,1);

        //abrir la base de datos
        SQLiteDatabase database = persistirDatos.getReadableDatabase();

        String codigo = et_codigo.getText().toString();
        Cursor cursor = database.rawQuery("SELECT Nombre, Apellido, Telefono, Correo FROM Contactos WHERE Codigo="+codigo,null);

        if (cursor.moveToFirst()){
            //mostrar la fila recuperada
            et_nombre.setText(cursor.getString(0));
            et_apellido.setText(cursor.getString(1));
            et_telefono.setText(cursor.getString(2));
            et_correo.setText(cursor.getString(3));
        }else {
            Toast.makeText(this,"El contacto no se puede recuperar",Toast.LENGTH_LONG).show();
            limpiarControles();
        }
        //cerrar la base
        database.close();
    }

    private void buscarConClases() {
        ContactoDAL dal = new ContactoDAL(this);

        String codigo = et_codigo.getText().toString();

        Contacto contacto = new Contacto();
        contacto = dal.selectByCodigo(Integer.parseInt(codigo));

        if (contacto != null){
            //mostrar la fila recuperada
            et_nombre.setText(contacto.getNombre());
            et_apellido.setText(contacto.getApellido());
            et_telefono.setText(contacto.getTeefono());
            et_correo.setText(contacto.getCorreo());
        }else {
            Toast.makeText(this,"El contacto no se puede recuperar",Toast.LENGTH_LONG).show();
            limpiarControles();
        }
    }

    public void clickModificar(View parametro){
        //crear la base datos
        PersistirDatos persistirDatos = new PersistirDatos(this,"OCTAVODB",null,1);

        //abrir la base de datos
        SQLiteDatabase database = persistirDatos.getWritableDatabase();     //modo escritura

        //capturar los datos para el: INSERT
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String telefono = et_telefono.getText().toString();
        String correo = et_correo.getText().toString();

        String codigo = et_codigo.getText().toString();
        //FORMA CORRECTA
        ContentValues fila = new ContentValues();
        fila.put("Nombre",nombre);
        fila.put("Apellido",apellido);
        fila.put("Telefono",telefono);
        fila.put("Correo",correo);

        //enviar a la base de datos el UPDATE
        long cantidad = database.update("Contactos",fila,"Codigo="+codigo,null);
        if (cantidad>0){
            Toast.makeText(this,"Contacto modificado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"El contacto no se pudo modificar",Toast.LENGTH_LONG).show();
            limpiarControles();
        }

        //cerrar la base de datos
        database.close();

        //Limpiar los controles
        limpiarControles();
    }
    public void clickEliminar(View parametro){
        //crear la base datos
        PersistirDatos persistirDatos = new PersistirDatos(this,"OCTAVODB",null,1);

        //abrir la base de datos
        SQLiteDatabase database = persistirDatos.getWritableDatabase();     //modo escritura

        //capturar los datos para el: DELETE
        String codigo = et_codigo.getText().toString();

        //enviar a la base de datos el UPDATE
        long cantidad = database.delete("Contactos","Codigo="+codigo,null);
        if (cantidad>0){
            Toast.makeText(this,"Contacto eliminado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"El contacto no se pudo eliminar",Toast.LENGTH_LONG).show();
            limpiarControles();
        }

        //cerrar la base de datos
        database.close();

        //Limpiar los controles
        limpiarControles();
    }

    public void cliclLimpiar(View parametro){
        limpiarControles();
    }

    public void clickMostrarContactos(View parametro){
        Intent intent = new Intent(this,ListaContactosActivity.class);
        startActivity(intent);
    }

    //trabajo con el: ActionBar (Barra de herramientas con un menu)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_principal,menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemSeleccionado = item.getItemId();
        if (itemSeleccionado == R.id.mnu_archivo){
            Toast.makeText(this,"Presiono en: Archivo",Toast.LENGTH_LONG).show();
        }
        if (itemSeleccionado == R.id.mnu_cortar){
            Toast.makeText(this,"Presiono en: Cortar",Toast.LENGTH_LONG).show();
        }
        if (itemSeleccionado == R.id.mnu_copiar){
            Toast.makeText(this,"Presiono en: Copiar",Toast.LENGTH_LONG).show();
        }
        if (itemSeleccionado == R.id.mnu_pegar){
            Toast.makeText(this,"Presiono en: Pegar",Toast.LENGTH_LONG).show();
        }
        if (itemSeleccionado == R.id.mnu_listaContactos){
            Intent intent = new Intent(this,ListaContactosActivity.class);
            startActivity(intent);
        }
        if (itemSeleccionado == R.id.mnu_acercaDe){
            Toast.makeText(this,"Presiono en: Acerca de...",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}