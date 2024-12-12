package com.example.banco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EnviarPixActivity extends AppCompatActivity {
    private RepositorioBanco repositorioBanco;
    private Conta conta;
    private ValidarCPF validarCPF;
    private ValidarTelefone validarTelefone;
    private RepositorioPix repositorioPix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar_pix);

        validarCPF = new ValidarCPF();
        validarTelefone = new ValidarTelefone();

        repositorioBanco = new RepositorioBanco(this);
        repositorioPix = new RepositorioPix(this);

        // Obtém a conta do repositório
        conta = repositorioBanco.getConta();
        if (conta == null) {
            // Exibe uma mensagem de erro se não encontrar a conta
            Toast.makeText(this, "Erro ao carregar conta", Toast.LENGTH_SHORT).show();
            finish(); // Encerra a atividade
        }
    }

    public void confirmarPix(View view) {
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

        // Verifica se a chave Pix está cadastrada
        if (repositorioPix.isChaveCadastrada(valorChaveTexto)) {
            Toast.makeText(this, "Chave pix Deve ser Diferente", Toast.LENGTH_SHORT).show();
            return;
        }

        // Agora que sabemos que a chave está cadastrada, podemos buscar o objeto Pix
        Pix pix = repositorioPix.getPix(valorChaveTexto);

        // Verifica se a chave fornecida não é a mesma do usuário
        if (repositorioPix.isChaveDoUsuario(valorChaveTexto, pix)) {
            Toast.makeText(this, "Você não pode enviar Pix para sua própria chave", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorPixNum = Double.parseDouble(valorPixTexto);
        if (valorPixNum <= 0) {
            Toast.makeText(this, "Digite um valor maior que ZERO", Toast.LENGTH_SHORT).show();
            return;
        }

        // Atualiza o saldo diretamente na conta
        double novoSaldo = conta.saldo - valorPixNum;
        if (novoSaldo < 0) {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
            return;
        }

        repositorioBanco.atualizarSaldo(novoSaldo);
        repositorioBanco.registrarTransacao("pix", valorPixNum, novoSaldo);

        Toast.makeText(this, "Pagamento via Pix realizado com sucesso", Toast.LENGTH_SHORT).show();

        // Chama a tela Principal
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

}
