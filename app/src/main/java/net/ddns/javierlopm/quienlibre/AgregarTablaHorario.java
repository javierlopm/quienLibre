package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;


public class AgregarTablaHorario extends Activity {

    Boolean [][] ocupado;
    int [][] idBotones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tabla_horario);

        ocupado = new Boolean[8][5];
        for (int i=0;i<8;i++) for (int j=0;j<5;j++) ocupado[i][j] = false;

        idBotones = new int[4][5];
        inicializarBotones(idBotones);

        for (int i=0;i<4;i++){
            for (int j=0;j<5;j++){
                Button b = (Button) findViewById(idBotones[i][j]);
//                b.setBackgroundColor(Color.parseColor("black"));
//                b.setScaleX(0.5f);
//                b.setScaleY(0.1f);
//                b.setBackgroundResource(R.drawable.colorgris);

            }
            //Forzando al recolector de basura por cada fila
            System.gc();
        }


    }

    public void inicializarBotones(int a[][]){
        a[0][0] = R.id.button00;
        a[0][1] = R.id.button01;
        a[0][2] = R.id.button02;
        a[0][3] = R.id.button03;
        a[0][4] = R.id.button04;

        a[1][0] = R.id.button10;
        a[1][1] = R.id.button11;
        a[1][2] = R.id.button12;
        a[1][3] = R.id.button13;
        a[1][4] = R.id.button14;

        a[2][0] = R.id.button20;
        a[2][1] = R.id.button21;
        a[2][2] = R.id.button22;
        a[2][3] = R.id.button23;
        a[2][4] = R.id.button24;

        a[3][0] = R.id.button30;
        a[3][1] = R.id.button31;
        a[3][2] = R.id.button32;
        a[3][3] = R.id.button33;
        a[3][4] = R.id.button34;
    }


}
