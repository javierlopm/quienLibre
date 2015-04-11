package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AgregarTablaHorario extends Activity {

    Boolean [][][] ocupado;
    int [][][] idBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tabla_horario);

        ocupado = new Boolean[4][5][2];
        for (int i=0;i<4;i++) for (int j=0;j<5;j++) for (int k=0;k<=1;k++) ocupado[i][j][k] = false;

        idBotones = new int[4][5][2];
        inicializarBotones(idBotones);

        for(int i = 0; i<4 ; i++){
            for(int j=0; j<2;j++){
                //Extraido de
                //http://stackoverflow.com/questions/19789964/how-to-get-clicks-on-two-overlapping-button-in-same-frame-layout-in-android
                EscucharBoton eb = new EscucharBoton(i,2,j);
                findViewById(idBotones[i][2][j]).setOnTouchListener(eb);
            }


        }


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

    public void enviarMensaje(String mensaje){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                mensaje,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void inicializarBotones(int a[][][]){
//        a[0][0] = R.id.but00;
//        a[0][1] = R.id.but01;
        a[0][2][0] = R.id.but02a;
        a[0][2][1] = R.id.but02b;
//        a[0][3] = R.id.but03;
//        a[0][4] = R.id.but04;

//        a[1][0] = R.id.but10;
//        a[1][1] = R.id.but11;
        a[1][2][0] = R.id.but12a;
        a[1][2][1] = R.id.but12b;
//        a[1][3] = R.id.but13;
//        a[1][4] = R.id.but14;

//        a[2][0] = R.id.but20;
//        a[2][1] = R.id.but21;
        a[2][2][0] = R.id.but22a;
        a[2][2][1] = R.id.but22b;
//        a[2][3] = R.id.but23;
//        a[2][4] = R.id.but24;

//        a[3][0] = R.id.but30;
//        a[3][1] = R.id.but31;
        a[3][2][0] = R.id.but32a;
        a[3][2][1] = R.id.but32b;
//        a[3][3] = R.id.but33;
//        a[3][4] = R.id.but34;
    }


}
