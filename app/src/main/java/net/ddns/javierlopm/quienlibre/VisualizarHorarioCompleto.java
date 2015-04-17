package net.ddns.javierlopm.quienlibre;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class VisualizarHorarioCompleto extends Fragment {

    //fragment_visualizar_horario_completo layout que se creo automaticamente
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View v = inflater.inflate(R.layout.activity_agregar_tabla_horario,container,false);

        v.findViewById(R.id.send).setVisibility(View.GONE);
        v.findViewById(R.id.spSeleccionarConsulta).setVisibility(View.VISIBLE);

        return v;
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

}
