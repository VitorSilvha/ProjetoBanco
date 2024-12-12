package com.example.banco;

public class Pix {
    public int id;
    public String cpf;
    public String telefone;

    // Sobrescrevendo o método toString()
    @Override
    public String toString() {
        return "ID: " + id + " CPF: " + cpf + " / TEL: " + telefone + "";
    }
}
