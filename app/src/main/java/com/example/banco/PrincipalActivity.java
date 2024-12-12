package com.example.banco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {
    RepositorioBanco repositorioBanco;
    TextView txtSaldo;
    TextView txtNumeroConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);


        txtSaldo = findViewById(R.id.txtSaldo);
        txtNumeroConta = findViewById(R.id.txtNumeroConta);
        repositorioBanco = new RepositorioBanco(this);

        carregarDadosConta();
    }


    private void carregarDadosConta() {
        int numeroConta = repositorioBanco.getContaAtualizada().id;
        double saldo = repositorioBanco.getContaAtualizada().saldo;

        txtNumeroConta.setText("Conta: " + numeroConta);
        txtSaldo.setText(String.format("Saldo: R$ %.2f", saldo));
    }

    // DEPOSITO
    public void depositar(View view) {
        Intent intent = new Intent(this, DepositoActivity.class);
        startActivity(intent);
    }

    // SAQUE
    public void sacar(View view) {
        Intent intent = new Intent(this, SaqueActivity.class);
        startActivity(intent);
    }

    // EXTRATO
    public void extrato(View view) {
        Intent intent = new Intent(this, ExtratoActivity.class);
        startActivity(intent);
    }

    // PIX
    public void Pix(View view) {
        Intent intent = new Intent(this, PixActivity.class);
        startActivity(intent);
    }
}
