package electric.cars.chargedcars.controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import electric.cars.chargedcars.fragment.AgregarPuntoFragment;
import electric.cars.chargedcars.fragment.PuntoRecargaFragment;
import electric.cars.chargedcars.fragment.UbicacionPuntoFragment;

public class PagerController extends FragmentPagerAdapter {

    private int numOfTabs;

    public PagerController(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm, numOfTabs);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AgregarPuntoFragment();
            case 1:
                return new PuntoRecargaFragment();
            case 2:
                return new UbicacionPuntoFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}

