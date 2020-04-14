package br.com.asilvadesa.searchonlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listaDeContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        listaDeContatos = new ArrayList<>(Arrays.asList("you", "yuo", "probably","despite", "moon", "misspellings"));
        final ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeContatos));

        EditText edit_text = findViewById(R.id.edit_text);

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<String> result = verificaPalavraNaLista(listaDeContatos, s.toString());
                if(result.size() > 0){
                    listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, result));
                }else{
                    listView.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaDeContatos));
                }

            }
        });
    }

    private static List<String> verificaPalavraNaLista(List listaDeContatos, String palavra_two) {
        List<String> listaDeRetorno = new ArrayList<>();
        for (Object contato: listaDeContatos){
            String palavra_one = (String) contato;
            if(verificaStringAproximada(palavra_one,palavra_two)){
                listaDeRetorno.add(palavra_one);
            }
        }
        return listaDeRetorno;
    }

    private static boolean verificaStringAproximada(String palavra_one, String palavra_two) {
        if (palavra_two == null || palavra_two.equals("")) return false;
        if(palavra_one.equals(palavra_two)) return true;
        try {
            if(palavra_one.substring(0,1).equals(palavra_two.substring(0,1)) && palavra_one.length() == palavra_two.length()){
                char[] chars_one = palavra_one.toCharArray();
                char[] chars_two = palavra_two.toCharArray();

                for (int index = 0; index < palavra_one.length(); index++){
                    if(chars_one[index] != chars_two[index]){
                        boolean cond1 = chars_one[index] == chars_two[index+1];
                        boolean cond2 = chars_one[index+1] == chars_two[index];
                        return cond1 && cond2;
                    }

                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
