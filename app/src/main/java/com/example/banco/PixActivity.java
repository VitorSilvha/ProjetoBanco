package com.example.banco;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PixActivity extends AppCompatActivity {

    ArrayList<Pix> listaDePix;
    RepositorioPix repositorioPix;
    ValidarCPF validarCPF;
    ValidarTelefone validarTelefone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pix);

        listaDePix  = (ArrayList<Pix>) getIntent().getSerializableExtra("lista_pix");
        Log.i("Pix","Cadastro carregado com sucesso");

        repositorioPix = new RepositorioPix(this);
        validarCPF = new ValidarCPF();
        validarTelefone = new ValidarTelefone();

    }

    public void CadastroPix(View view) {

        EditText editCPF = findViewById(R.id.editTextCadastroPixCPF);
        EditText editTelefone = findViewById(R.id.editTextCadastroPixTelefone);
        String conteudopixCpf = editCPF.getText().toString();
        String conteudopixTelefone = editTelefone.getText().toString();

        // validação se foi digitado algo na caixa CPF
        if (conteudopixCpf.isEmpty()) {
            Toast.makeText(this, "Digite o CPF", Toast.LENGTH_SHORT).show();
            return;
        }

        // validação se foi digitado algo na caixa telefone
        if (conteudopixTelefone.isEmpty()) {
            Toast.makeText(this, "Digite o Telefone", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean cpfValido = validarCPF.isCpfValido(conteudopixCpf);
        if (!cpfValido){
            Toast.makeText(this, "CPF invalido", Toast.LENGTH_SHORT).show();
            return;

        }

        boolean telefoneValido = validarTelefone.isTelefoneValido(conteudopixTelefone);
        if (!telefoneValido) {
            Toast.makeText(this, "Telefone inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // adcionando os conteudos escrito no banco de dados
        Pix pix = new Pix();
        pix.cpf =  conteudopixCpf;
        pix.telefone = conteudopixTelefone;
        repositorioPix.adcionarChave(pix);
        Toast.makeText(this, "Chave pix cadastrada com sucesso", Toast.LENGTH_SHORT).show();
    }


    public void listarChaves(View view) {
        Intent intent = new Intent(this, ChavesActivity.class);
        startActivity(intent);
    }

    public void enviarPix(View view) {
        Intent intent = new Intent(this, EnviarPixActivity.class);
        startActivity(intent);
    }
}