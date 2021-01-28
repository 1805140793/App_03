package fisei.app_03;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PersistirDatos extends SQLiteOpenHelper {

    private final String SQL_CREATE_TABLE_CONTACTOS = "CREATE TABLE Contactos (Codigo INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellido TEXT, Telefono TEXT, Correo TEXT)";
    //private String SQL_CREATE_TABLE_REDES_SOCIALES = "CREATE TABLE Contactos (Codigo INTEGER PRIMARY KEY AUTOINCREMENT, Nombre TEXT, Apellido TEXT, Telefono TEXT, Correo TEXT)";


    public PersistirDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //para crear la estructura de la Base de Datos (TABLAS)
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONTACTOS);     //menos un: select
        //sqLiteDatabase.execSQL(SQL_CREATE_TABLE_REDES SOCIALES);

        //AGREGAR DATOS iniciales en las tablas
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Codigo SQL para crear la estructura de la Base de Datos(TABLAS)
        //QUE requieren modificarse
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Contactos");

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONTACTOS);
    }
}
