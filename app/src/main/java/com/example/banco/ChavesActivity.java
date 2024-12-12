package com.example.banco;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ChavesActivity extends AppCompatActivity {
    RepositorioPix repositorioPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chaves);

        repositorioPix = new RepositorioPix(this);
        ListView listView = findViewById(R.id.editListChaves);

        List<Pix> chavesPix = repositorioPix.listarChaves();

        ArrayAdapter<Pix> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, chavesPix);

        listView.setAdapter(adapter);

    }

    public void remover(View view) {
        EditText editText = findViewById(R.id.editTextRemover);
        String idString = editText.getText().toString();

        // Verifica se o campo está vazio
        if (idString.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um ID", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            // conversão do id para um numero inteiro
            Integer id = Integer.parseInt(idString);

            // aqui listei todas nossas chaves
            List<Pix> listaPix = repositorioPix.listarChaves();

            // procuro no banco o pix com o id digitado
            Pix pix = null;
            for (Pix p : listaPix) {
                if (p.id == id) {
                    pix = p;
                    break;
                }
            }
            // Verifica se o Pix foi encontrado em nosso banco de dados
            if (pix == null) {
                Toast.makeText(this, "ID não encontrado", Toast.LENGTH_LONG).show();
                return;
            }

            // Chama o método de remoção
            repositorioPix.removerPix(id);

            // Atualiza a lista de chaves Pix
            List<Pix> chavesAtualizadas = repositorioPix.listarChaves();

            // Atualiza o adaptador com a lista nova
            ArrayAdapter<Pix> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chavesAtualizadas);
            ListView listView = findViewById(R.id.editListChaves);
            listView.setAdapter(adapter);

            // Exibe uma mensagem de sucesso
            Toast.makeText(this, "Remoção realizada com sucesso", Toast.LENGTH_LONG).show();

        } catch (NumberFormatException e) {
            // Captura erro de formato de número
            Toast.makeText(this, "Digite somente números", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // Captura qualquer outro erro imprevisto
            Toast.makeText(this, "Erro desconhecido: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}