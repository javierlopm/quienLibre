package net.ddns.javierlopm.quienlibre;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by javierlopm on 11/04/15.
 *
 * Basado en el video https://www.youtube.com/watch?v=pCz2znfwhGc
 * Implementacion en http://www.easyway2in.blogspot.ae/2014/07/android-swipe-views-example.html
 */
public class AdaptadorPaginas extends FragmentPagerAdapter {
    public AdaptadorPaginas(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public Fragment getItem(int objeto){
        switch(objeto){
            case 0:
                return new VisualizarHoraActual();
            case 1:
                return new VerPrincipal();
            case 2:
                return new VisualizarHorarioCompleto();
            default:
                return null;
        }
    }
}
