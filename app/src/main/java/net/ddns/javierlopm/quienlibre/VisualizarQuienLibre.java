package net.ddns.javierlopm.quienlibre;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


public class VisualizarQuienLibre extends FragmentActivity {
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_quien_libre);
        vp = (ViewPager) findViewById(R.id.pager);
        AdaptadorPaginas ap = new AdaptadorPaginas(getSupportFragmentManager());
        vp.setAdapter(ap);
        vp.setCurrentItem(1);

    }


}
