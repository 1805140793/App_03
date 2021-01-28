package fisei.app_03.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fisei.app_03.Entities.Contacto;
import fisei.app_03.PersistirDatos;

public class ContactoDAL {
    private PersistirDatos persistirDatos;      //Crear BD
    private SQLiteDatabase sql;                 //Emitir comandos SQL
    private Context context;                    //Para el constructor

    public ContactoDAL(Context context) {
        this.context = context;
    }

    public void open(){
        persistirDatos = new PersistirDatos(context,"DB_OCTAVO",null,1);
        sql = persistirDatos.getWritableDatabase();     //modo escritura
    }

    public void close(){
        sql.close();
    }

    public Contacto selectByCodigo(int codigo){
        Contacto contacto = null;
        //open();
        try {
            Cursor cursor = sql.rawQuery("SELECT Nombre, Apellido, Telefono, Correo FROM Contactos WHERE Codigo="+codigo,null);

            if (cursor.moveToFirst()){
                //mostrar la fila recuperada
                contacto = new Contacto();

                contacto.setNombre(cursor.getString(0));
                contacto.setApellido(cursor.getString(1));
                contacto.setTeefono(cursor.getString(2));
                contacto.setCorreo(cursor.getString(3));
            }
        }catch (Exception e){
            throw e;
        }finally {
            close();
        }

        return  contacto;
    }

    public long insert(Contacto contacto){
        long count = 0;
        open();
        try{
            ContentValues row = new ContentValues();
            row.put("Nombre",contacto.getNombre());
            row.put("Apellido",contacto.getApellido());
            row.put("Telefono",contacto.getTeefono());
            row.put("Correo",contacto.getCorreo());

            count = sql.insert("Contactos",null,row);
        }catch (Exception e){
            throw e;
        }finally {
            close();
        }

        return count;
    }

    public ArrayList<String> select(){
        open();
        ArrayList<String> list = null;
        try {
            String sqlSelect = "SELECT Codigo, Nombre, Apellido, Telefono, Correo FROM Contactos";    //Rendimiento mejor para recuperar los datos. Ocupa menos recursos que la primera opcion

            Cursor cursor = sql.rawQuery(sqlSelect,null);

            if (cursor.moveToFirst()){
                list = new ArrayList<String>();
                do {
                    list.add(cursor.getString(0) + "  " + cursor.getString(1) + "  " +
                            cursor.getString(2) + "  " + cursor.getString(3) + "  " +
                            cursor.getString(4));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            throw e;
        }finally {
            close();
        }

        return list;
    }

    public int update(Contacto contacto){
        int count = 0;
        open();
        try{
            ContentValues row = new ContentValues();
            row.put("Nombre",contacto.getNombre());
            row.put("Apellido",contacto.getApellido());
            row.put("Telefono",contacto.getTeefono());
            row.put("Correo",contacto.getCorreo());

            row.put("Codigo",contacto.getCodigo());

            count = sql.update("Contactos",row,
                    "Codigo="+contacto.getCodigo(),null);

        }catch (Exception e){
            throw e;
        }finally {
            close();
        }

        return count;
    }

    public int delete(int codigo){
        int count = 0;
        open();
        try {
            count = sql.delete("Contactos","Codigo="+codigo,null);
        }catch (Exception e){
            throw e;
        }finally {
            close();
        }
        return count;
    }
}
