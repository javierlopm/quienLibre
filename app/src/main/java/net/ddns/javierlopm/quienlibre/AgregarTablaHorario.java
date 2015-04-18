package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class AgregarTablaHorario extends Activity {

    Boolean [][][] ocupado;
    int     [][][] idBotones;
    String[] tokens;
    String  nombre;             //Nombre de la persona a agregar, obtenido del activity anterior
    File    archivoTrimestre;   //Archivo de donde se leera anio/trimestre
    String  trimestre;          //Trimestre del horario a agregar
    int     anio;               //Analogo a trimestre
    String buffer;              //String auxiliar para lectura



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tabla_horario);

        /*Variables necesarias para la escritura en la base de datos*/
        Intent in = getIntent();

        nombre = in.getStringExtra("nombre");


        archivoTrimestre = new File(getApplicationContext().getFilesDir(),"trimestreActual");
        try{
            Scanner lector = new Scanner(archivoTrimestre);
            buffer = lector.nextLine();
            tokens = buffer.split(" ");
            anio = Integer.parseInt(tokens[0]);
            trimestre = tokens[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ocupado = new Boolean[4][5][2];
        for (int i=0;i<4;i++) for (int j=0;j<5;j++) for (int k=0;k<=1;k++) ocupado[i][j][k] = false;

        idBotones = new int[4][5][2];
        inicializarBotones(idBotones);

        for(int i = 0; i<4 ; i++){
            for(int j=0; j<5;j++){
                for(int k=0;k<2;k++) {
                    EscucharBoton eb = new EscucharBoton(i, j, k);
                    findViewById(idBotones[i][j][k]).setOnTouchListener(eb);
                }
            }


        }


    }

    public void insertarEnDb(View view){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Horario de " + nombre + "agregado",
                Toast.LENGTH_SHORT);
        toast.show();

        ModeloHorario db = new ModeloHorario(this);

        for (int i=0;i<4;i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k <= 1; k++) {
                    if(!ocupado[i][j][k]){
                        //Fila y posicion(arriba abajo) definen la hora
                        db.agregarClase(trimestre,anio,nombre,intADia(j),(i*2) + 1 +k);

                    }
                }
            }
        }
        db.close();


        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public String intADia(int a){
        String dia;
        switch (a) {
            case 0:  dia = "lunes";
                break;
            case 1:  dia = "martes";
                break;
            case 2:  dia = "miércoles";
                break;
            case 3:  dia = "jueves";
                break;
            case 4:  dia = "viernes";
                break;
            default: dia = "invalido";
                break;
        }

        return  dia;
    }

    public void inicializarBotones(int a[][][]){
        a[0][0][0] = R.id.but00a;
        a[0][0][1] = R.id.but00b;
        a[0][1][0] = R.id.but01a;
        a[0][1][1] = R.id.but01b;
        a[0][2][0] = R.id.but02a;
        a[0][2][1] = R.id.but02b;
        a[0][3][0] = R.id.but03a;
        a[0][3][1] = R.id.but03b;
        a[0][4][0] = R.id.but04a;
        a[0][4][1] = R.id.but04b;

        a[1][0][0] = R.id.but10a;
        a[1][0][1] = R.id.but10b;
        a[1][1][0] = R.id.but11a;
        a[1][1][1] = R.id.but11b;
        a[1][2][0] = R.id.but12a;
        a[1][2][1] = R.id.but12b;
        a[1][3][0] = R.id.but13a;
        a[1][3][1] = R.id.but13b;
        a[1][4][0] = R.id.but14a;
        a[1][4][1] = R.id.but14b;

        a[2][0][0] = R.id.but20a;
        a[2][0][1] = R.id.but20b;
        a[2][1][0] = R.id.but21a;
        a[2][1][1] = R.id.but21b;
        a[2][2][0] = R.id.but22a;
        a[2][2][1] = R.id.but22b;
        a[2][3][0] = R.id.but23a;
        a[2][3][1] = R.id.but23b;
        a[2][4][0] = R.id.but24a;
        a[2][4][1] = R.id.but24b;

        a[3][0][0] = R.id.but30a;
        a[3][0][1] = R.id.but30b;
        a[3][1][0] = R.id.but31a;
        a[3][1][1] = R.id.but31b;
        a[3][2][0] = R.id.but32a;
        a[3][2][1] = R.id.but32b;
        a[3][3][0] = R.id.but33a;
        a[3][3][1] = R.id.but33b;
        a[3][4][0] = R.id.but34a;
        a[3][4][1] = R.id.but34b;
    }

    public class EscucharBoton implements View.OnTouchListener  {
        int i,j,k;

        EscucharBoton(int fila,int columna,int pos){
            super();
            i = fila;
            j = columna;
            k = pos;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //Extraido de
            //http://stackoverflow.com/questions/19789964/how-to-get-clicks-on-two-overlapping-button-in-same-frame-layout-in-android
            int altura = findViewById(idBotones[i][j][k]).getWidth();
            int ancho  = findViewById(idBotones[i][j][k]).getWidth();
            float puntoX = event.getX();
            float puntoY = event.getY();

            if( event.getActionMasked() == MotionEvent.ACTION_DOWN) {

                boolean trianguloSup = (altura - puntoY) * ancho > puntoX * altura;

                int codigoImagen; //Forzar cambio del boton no presionable de arriba, su id no se puede obtener del view

                int pos;

                if (trianguloSup) {
                    pos = 0;
                    if (!ocupado[i][j][pos]) {
                        ocupado[i][j][pos] = true;
                        codigoImagen = R.drawable.supselected;
                    } else {
                        ocupado[i][j][pos] = false;
                        codigoImagen = R.drawable.supnotselected;
                    }
                } else {
                    pos = 1;
                    if (!ocupado[i][j][pos]) {
                        ocupado[i][j][pos] = true;
                        codigoImagen = R.drawable.botselected;
                    } else {
                        ocupado[i][j][pos] = false;
                        codigoImagen = R.drawable.botnotselected;
                    }
                }


                findViewById(idBotones[i][j][pos]).setBackgroundResource(codigoImagen);

                return true;
            }
            else{
                return false;
            }
        }
    }





}
