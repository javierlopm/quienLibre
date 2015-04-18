package net.ddns.javierlopm.quienlibre;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;



public class VisualizarHorarioCompleto extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View v = inflater.inflate(R.layout.activity_agregar_tabla_horario,container,false);

        v.findViewById(R.id.send).setVisibility(View.GONE);
        v.findViewById(R.id.spSeleccionarConsulta).setVisibility(View.VISIBLE);

        final View este = v;    //Debia ser final... vainas de android

        Spinner sp = (Spinner) v.findViewById(R.id.spSeleccionarConsulta);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(
                                        AdapterView<?> parentView,
                                        View selectedItemView,
                                        int position,
                                        long id) {

                if(position==0) actTodoLibres(este); //Deberia agregar funcionalidad a los botones aqui
                else{
                    Horario h = new Horario(este);
                    h.actualizarBotones();
                    //Codigo aqui... ahora debe crearse el spinner de seleccion multiple
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        actTodoLibres(v);


        return v;
    }

    private void actTodoLibres(View v){
        Horario h = new Horario(v);
        h.cargarTodosLibres(v.getContext());
        h.actualizarBotones();
    }
}
