package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;







public class MenuPrincipal extends Activity {
    public final String mainTag = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        actualizarTextos();


    }



    @Override
    public void onResume(){
        super.onResume();
        actualizarTextos();
    }

    public void actualizarTextos(){
        ModeloHorario md = new ModeloHorario(this);

        if (md.hayHorario()){
            BloquePropio proximaClase = md.proximoBloque();
            TextView texto = (TextView)findViewById(R.id.salon);
            texto.setText(proximaClase.edificio.toString() + proximaClase.salon);
            texto = (TextView)findViewById(R.id.hora);
            texto.setText(proximaClase.dia.toString() + " hora " + proximaClase.hora);
            texto = (TextView)findViewById(R.id.asignatura);
            texto.setText(md.obterNombreAsignatura(proximaClase.materia));
            texto = (TextView)findViewById(R.id.tituloClase);
            texto.setVisibility(View.VISIBLE);
        } else {

            (findViewById(R.id.hora)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.asignatura)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.tituloClase)).setVisibility(View.INVISIBLE);
            ((TextView)findViewById(R.id.salon)).setText("Agrega tu horario");

        }
    }

    public void enviarMensaje(String mensaje){
        Toast toast = Toast.makeText(
                getApplicationContext(),
                mensaje,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    //Funcion para que cada boton inicie el activity correspondiente
    public void cambTrim(View view){
        Intent intent = new Intent(this, CambioTrimestre.class);
        startActivity(intent);

    }

    public void borrarDatos(View view){
        ModeloHorario md = new ModeloHorario(this);
        md.limpiarTablas();
        actualizarTextos();
    }

    public void agregarComprobante(View view){
        Intent intent = new Intent(this, AgregarComprobante.class);
        startActivity(intent);
    }

    public void agregarPersona(View view){
        Intent intent = new Intent(this, AdicionHorario.class);
        startActivity(intent);
    }
    public void verDisp(View view){
        //actualizarVariables();
        ModeloHorario md = new ModeloHorario(this);
        md.limpiarTablas();


    }

}

