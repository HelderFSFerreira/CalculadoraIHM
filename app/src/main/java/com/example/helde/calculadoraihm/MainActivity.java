package com.example.helde.calculadoraihm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText Display1;
    private EditText Display2;
    private String strAnterior="",operacao="",strAtual="";
    private float resultado=0,memoriaM;
    private boolean ultimoBotaoEIgual,memoriaLimpa=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Display1 = (EditText) findViewById(R.id.eText1);
        Display2 = (EditText) findViewById(R.id.eText2);

        int idList[] = {R.id.btnUm, R.id.btnDois, R.id.btnTres, R.id.btnQuatro, R.id.btnCinco, R.id.btnSeis, R.id.btnSete, R.id.btnOito, R.id.btnNove, R.id.btnzero,
                R.id.btnDivisao, R.id.btnMutilicacao, R.id.btnSoma, R.id.btnSubtracao, R.id.btnIgual,
                R.id.btnApagarCaractere, R.id.btnLimpar, R.id.btnPonto,
                R.id.btnMMais,R.id.btnMMenos,R.id.btnMShow,R.id.btnMClean};

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
            case R.id.btnMClean:
                controlaMemoria("C");
                break;
            case R.id.btnMMais:
                controlaMemoria("+");
                break;
            case R.id.btnMMenos:
                controlaMemoria("-");
                break;
            case R.id.btnMShow:
                controlaMemoria("M");
                break;



            default:
                String numero = ((Button) view).getText().toString();
                adicionaNumero(numero);
                break;
        }
        penultimobotao(view);
    }

    //Verifica o estado da memoria e controla os calculos

    public void controlaMemoria(String tipo) {
        switch (tipo) {
            case "C":
                if (memoriaLimpa == false) {
                    memoriaM=0;
                    memoriaLimpa=true;
                    strAnterior="";
                    strAtual="";
                    operacao="";
                    resultado=0;
                    Toast.makeText(this,"Memoria apagada",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"Memoria já se encontra apagada",Toast.LENGTH_LONG).show();
                }

                break;
            case "+":
                if (memoriaLimpa) {
                    memoriaM=getNumeroMemoria();
                    memoriaLimpa=false;
                } else {
                    float ma1,ma2; // Memorias auxiliares
                    ma1=memoriaM;
                    ma2=getNumeroMemoria();

                    // Adicionar às Strings

                    strAnterior=String.valueOf(ma1);
                    strAtual=String.valueOf(ma2);
                    operacao="+";
                    mostrarConta();
                    calculaResultado();
                    memoriaM=resultado;


                }
                break;
            case "-":
                if (memoriaLimpa) {
                    memoriaM=getNumeroMemoria();
                    memoriaLimpa=false;
                } else {
                    float ma1,ma2; // Memorias auxiliares
                    ma1=memoriaM;
                    ma2=getNumeroMemoria();

                    // Adicionar às Strings

                    strAnterior=String.valueOf(ma1);
                    strAtual=String.valueOf(ma2);
                    operacao="-";
                    mostrarConta();
                    calculaResultado();
                    memoriaM=resultado;
                }
                break;
            case "M":
                if (!memoriaLimpa) {
                    Display1.setText(String.valueOf(memoriaM));
                    strAnterior="";
                    strAtual="";
                    operacao="";
                    resultado=0;
                    Display2.setText("");

                }

        }
    }

    public float getNumeroMemoria() {
        if (strAnterior.isEmpty()) {
            if (!strAtual.isEmpty()) {
                return Float.parseFloat(strAtual);
            } else {
                Toast.makeText(this,"Não há número para adicionar à memoria",Toast.LENGTH_LONG).show();
            }
        } else {
            return resultado;
        }
        return 0;
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

    // Verifica se uma string tem mais de um ponto


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

    // Apaga o caratecre da string atual

    public void apagaCaractere() {
        if (!strAtual.isEmpty()) {
            strAtual=strAtual.substring(0,strAtual.length()-1);
            mostrarConta();
        }
    }

    //Mostra o resultado e coloca o resultado na String anterior

    public void igual() {
        if (strAtual.isEmpty() || strAnterior.isEmpty() || operacao.isEmpty()) {

        } else {
            Display1.setText(strAnterior + " " + operacao + " " + strAtual);
            calculaResultado();
            strAnterior=String.valueOf(resultado);
            operacao="";
            strAtual="";

        }

    }

    // Adiciona um novo numero à string atual
    // Verifica se o ultimo botão é o = para não dar o bug de adicionar novos numeros sem operação

    public void adicionaNumero(String numero) {
        if (ultimoBotaoEIgual) {
            strAnterior="";
        }
        strAtual+=numero;
        mostrarConta();
    }

    // Adiciona operações

    public void adicionaOperacao(String operacao) {
        if ((strAnterior.isEmpty() && (!strAtual.isEmpty())) || (!strAnterior.isEmpty() && strAtual.isEmpty()) || (!strAnterior.isEmpty()) && (!strAtual.isEmpty())) {
            switch (operacao) {
                case "+":
                    if (strAnterior.isEmpty()) {
                        operacao = "+";
                        this.operacao = operacao;
                        strAnterior = strAtual;
                        strAtual = "";
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
                        operacao = "-";
                        this.operacao = operacao;
                        strAnterior = strAtual;
                        strAtual = "";
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
                        operacao = "*";
                        this.operacao = operacao;
                        strAnterior = strAtual;
                        strAtual = "";
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
                        operacao = "/";
                        this.operacao = operacao;
                        strAnterior = strAtual;
                        strAtual = "";
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
    }

    // Verifica se a string atual é vazia

    public boolean verificarAtualVazio() {
        mostrarConta();
        if (strAtual.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    // Calcula o resultado final, e coloca no display do resultado
    // Adiciona o resultado à strAnterior para permitir novas contas sobre o resultado

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

    // Imprime para o ecra de contas a conta a realizar.

    public void mostrarConta() {
        Display1.setText(strAnterior + " " + operacao + " " + strAtual);
    }
}
