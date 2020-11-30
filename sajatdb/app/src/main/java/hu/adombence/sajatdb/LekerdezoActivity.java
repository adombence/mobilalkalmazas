package hu.adombence.sajatdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LekerdezoActivity extends AppCompatActivity {

    Button btn_keres;
    EditText et_keresendo;
    ListView listv;
    ArrayList<Ember> lista = new ArrayList<Ember>();

    private class SajatAdapter extends ArrayAdapter<Ember> {

        public SajatAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        public SajatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ember> lista) {
            super(context, resource, lista);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Ember uj = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_elem, parent, false);
            }

            TextView nev = convertView.findViewById(R.id.textView_lista_nev);
            TextView szev = convertView.findViewById(R.id.textView_lista_szev);

            nev.setText(uj.getNev());
            szev.setText(uj.getSzev() + "");

            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekerdezo);
        btn_keres = this.findViewById(R.id.btn_keres);
        et_keresendo = this.findViewById(R.id.editText_keresendo);
        listv = this.findViewById(R.id.listView_talalatok);

        ArrayAdapter<Ember> adapter_lista = new ArrayAdapter<Ember>(this, R.layout.lista_elem, R.id.textView_lista_nev, lista);
        listv.setAdapter(adapter_lista);

        listv.setOnItemClickListener((adapterView, view, i, l) ->
                Toast.makeText(this, "Kiválaszott: " + lista.get(i).getNev(), Toast.LENGTH_SHORT).show());

        btn_keres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lista.isEmpty()) {
                    lista.clear();
                    adapter_lista.notifyDataSetChanged();
                }

                String mit = et_keresendo.getText().toString();

                Cursor resultSet = MainActivity.adatbazis.rawQuery("SELECT " + MainActivity.COL_NEV + ", "
                        + MainActivity.COL_SZEV + " FROM " + MainActivity.TABLE_NAME + " WHERE " + MainActivity.COL_NEV
                        + " like '" + mit + "%' ORDER BY " + MainActivity.COL_NEV + ";", null);

                /*Cursor resultSet2 = MainActivity.adatbazis.query(MainActivity.TABLE_NAME, new String[]{MainActivity.COL_NEV, MainActivity.COL_SZEV},
                        MainActivity.COL_NEV + " like '" + mit + "%' ", null, null, null, MainActivity.COL_NEV, null);*/

                resultSet.moveToFirst();
                while (resultSet.isAfterLast() == false) {
                    Ember uj = new Ember(resultSet.getString(0), resultSet.getInt(1));
                    lista.add(uj);
                    resultSet.moveToNext();
                }

                adapter_lista.notifyDataSetChanged();
            }
        });
    }
}