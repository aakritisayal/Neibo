package android.app.krap.neibo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by Android on 11/29/2016.
 */
public class Book_table extends Fragment {

    ArrayList<String> listUrlLinks = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
LinearLayout linearTable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.book_table, null);
        linearTable=(LinearLayout) v.findViewById(R.id.linearTable);


        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/04/Salmon_jpg_1429256818.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/05/4G_Rack_Of_Lamb_jpg_1431938626.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Web_slider2_jpg_1479702392.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Bar_Area_jpg_1441950641.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/12/Margherita_Pizza_jpg_1449992433.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Curry_jpg_1444297706.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/04/Lobster_jpg_1429265353.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/10/Web_Slider_3_jpg_1476086547.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Noodle_jpg_1443696866.jpg?date=20161110");
        listUrlLinks.add("http://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Champagne_jpg_1446773390.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/BarSu_Cocktail_jpg_1438614702.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Salmon_JPG_1438611337.JPG?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/See_Krong_Mee_Krob_Wan_jpg_1433501196.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/Tuna_Tartare_JPG_1433410359.JPG?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/12/Foie_Gras_jpg_1449033207.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/04/Vertigo_Web_Slider_jpg_1461555503.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Tournedos_Rossini_jpg_1447915402.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Chicken_jpg_1446188550.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Food_Spread_jpg_1447759520.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/04/Lobster_jpg_1429673277.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Lobster_jpg_1446182827.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Exterior_jpg_1441370745.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/04/Dining_Area_jpg_1460021668.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Seven_Spoon_jpg_1479705663.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Main_Dish_jpg_1441024434.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Seafood2_jpg_1442577062.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/DID_offers_surprise_menus_reve_1438613744.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Atmosphere_jpg_1441881995.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Beer_Ice_Cream_jpg_1441372682.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Duck_Dish_jpg_1441960082.jpg?date=20161110");
        listUrlLinks.add("http://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Thai_Tea_jpg_1446774460.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Chicken_Soup_Noodle_jpg_1447923919.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Buffet_Line_jpg_1446196103.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Lobsters_jpg_1448465690.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Tajima_Wagyu_jpg_1446183804.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Buffet_Line_jpg_1445486608.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/02/Salad_jpg_1455783608.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Roasted_Cod_Fish_jpg_1448464731.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/Australian_Lamb_Rump_jpg_1433834808.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/09/Cheese_Platter_jpg_1475223790.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Buffet_jpg_1448463888.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/10/Web_Slider_3_jpg_1476086547.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Egg_Benedict_jpg_1444108040.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/07/Fish_and_Chips_jpg_1436500578.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Spaghetti_shot_jpg_1439814671.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/04/Indus_Kabab_e_Malai_0_jpg_1429690432.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Ever_jpg_1479705359.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Sashimi_jpg_1446722851.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/04/Dish_jpg_1429694934.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/04/Vertigo_Web_Slider_jpg_1461555503.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Seven_Spoon_jpg_1479705663.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Beef_jpg_1445487039.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Fish_jpg_1441961228.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Sirloin_jpg_1442569101.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/05/LD57_Rice_Noodles_jpg_1432624629.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Dessert_jpg_1441368393.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Beef_Wellington_jpg_1448462897.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/Madison_Food_Shot_jpg_1433414987.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Lamb_Korma_jpg_1441368996.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Veg_Fried_Rice_jpg_1441370211.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Veg_Pakoras_jpg_1441369564.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Keema_Mutter_jpg_1441369885.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Duck_jpg_1447926534.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/05/Steak_jpg_1431934716.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/12/Khao_Kluk_Kapi_jpg_1449805780.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Food_jpg_1442580505.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Web_slider2_jpg_1479702392.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Set_Menu_jpg_1440053941.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Food_Spread_jpg_1448464312.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/04/Vertigo_Web_Slider_jpg_1461555503.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Seafood_Buffet_jpg_1438615485.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Green_Curry_jpg_1438685926.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Seven_Spoon_jpg_1479705663.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/09/Beef_jpg_1441953735.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/Park_Society_Grass_Ocean_JPG_1433413076.JPG?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Web_slider2_jpg_1479702392.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/11/Seven_Spoon_jpg_1479705663.jpg");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Burrata_Cheese_jpg_1446464531.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/07/Eggs_jpg_1436845062.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/04/Prawns_jpg_1429773982.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/Red_Oven_Buffet_Spread_jpg_1433412654.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/07/5_jpg_1437539638.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Tiger_Prawn_Salad_jpg_1447935508.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/360_View_jpg_1438847565.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/08/Cold_Cut_jpg_1438616008.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/10/Food_jpg_1443698292.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Food_Spread_jpg_1447939350.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Food_Spread_jpg_1447919720.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2016/01/Shrimp_Dish_jpg_1452854458.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/12/Fish_Dish_jpg_1449722713.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Fish_jpg_1446723978.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/12/Food_jpg_1450333128.jpg?date=20161110");
        listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/11/Fish_Dish_jpg_1448613384.jpg?date=20161110");
      //  listUrlLinks.add("https://d2jzxcrnybzkkt.cloudfront.net/uploads/2015/06/Salmon_Squid_Ink_Cone_jpg_1433410414.jpg?date=20161110");


        listName.add("22 Kitchen & Bar");
        listName.add("4Garcons");
        listName.add("abc Essence in Eatery");
        listName.add("Ambar (Fourpoints by Sheraton)");
        listName.add("Angelini (Shangri-la) - renamed to Volti");
        listName.add("Apsara (Banyantree)");
        listName.add("Artur");
        listName.add("Aston");
        listName.add("Baiyun (Banyantree)");
        listName.add("Bar 494 (Grand Hyatt)");
        listName.add("Barsu");
        listName.add("Basil");
        listName.add("Benjarong Restaurant");
        listName.add("Biscotti");
        listName.add("Brasserie 9");
        listName.add("Brasserie Europa");
        listName.add("Brio (Anantara Riverside)");
        listName.add("Cafe G (Ihg)");
        listName.add("Chakrabongse Dining");
        listName.add("Charcoal Tandoor Grill & Mixology");
        listName.add("Charm Thai (Ihg)");
        listName.add("Chez Pape");
        listName.add("Ciao Terraza (Mandarin Oriental)");
        listName.add("Coconut  Terrace (Ramada)");
        listName.add("Da vinci (Rembrandt)");
        listName.add("Dee Lite");
        listName.add("Dine in the Dark");
        listName.add("Divino");
        listName.add("Drunken Leprechaun (Foupoints by Sheraton)");
        listName.add("Empire Dining");
        listName.add("Erawan TearoomGrand Hyatt");
        listName.add("Err URban Rustic Thai");
        listName.add("Espresso (Ihg)");
        listName.add("Feast (sheraton)");
        listName.add("Fireplace Grill and Bar (Ihg)");
        listName.add("Flavors(Renaissance BKK)");
        listName.add("Gianni Ristorante");
        listName.add("Giorgio’s (Italian) Sheraton");
        listName.add("Hamilton's Steak House");
        listName.add("Harvest");
        listName.add("Horizon Cruise (Shangri-la)");
        listName.add("HOT ROD");
        listName.add("Hugo Bar & Eatery");
        listName.add("Huntsman Bar");
        listName.add("Indique");
        listName.add("Indus Contemporary Indian Dining");
        listName.add("Jubei Izakaya");
        listName.add("Kisso (Westin Grande)");
        listName.add("La Bottega Bangkok");
        listName.add("La monita taqueria");
        listName.add("La monita Urban");
        listName.add("La VIE (VIE Hotel)");
        listName.add("Lady Brett");
        listName.add("Le Dalat");
        listName.add("Le Du");
        listName.add("Le Petit Zinc (Italics)");
        listName.add("Madison");
        listName.add("Masala art");
        listName.add("Masala Narathiwas 24");
        listName.add("Masala Silom");
        listName.add("Masala Sukhumvit");
        listName.add("Mei Jiang- Peninsula");
        listName.add("Melange Restaurant");
        listName.add("Metro on Wireless (Hotel Indigo Bangkok Wireless Road)");
        listName.add("Mondo");
        listName.add("Moulin");
        listName.add("Myra");
        listName.add("Next 2 Cafe (Shangri-la)");
        listName.add("Niche");
        listName.add("Orchid Cafe");
        listName.add("Osha Bangkok");
        listName.add("Osito");
        listName.add("Paris Bangkok");
        listName.add("Park Society");
        listName.add("Paste Bangkok (Gaysorn)");
        listName.add("Paste Bangkok (Sukhumvit)");
        listName.add("Peppina");
        listName.add("Quince");
        listName.add("Rang Mahal (Rembrandt)");
        listName.add("Red Oven");
        listName.add("Rib Room Bar & Steakhouse");
        listName.add("River Cafe & Terrace- Peninsula");
        listName.add("Roof (Mode Sathorn)");
        listName.add("Rossini's");
        listName.add("Saffron (Banyantree)");
        listName.add("Sala Rim Naam (MO)");
        listName.add("Salathip (Shangri-la)");
        listName.add("Sambal (International) Sheraton (renamed to Riverside Grill)");
        listName.add("Scalini");
        listName.add("Seasonal Tastes (Westin Grande)");
        listName.add("Senor Pico (Rembrandt) - renamed to Mexicano");
        listName.add("Shang Palace (Shangri-la)");
        listName.add("Shintaro");


        int size = listName.size();
        int si = listUrlLinks.size();

        Log.e("size", "" + si);


        for (int i=0;i<listName.size();i++){

            String name = listName.get(i);
            String url = listUrlLinks.get(i);

            if(url.contains(" ")){
                url =url.replaceAll(" ","");
            }



            LayoutInflater inflate = getActivity().getLayoutInflater();
            View view = inflate.inflate(R.layout.custom_book_table, null);
            TextView txtRestName =(TextView) view.findViewById(R.id.txtRestName);
            ImageView icon_rest =(ImageView) view.findViewById(R.id.icon_rest);

            UrlImageViewHelper.setUrlDrawable(icon_rest,url,R.drawable.find_facebook);
            txtRestName.setText(name);



            linearTable.addView(view);

        }


        return v;
    }
}
