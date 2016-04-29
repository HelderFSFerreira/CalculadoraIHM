package com.example.helde.calculadoraihm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Display1;
    private EditText Display2;
    private String strAnterior="",operacao="",strAtual="";
    private float resultado;
    private boolean ultimoBotaoEIgual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Display1 = (EditText) findViewById(R.id.eText1);
        Display2 = (EditText) findViewById(R.id.eText2);

        int idList[] = {R.id.btnUm, R.id.btnDois, R.id.btnTres, R.id.btnQuatro, R.id.btnCinco, R.id.btnSeis, R.id.btnSete, R.id.btnOito, R.id.btnNove, R.id.btnzero,
                R.id.btnDivisao, R.id.btnMutilicacao, R.id.btnSoma, R.id.btnSubtracao, R.id.btnIgual,
                R.id.btnApagarCaractere, R.id.btnLimpar, R.id.btnPonto};

        for (int id : idList) {
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSoma:
                adicionaOperacao("+");
                break;
            case R.id.btnSubtracao:
                adicionaOperacao("-");
                break;
            case R.id.btnMutilicacao:
                adicionaOperacao("*");
                break;
            case R.id.btnDivisao:
                adicionaOperacao("/");
                break;
            case R.id.btnIgual:
                igual();
                break;
            case R.id.btnLimpar:
                strAnterior="";
                strAtual="";
                operacao="";
                resultado=0;
                Display2.setText("");
                Display1.setText("");
                break;
            case R.id.btnApagarCaractere:
                apagaCaractere();
                break;
            case R.id.btnPonto:
                checkPonto();
                break;


            default:
                String numero = ((Button) view).getText().toString();
                adicionaNumero(numero);
                break;
        }
        penultimobotao(view);
    }

    //Verifica se o ultimo botão é o botão igual.
    //Caso seja igual ao adicionar novos numeros os numeros das operações anteriores desaparecem.

    public boolean penultimobotao(View view) {
        if (view.getId()==R.id.btnIgual) {
            ultimoBotaoEIgual=true;
            return true;
        } else {
            ultimoBotaoEIgual=false;
            return false;
        }
    }


    public void checkPonto() {
        if (strAtual.isEmpty()) {
            strAtual="0.";
        } else {
            int index = strAtual.indexOf(".");
            if (index > -1) {
                strAtual = strAtual.substring(0, index) + strAtual.substring(index + 1) + ".";
            } else {
                strAtual += ".";
            }

        }
        mostrarConta();
    }

    public void apagaCaractere() {
        if (!strAtual.isEmpty()) {
            strAtual=strAtual.substring(0,strAtual.length()-1);
            mostrarConta();
        }
    }

    public void igual() {
        if (!(strAtual.isEmpty() || strAnterior.isEmpty())) {
            Display1.setText(strAnterior + " " + operacao + " " + strAtual);
            calculaResultado();
            strAnterior=String.valueOf(resultado);
            operacao="";
            strAtual="";

        }

    }

    public void adicionaNumero(String numero) {
        if (ultimoBotaoEIgual) {
            strAnterior="";
        }
        strAtual+=numero;
        mostrarConta();
    }

    public void adicionaOperacao(String operacao) {
        switch (operacao) {
            case "+":
                if (strAnterior.isEmpty()) {
                    operacao="+";
                    this.operacao=operacao;
                    strAnterior=strAtual;
                    strAtual="";
                    mostrarConta();
                } else {
                    if (verificarAtualVazio()) {
                        calculaResultado();
                    }
                    operacao = "+";
                    this.operacao = operacao;
                    mostrarConta();
                }
                break;
            case "-":
                if (strAnterior.isEmpty()) {
                    operacao="-";
                    this.operacao=operacao;
                    strAnterior=strAtual;
                    strAtual="";
                    mostrarConta();
                } else {
                    if (verificarAtualVazio()) {
                        calculaResultado();
                    }
                    operacao = "-";
                    this.operacao = operacao;
                    mostrarConta();
                }
                break;
            case "*":
                if (strAnterior.isEmpty()) {
                    operacao="*";
                    this.operacao=operacao;
                    strAnterior=strAtual;
                    strAtual="";
                    mostrarConta();
                } else {
                    if (verificarAtualVazio()) {
                        calculaResultado();
                    }
                    operacao = "*";
                    this.operacao = operacao;
                    mostrarConta();
                }
                break;
            case "/":
                if (strAnterior.isEmpty()) {
                    operacao="/";
                    this.operacao=operacao;
                    strAnterior=strAtual;
                    strAtual="";
                    mostrarConta();
                } else {
                    if (verificarAtualVazio()) {
                        calculaResultado();
                    }
                    operacao = "/";
                    this.operacao = operacao;
                    mostrarConta();
                }
                break;
        }
    }

    public boolean verificarAtualVazio() {
        mostrarConta();
        if (strAtual.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void calculaResultado() {
        float n1 = Float.parseFloat(strAnterior);
        float n2 = Float.parseFloat(strAtual);

        switch (operacao) {
            case "+":
                resultado=n1+n2;
                strAnterior=String.valueOf(resultado);
                strAtual="";
                Display2.setText(String.valueOf(resultado));
                break;
            case "-":
                resultado=n1-n2;
                strAnterior=String.valueOf(resultado);
                strAtual="";
                Display2.setText(String.valueOf(resultado));
                break;
            case "*":
                resultado=n1*n2;
                strAnterior=String.valueOf(resultado);
                strAtual="";
                Display2.setText(String.valueOf(resultado));
                break;
            case "/":
                resultado=n1/n2;
                strAnterior=String.valueOf(resultado);
                strAtual="";
                Display2.setText(String.valueOf(resultado));
                break;
        }

    }

    public void mostrarConta() {
        Display1.setText(strAnterior + " " + operacao + " " + strAtual);
    }
}
