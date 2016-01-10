package net.ddns.javierlopm.quienlibre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;







public class MenuPrincipal extends Activity {
    public final String mainTag = "MainActivity";

    Boolean trimestreSeleccionado = false;
    Boolean tengoHorario          = false;
    Boolean tengoAmigos           = false; // Forever alone var
    File archivoTrimestre;
    String trimestre;
    int anio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ModeloHorario md = new ModeloHorario(this);
        if (! md.hayHorario()){
            HorarioParser hp = new HorarioParser(this);
            hp.agregarHorario();
        }

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
            ((TextView)findViewById(R.id.salon)).setText("Agrega tu horario");

        }



    }



    @Override
    public void onResume(){
        super.onResume();
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
    public void agregarPersona(View view){
        Intent intent = new Intent(this, AdicionHorario.class);
        startActivity(intent);
    }
    public void verDisp(View view){
        //actualizarVariables();
        ModeloHorario md = new ModeloHorario(this);
        md.limpiarTablas();
//        if (trimestreSeleccionado && tengoHorario && tengoAmigos){
//            //Si to_do marcha bien mostrar horario con disponibilidad de amigos
//            Intent intent = new Intent(this, VisualizarQuienLibre.class);
//            startActivity(intent);
//
//        }
//        else if(!trimestreSeleccionado){
//            enviarMensaje("Parece que no has seleccionado un trimestre! Qué esperas?");
//        }
//        else if(trimestreSeleccionado && !tengoHorario){
//            enviarMensaje("Parece que has olvidado agrega tu horario este trimestre");
//        }
//        else if(trimestreSeleccionado && tengoHorario && !tengoAmigos){
//            enviarMensaje("Parece que no tienes el horario de nadie mas este trimestre :(");
//        }
//        else {
//            enviarMensaje("Sinceramente no se que hago aqui");
//        }

    }

}

