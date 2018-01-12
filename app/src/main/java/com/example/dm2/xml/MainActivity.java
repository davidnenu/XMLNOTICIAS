package com.example.dm2.xml;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lstWeb;
    List noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstWeb=findViewById(R.id.lstWeb);



        CargarXmlTask tarea = new CargarXmlTask();
        tarea.execute("http://www.europapress.es/rss/rss.aspx");




        //Eventos
        lstWeb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {

                String url =
                        ((Web)a.getItemAtPosition(position)).getUrl();
                //long opcion = a.getItemIdAtPosition(position);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


    }

    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {
        protected Boolean doInBackground(String... params) {
            RssParserSax saxparser = new RssParserSax(params[0]);
            noticias = saxparser.parse();
            //Log.i("aa", "Tamaño noticias: "+noticias.size());
            return true;
        }

        protected void onPostExecute(Boolean result) {

            AdaptadorFavoritos adaptador =
                    new AdaptadorFavoritos(MainActivity.this, noticias);
            //lstWeb = (ListView)findViewById(R.id.lstWeb);
            lstWeb.setAdapter(adaptador);
        }
    }

    class AdaptadorFavoritos extends ArrayAdapter<Web> {

        public AdaptadorFavoritos(Context context, List dat) {
            super(context, R.layout.listitem_web,dat);

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.listitem_web, null);

            TextView lblNombre = (TextView) item.findViewById(R.id.lblNombre);
            lblNombre.setText(super.getItem(position).getNombre());

            TextView lblUrl =
                    (TextView) item.findViewById(R.id.lblUrl);
            lblUrl.setText(super.getItem(position).getUrl());


            return (item);
        }

    }

}
