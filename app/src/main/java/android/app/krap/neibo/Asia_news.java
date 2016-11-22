package android.app.krap.neibo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by Android on 11/17/2016.
 */
public class Asia_news extends Activity {

    LinearLayout linearAsia1,linearAsia2,linearAsia3,linearAsia4,linearAsia5;
ImageView backasia,img_news,img_asianews,img_atimes,img_cnnndiditral;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_asia);

        Common_methods.showProgressDialog(Asia_news.this);

        backasia =(ImageView) findViewById(R.id.backasia);
        linearAsia1 =(LinearLayout) findViewById(R.id.linearAsia1);
        linearAsia2 =(LinearLayout) findViewById(R.id.linearAsia2);
        linearAsia3 =(LinearLayout) findViewById(R.id.linearAsia3);
        linearAsia4 =(LinearLayout) findViewById(R.id.linearAsia4);
        linearAsia5 =(LinearLayout) findViewById(R.id.linearAsia5);

        img_asianews =(ImageView) findViewById(R.id.img_asianews);
        img_news =(ImageView) findViewById(R.id.img_news);
        img_atimes =(ImageView) findViewById(R.id.img_atimes);
        img_cnnndiditral =(ImageView) findViewById(R.id.img_cnnndiditral);

         new  Common_methods.BackTask(Asia_news.this,img_news,"http://www.financeasia.com/RSS/Default.aspx").execute();
        new  Common_methods.BackTask(Asia_news.this,img_asianews,"http://www.asianewsnet.net/rss.html").execute();
        new  Common_methods.BackTask(Asia_news.this,img_atimes,"http://www.atimes.com/rss-feeds/").execute();
        new  Common_methods.BackTask(Asia_news.this,img_atimes,"http://rss.cnn.com/rss/edition_asia.rss").execute();

        backasia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        GetNewsAsian asiaNews = new GetNewsAsian();
        asiaNews.execute();

    }


    public class GetNewsAsian extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            String url ="http://www.channelnewsasia.com/rss/latest_cna_asiapac_rss.xml";

            String response = Common_methods.getResponseGet(url);
            return  response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Common_methods.showDialog.dismiss();

            if(s!=null){
                XMLParser parse = new XMLParser();
                Document doc = parse.getDomElement(s);
                NodeList nodeList = doc.getElementsByTagName("item");
                int length = nodeList.getLength();

                if(length!=0){
                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Element terrif = (Element) nodeList.item(i);

                        String title = terrif.getElementsByTagName("title").item(0).getTextContent();


                        String description = terrif.getElementsByTagName("description").item(0).getTextContent();

                        String urllink = terrif.getElementsByTagName("link").item(0).getTextContent();

                        String pubDate =terrif.getElementsByTagName("pubDate").item(0).getTextContent();

                        //  String url = Common_methods.GetUrls(urllink);

                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.news_detail, null);
                        TextView news_heading = (TextView) v.findViewById(R.id.news_heading);
                        TextView news_time = (TextView) v.findViewById(R.id.news_time);
                        ImageView news_icon = (ImageView) v.findViewById(R.id.news_icon);
                        TextView news_DETAIL =(TextView) v.findViewById(R.id.news_DETAIL);

                        news_heading.setText(title);
                        news_DETAIL.setText(description);
                        news_time.setText("Published On: " +pubDate);



                        linearAsia1.addView(v);

                        new Common_methods.BackTask(Asia_news.this,news_icon,urllink).execute();

                    }

                }
            }
            else{
                Toast.makeText(Asia_news.this, s, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Common_methods.showDialog.show();
        }
    }
}
