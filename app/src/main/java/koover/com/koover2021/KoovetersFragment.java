package koover.com.koover2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class KoovetersFragment extends Fragment {
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng userLatLong;
    Marker userMarker;
    String userAddress;

    GoogleMap nMap;

    public ArrayList<DynamicRVModel> kooveters;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            nMap = googleMap;

            //adapter to show marker info box
            nMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    Context context = getContext(); //or getActivity(), YourActivity.this, etc.

                    LinearLayout info = new LinearLayout(context);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(context);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(context);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    Button verPerfil = new Button(context);
                    verPerfil.setText("Ver perfil");
                    verPerfil.setBackgroundColor(Color.rgb(27,94,32));
                    verPerfil.setTextColor(Color.WHITE);

                    info.addView(title);
                    info.addView(snippet);
                    info.addView(verPerfil);

                    return info;
                }
            });

            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    // store user latlong
                    userLatLong = new LatLng(location.getLatitude(),location.getLongitude());

                    userMarker.setPosition(userLatLong);

                    //nMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
                }
            };

            askLocationPermission();

            loadKoovetersData();

            Iterator<DynamicRVModel> iterator = kooveters.iterator();

            while (iterator.hasNext()) {
                DynamicRVModel kooveter = iterator.next();

                // calculate distance
                Location userLoc = new Location("Marker");
                userLoc.setLatitude(userLatLong.latitude);
                userLoc.setLongitude(userLatLong.longitude);

                Location kooveterLoc = new Location("Kooveter");
                kooveterLoc.setLatitude(kooveter.getLatLong().latitude);
                kooveterLoc.setLongitude(kooveter.getLatLong().longitude);

                Float distance = userLoc.distanceTo(kooveterLoc);

                DecimalFormat df = new DecimalFormat("0.00");
                String distance_final = df.format(distance / 1000);

                // generate small icon
                int height = 100;
                int width = 100;
                BitmapDrawable bitmapdraw;

                switch(kooveter.getPos()) {
                    case 1:
                        bitmapdraw= (BitmapDrawable)getResources().getDrawable(R.drawable.doctor);
                        break;
                    case 2:
                        bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.teacher);
                        break;
                    case 3:
                        bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.delivery);
                        break;
                    case 4:
                        bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.lawyer);
                        break;
                    default:
                        bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.worker);
                }

                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                kooveter.setAddress(getAddressFromLocation(kooveter.getLatLong()));

                String title = kooveter.getName()
                        .concat(" - ")
                        .concat(kooveter.getDetails())
                        .concat(" (")
                        .concat(distance_final)
                        .concat(" km)");

                nMap.addMarker(new MarkerOptions()
                        .position(kooveter.getLatLong())
                        .title(title)
                        .snippet(kooveter.getAddress())
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                );

                //System.out.println(kooveter.getName());
            }
        }
    };

    private void askLocationPermission() {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                // getting user last location to set the default location marker in the map
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                userLatLong = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                userAddress = getAddressFromLocation(userLatLong);

                nMap.getUiSettings().setZoomControlsEnabled(true);

                userMarker = nMap.addMarker(new MarkerOptions()
                        .position(userLatLong)
                        .title("Aquí estás tú")
                        .snippet(userAddress)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                );

                nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 13.0f));
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private String getAddressFromLocation(LatLng latlon) {

        Geocoder geocoder = new Geocoder(getContext());

        try {
            List<Address> addresses = geocoder.getFromLocation(latlon.latitude, latlon.longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                String address = fetchedAddress.getAddressLine(0).toString();

                String[] address_split = address.split(",");

                return address_split[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error obteniendo dirección!");
        }

        return "Dirección no encontrada...";
    }

    private void loadKoovetersData(){
        kooveters = new ArrayList<DynamicRVModel>();
        kooveters.add(new DynamicRVModel("Juan", "Psicologo", R.drawable.deliveryman, R.drawable.salud, 1, new LatLng(-34.8871823, -56.1734712)));
        kooveters.add(new DynamicRVModel("Susana", "Fisioterapeuta", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.8796339, -56.1688165)));
        kooveters.add(new DynamicRVModel("Pedro", "Medico General", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.877413137890876, -56.1386855591942)));
        kooveters.add(new DynamicRVModel("Ernestina", "Pediatra", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.9120914, -56.1961762)));
        kooveters.add(new DynamicRVModel("Romulo", "Nutricionista", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.8970682, -56.1471212)));
        kooveters.add(new DynamicRVModel("Romualdo", "Enfermero", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.86855115154034, -56.21430913913508)));
        kooveters.add(new DynamicRVModel("Cristina", "Psicopedagoga", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.854676267447466, -56.131870539559586)));
        kooveters.add(new DynamicRVModel("Jorge", "Psicomotricista", R.drawable.deliveryman2, R.drawable.salud, 1, new LatLng(-34.84721002822779, -56.14320019058114)));

        kooveters.add(new DynamicRVModel("Alfredo", "Maestro Preescolar", R.drawable.deliveryman,R.drawable.educacionbg, 2, new LatLng(-34.9100056, -56.1591138)));
        kooveters.add(new DynamicRVModel("Teresita", "Profesora de Fisica", R.drawable.deliveryman2,R.drawable.educacionbg, 2, new LatLng(-34.8790923, -56.1847626)));
        kooveters.add(new DynamicRVModel("Mary", "Maestra de Primaria", R.drawable.deliveryman,R.drawable.educacionbg, 2, new LatLng(-34.8806832, -56.1703492)));
        kooveters.add(new DynamicRVModel("Elsa", "Educadora en Primera Infancia", R.drawable.deliveryman2,R.drawable.educacionbg, 2, new LatLng(-34.9130021, -56.1533654)));
        kooveters.add(new DynamicRVModel("Nicolas", "Profesor de Matematica", R.drawable.deliveryman,R.drawable.educacionbg, 2, new LatLng(-34.8764969, -56.1472055)));
        kooveters.add(new DynamicRVModel("Alberto", "Prfoesor de Literatura", R.drawable.deliveryman2,R.drawable.educacionbg, 2, new LatLng(-34.8627277, -56.1522711)));

        kooveters.add(new DynamicRVModel("Juan", "Delivery", R.drawable.deliveryman, R.drawable.deliverymoto, 3, new LatLng(-34.8948847, -56.1574818)));
        kooveters.add(new DynamicRVModel("Jonny", "Delivery", R.drawable.deliveryman2, R.drawable.deliverymoto, 3, new LatLng(-34.9006967, -56.1840606)));
        kooveters.add(new DynamicRVModel("Christofer", "Delivery", R.drawable.deliveryman, R.drawable.deliverymoto, 3, new LatLng(-34.8764969, -56.1472055)));
        kooveters.add(new DynamicRVModel("Juan Carlos", "Delivery", R.drawable.deliveryman2, R.drawable.deliverymoto, 3, new LatLng(-34.9173085, -56.1545187)));
        kooveters.add(new DynamicRVModel("Roberto", "Delivery", R.drawable.deliveryman, R.drawable.deliverymoto, 3, new LatLng(-34.8682208, -56.1703008)));
        kooveters.add(new DynamicRVModel("Fernando", "Delivery", R.drawable.deliveryman2, R.drawable.deliverymoto, 3, new LatLng(-34.908196227172624, -56.20534698142133)));

        kooveters.add(new DynamicRVModel("Juan Andres", "Abogado", R.drawable.deliveryman, R.drawable.profesionales, 4, new LatLng(-34.8994634, -56.1780453)));
        kooveters.add(new DynamicRVModel("Bruno", "Contador", R.drawable.deliveryman2, R.drawable.profesionales, 4, new LatLng(-34.911713, -56.1838953)));
        kooveters.add(new DynamicRVModel("Lourdes", "Escribana", R.drawable.deliveryman,R.drawable.profesionales,  4, new LatLng(-34.8900544, -56.1966421)));
        kooveters.add(new DynamicRVModel("Adriana", "Arquitecta", R.drawable.deliveryman2,R.drawable.profesionales,  4, new LatLng(-34.9260956, -56.1677233)));
        kooveters.add(new DynamicRVModel("Diego", "Gestor", R.drawable.deliveryman,R.drawable.profesionales,  4, new LatLng(-34.8654615, -56.1985661)));
        kooveters.add(new DynamicRVModel("Fernanda", "Ing. Agronoma", R.drawable.deliveryman2, R.drawable.profesionales, 4, new LatLng(-34.87938686870341, -56.11541679306429)));

        kooveters.add(new DynamicRVModel("Sandra", "Acompañante Especializada", R.drawable.deliveryman,R.drawable.otrosbg, 5, new LatLng(-34.8879716, -56.1874992)));
        kooveters.add(new DynamicRVModel("Micaela", "Cocinera", R.drawable.deliveryman2,R.drawable.otrosbg, 5, new LatLng(-34.9142463, -56.1767327)));
        kooveters.add(new DynamicRVModel("Jessica", "Baby Sister", R.drawable.deliveryman,R.drawable.otrosbg, 5, new LatLng(-34.9041639, -56.197949)));
        kooveters.add(new DynamicRVModel("Martin", "Albañil", R.drawable.deliveryman2,R.drawable.otrosbg, 5, new LatLng(-34.9081238, -56.1438718)));
        kooveters.add(new DynamicRVModel("Fernando", "Electricista", R.drawable.deliveryman,R.drawable.otrosbg, 5, new LatLng(-34.8781236, -56.195605)));
        kooveters.add(new DynamicRVModel("Manuel", "Jardinero", R.drawable.deliveryman2,R.drawable.otrosbg, 5, new LatLng(-34.89099102948133, -56.10617842901086)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kooveters_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}