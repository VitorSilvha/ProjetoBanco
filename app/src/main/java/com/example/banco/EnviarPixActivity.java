package com.example.banco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EnviarPixActivity extends AppCompatActivity {
    RepositorioBanco repositorioBanco;
    Conta conta;
    ValidarCPF validarCPF;
    ValidarTelefone validarTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar_pix);
        validarCPF = new ValidarCPF();
        validarTelefone = new ValidarTelefone();
    }

    public void confirmarPix(View view) {
        repositorioBanco = new RepositorioBanco(this);
        conta = repositorioBanco.getConta();

        EditText edtChavePix = findViewById(R.id.edtChavePix);
        EditText edtValorPix = findViewById(R.id.edtValorPix);
        String valorChaveTexto = edtChavePix.getText().toString().trim();
        String valorPixTexto = edtValorPix.getText().toString().trim();


        if (valorPixTexto.isEmpty() || valorChaveTexto.isEmpty()) {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!valorPixTexto.matches("\\d+(\\.\\d+)?")) {
            Toast.makeText(this, "Digite um valor numérico válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida se a chave é CPF ou telefone
        if (!isChaveValida(valorChaveTexto)) {
            Toast.makeText(this, "Digite uma chave válida (CPF ou Telefone)", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorPixNum = Double.parseDouble(valorPixTexto);
        if (valorPixNum <= 0) {
            Toast.makeText(this, "Digite um valor maior que ZERO", Toast.LENGTH_SHORT).show();
            return;
        }

        if (valorPixNum > conta.saldo){
            Toast.makeText(this, "Você não tem dinheiro na conta, realize um deposito", Toast.LENGTH_SHORT).show();
            return;
        }

        // Atualiza o saldo diretamente na conta
        double novoSaldo = conta.saldo - valorPixNum;
        repositorioBanco.atualizarSaldo(novoSaldo);
        repositorioBanco.registrarTransacao("pix", valorPixNum, novoSaldo);

        Toast.makeText(this, "Depósito realizado com sucesso", Toast.LENGTH_SHORT).show();

        // Chama a tela Principal
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }


    private boolean isChaveValida(String chave) {
        // Remove caracteres não numéricos
        String chaveNumerica = chave.replaceAll("[^0-9]", "");

        // Verifica se é CPF válido
        if (chaveNumerica.length() == 11 && validarCPF.isCpfValido(chaveNumerica)) {
            return true;
        }

        // Verifica se é telefone válido
        if ((chaveNumerica.length() == 10 || chaveNumerica.length() == 11) && validarTelefone.isTelefoneValido(chaveNumerica)) {
            return true;
        }

        // Retorna falso se não for CPF nem telefone
        return false;
    }


}
