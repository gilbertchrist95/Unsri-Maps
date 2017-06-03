package com.jogoler.jogolmaps;

import java.util.ArrayList;

/**
 * Created by Arief Wijaya on 13-Nov-16.
 */

public class MapsData {

    private static final int MARKER_ATM = R.drawable.atm;
    private static final int MARKER_BASKET = R.drawable.basket;
    private static final int MARKER_DEKANAT = R.drawable.dekanat;
    private static final int MARKER_FIRE_DEPARTMENT = R.drawable.firedepartment;
    private static final int MARKER_FOOTBALL = R.drawable.football;
    private static final int MARKER_GATE = R.drawable.gate;
    private static final int MARKER_HELIPAD = R.drawable.helipad;
    private static final int MARKER_MASJID = R.drawable.masjid;
    private static final int MARKER_MUSHOLA = R.drawable.mushola;
    private static final int MARKER_PARKIR = R.drawable.parkir;
    private static final int MARKER_POLIKLINIK = R.drawable.poliklinik;
    private static final int MARKER_RESTAURANT = R.drawable.restaurant;
    private static final int MARKER_TENNIS = R.drawable.tennis;
    private static final int MARKER_VOLLEY = R.drawable.volley;
    private static final int MARKER_MYLOCATION = R.drawable.mylocation;
    private static final int MARKER_NAVIGATION = R.drawable.navigation;
    private static final int MARKER_TARGETLOCATION = R.drawable.targetlocation;
    private static final int MARKER_OTHER = R.drawable.other_marker;

    public ArrayList<double[]> getLocationData()
    {
        ArrayList<double[]> points = new ArrayList<>();
        {
            points.add(new double[]{104.665089, -3.222219, MARKER_GATE}); //gate Rumah dinas
            points.add(new double[]{104.662969, -3.222486, MARKER_OTHER}); //rumah dinas
            points.add(new double[]{104.659406, -3.216644, MARKER_GATE}); //gate 3
            points.add(new double[]{104.660519, -3.218669, MARKER_PARKIR});//PARKIR STUDENT CENTER
            points.add(new double[]{104.657303, -3.222022, MARKER_OTHER});//APARTEMEN MAHASISWA
            points.add(new double[]{104.657389, -3.220522, MARKER_RESTAURANT}); //KANTIN ASRAMA MHS
            points.add(new double[]{104.657939, -3.220681, MARKER_OTHER});//ASRAMA
            points.add(new double[]{104.657486, -3.217044, MARKER_BASKET});//LAPANGAN BASKET
            points.add(new double[]{104.655047, -3.217781, MARKER_MASJID});//MASJID ALGHAZALI
            points.add(new double[]{104.654186, -3.216992, MARKER_OTHER});//TEATER
            points.add(new double[]{104.65415, -3.217653, MARKER_OTHER});//KANTOR ARSIP
            points.add(new double[]{104.652608, -3.217772, MARKER_OTHER});//LEMBAGA PENGABDIAN MASYARAKAT
            points.add(new double[]{104.651592, -3.221189, MARKER_PARKIR});//PARKIR FKIP
            points.add(new double[]{104.652011, -3.220481, MARKER_RESTAURANT});//KANTIN ILKOM
            points.add(new double[]{104.651914, -3.220142, MARKER_PARKIR});//PARKIR ILKOM
            points.add(new double[]{104.651692, -3.219756, MARKER_OTHER});//FASILKOM
            points.add(new double[]{104.6512, -3.219358, MARKER_DEKANAT});//DEKANAT ILKOM
            points.add(new double[]{104.651411, -3.218911, MARKER_PARKIR});//PARKIR ILKOM DAN EKONOMI
            points.add(new double[]{104.651172, -3.218083, MARKER_PARKIR});//PARKIR FAKULTAS EKONOMI
            points.add(new double[]{104.650219, -3.218283, MARKER_MUSHOLA});//MUSHOLA EKONOMI
            points.add(new double[]{104.650781, -3.21805, MARKER_DEKANAT});//DEKANAT EKONOMI
            points.add(new double[]{104.650842, -3.21775, MARKER_OTHER});//FAKULTAS EKONOMI
            points.add(new double[]{104.649986, -3.216467, MARKER_RESTAURANT});//KANTIN
            points.add(new double[]{104.649978, -3.216819, MARKER_OTHER});//HALTE
            points.add(new double[]{104.652386, -3.216969, MARKER_OTHER});//FISIP
            points.add(new double[]{104.651589, -3.216597, MARKER_PARKIR});//PARKIR FISIP
            points.add(new double[]{104.651208, -3.216492, MARKER_DEKANAT});//DEKANAT FISIP
            points.add(new double[]{104.651364, -3.216006, MARKER_RESTAURANT});//KANTIN HUKUM
            points.add(new double[]{104.651175, -3.2152, MARKER_OTHER});//HUKUM
            points.add(new double[]{104.651014, -3.215419, MARKER_OTHER});//HALTE
            points.add(new double[]{104.651564, -3.214942, MARKER_PARKIR});//PARKIR 2 HUKUM
            points.add(new double[]{104.651253, -3.214614, MARKER_PARKIR});//PARKIR 1 HUKUM
            points.add(new double[]{104.651503, -3.214211, MARKER_FOOTBALL});//BOLA HUKUM
            points.add(new double[]{104.650486, -3.215569, MARKER_DEKANAT});//DEKANAT HUKUM
            points.add(new double[]{104.650586, -3.215156, MARKER_MUSHOLA});//MUSHOLA HUKUM
            points.add(new double[]{104.649858, -3.214547, MARKER_PARKIR});//PARKIR
            points.add(new double[]{104.649386, -3.213522, MARKER_HELIPAD});//HELIPAD
            points.add(new double[]{104.649203, -3.214619, MARKER_OTHER});//REKTORAT
            points.add(new double[]{104.647478, -3.214094, MARKER_PARKIR});//PARKIR
            points.add(new double[]{104.648083, -3.215144, MARKER_PARKIR});//PARKIR
            points.add(new double[]{104.647569, -3.215972, MARKER_OTHER});//HALTE
            points.add(new double[]{104.648125, -3.215797, MARKER_OTHER});//LEMBAGA BAHASA
            points.add(new double[]{104.648739, -3.216292, MARKER_OTHER});//HALTE TRANSMUSI
            points.add(new double[]{104.648794, -3.217794, MARKER_OTHER});//LEMLIT INSTITUTE
            points.add(new double[]{104.649169, -3.217175, MARKER_OTHER});//PERPUSTAKAAN
            points.add(new double[]{104.648483, -3.216964, MARKER_OTHER});//ICT
            points.add(new double[]{104.647864, -3.217178, MARKER_OTHER});//GRAHA
            points.add(new double[]{104.6479, -3.218258, MARKER_RESTAURANT});//KANTIN
            points.add(new double[]{104.648414, -3.219217, MARKER_GATE});//POS SATPAM PERTANIAN
            points.add(new double[]{104.648372, -3.219008, MARKER_MUSHOLA});//MUSHOLA PERTANIAN
            points.add(new double[]{104.64895, -3.218522, MARKER_RESTAURANT});//KEDAI KAMPUS
            points.add(new double[]{104.650158, -3.218861, MARKER_PARKIR});//PARKIR TERMINAL BIS
            points.add(new double[]{104.649531, -3.218986, MARKER_OTHER});//TERMINAL BIS
            points.add(new double[]{104.650181, -3.223639, MARKER_OTHER});//PERKEMAHAN
            points.add(new double[]{104.650742, -3.222772, MARKER_GATE});//GATE PERKEMAHAN
            points.add(new double[]{104.6506, -3.219783, MARKER_VOLLEY});//VOLLEY FKIP
            points.add(new double[]{104.649833, -3.219722, MARKER_FOOTBALL});//BOLA FKIP
            points.add(new double[]{104.650056, -3.22005, MARKER_BASKET});//BASKET FKIP
            points.add(new double[]{104.649328, -3.220647, MARKER_GATE});//POST SATPAM FKIP
            points.add(new double[]{104.649886, -3.220561, MARKER_MUSHOLA});//MUSHOLA FKIP
            points.add(new double[]{104.649769, -3.220256, MARKER_OTHER});//FKIP
            points.add(new double[]{104.649333, -3.219842, MARKER_DEKANAT});//DEKANAT FKIP
            points.add(new double[]{104.648669, -3.220353, MARKER_PARKIR});//PARKIR
            points.add(new double[]{104.648497, -3.219839, MARKER_FOOTBALL});//BOLA PERTANIAN
            points.add(new double[]{104.6479, -3.220053, MARKER_PARKIR});//PARKIR PERTANIAN
            points.add(new double[]{104.648147, -3.219864, MARKER_DEKANAT});//DEKANAT PERTANIAN
            points.add(new double[]{104.646825, -3.218981, MARKER_RESTAURANT});//KANTIN FMIPA
            points.add(new double[]{104.647497, -3.220067, MARKER_OTHER});//PERTANIAN
            points.add(new double[]{104.646803, -3.220722, MARKER_MUSHOLA});//MUSHOLA PERTANIAN
            points.add(new double[]{104.647372, -3.221108, MARKER_OTHER});//LAB TERNAK
            points.add(new double[]{104.647536, -3.223353, MARKER_OTHER});//KEBUN KARET
            points.add(new double[]{104.646378, -3.221644, MARKER_OTHER});//KEBUN PERCOBAAN
            points.add(new double[]{104.645392, -3.223522, MARKER_OTHER});//KEBUN KELAPA SAWIT
            points.add(new double[]{104.644114, -3.222389, MARKER_OTHER});//ABORETUM
            points.add(new double[]{104.646192, -3.221019, MARKER_OTHER});//LAB KLIMATOLOGI
            points.add(new double[]{104.645914, -3.220167, MARKER_FOOTBALL});//BOLA FMIPA
            points.add(new double[]{104.644444, -3.217706, MARKER_PARKIR});//PARKIR
            points.add(new double[]{104.646158, -3.218661, MARKER_DEKANAT});//DEKANAT  FMIPA
            points.add(new double[]{104.645861, -3.218869, MARKER_MUSHOLA});//MUSHOLA FMIPA
            points.add(new double[]{104.646247, -3.219067, MARKER_PARKIR});//PARKIR FMIPA
            points.add(new double[]{104.645756, -3.219306, MARKER_OTHER});//FMIPA
            points.add(new double[]{104.645872, -3.217747, MARKER_BASKET});//BASKET
            points.add(new double[]{104.646125, -3.217417, MARKER_DEKANAT});//DEKANAT TEKNIK
            points.add(new double[]{104.645069, -3.217278, MARKER_OTHER});//FAKULTAS TEKNIK
            points.add(new double[]{104.646239, -3.216939, MARKER_MUSHOLA});//MUSHOLA TEKNIK
            points.add(new double[]{104.646172, -3.216681, MARKER_FOOTBALL});//LAPANGAN BOLA
            points.add(new double[]{104.646892, -3.215989, MARKER_DEKANAT});//DEKANAT KEDOKTERAN
            points.add(new double[]{104.647222, -3.215372, MARKER_OTHER});//PRODI KEDOKTERAN GIGI
            points.add(new double[]{104.645997, -3.215672, MARKER_OTHER});//PRODI PSIKOLOGI
            points.add(new double[]{104.646553, -3.215661, MARKER_OTHER});//KEDOKTERAN
            points.add(new double[]{104.646075, -3.216119, MARKER_MUSHOLA});//MUSHOLA KEDOKTERAN
            points.add(new double[]{104.645894, -3.216647, MARKER_OTHER});//GRAHA PATRA KEMIKA
            points.add(new double[]{104.648894, -3.211236, MARKER_GATE});//GATE 1
            points.add(new double[]{104.644667, -3.2147, MARKER_OTHER});//PRODI KEPERAWATAM
            points.add(new double[]{104.646639, -3.213464, MARKER_TENNIS});//LAPANGAN TENIS
            points.add(new double[]{104.645619, -3.213508, MARKER_OTHER});//PRODI KEPERAWATAN
            points.add(new double[]{104.644814, -3.21355, MARKER_PARKIR});//PARKIR FKM
            points.add(new double[]{104.644958, -3.213081, MARKER_OTHER});//FKM
            points.add(new double[]{104.645664, -3.210433, MARKER_GATE});//GATE 2
            points.add(new double[]{104.645394, -3.210881, MARKER_FIRE_DEPARTMENT});//PBK
            points.add(new double[]{104.646094, -3.210864, MARKER_POLIKLINIK});//POLIKLINIK
            points.add(new double[]{104.648361, -3.210911, MARKER_ATM});//ATM
            points.add(new double[]{104.659606, -3.218901, MARKER_OTHER});//STUDENT CENTER
            points.add(new double[]{104.648146, -3.214337, MARKER_OTHER});//AUDITORIUM
            points.add(new double[]{104.658244, -3.21865, MARKER_FOOTBALL});//LAPANGAN SEPAK BOL
        }
        return points;
    }
}
