package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botaoIniciar;
    private int numero;
    private Handler handler = new Handler(); //ele fica responsável por entregar as mensagens para a fila de sua thread, executando-as em seguida
    private boolean pararExecucao = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoIniciar = findViewById(R.id.buttonIniciar);

    }

    public void iniciarThread(View view){
       /* MyThread thread = new MyThread();
        thread.start();*/ //inicializando a thread lá embaixo.

        pararExecucao = false;
        MyRunnable runnable = new MyRunnable(); //runnable = executavel, crie um objeto executável
        new Thread(runnable).start();
    }

    public void pararThread(View view)
    {
      pararExecucao = true;
    }

    //Thread utiliza Runnable, ele é mais direto para atingir o objetivo.
    //Para implementar o Runnable precisamos criar o obj executável e instanciar.
    //Runnable você pode criar o obj exec. e fazer alterações antes de executar
    //ext. Threads será executado direto

    class MyRunnable implements Runnable{

        @Override
        public void run() {

            //Handler handler = new Handler(); //consigo enviar códigos que quero executar dentro da thread.

            for (int i=0; i<=15; i++){

                if(pararExecucao)
                    return;

                //Dará erro, pois só a UI Principal pode realizar alterações na interface por segurança.
                numero=i;
                Log.d("Thread", "contador: " + i);
                //botaoIniciar.setText("contador: " + i);

                //como enviar códigos para Thread principal, uma fila de execução.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        botaoIniciar.setText("contador: " + numero);
                    }
                });


                //runOnUiThread ou Handler faz exatamente a mesma coisa.
               /* handler.post(new Runnable() {
                    @Override
                    public void run() {
                        botaoIniciar.setText("contador: " + numero);
                    }
                });*/


                try {
                    Thread.sleep(1000);  //sleep pausar a execução
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    //classe interna
    //aqui não será usado na thread principal e sim na thread própria
    class MyThread extends Thread{

        @Override
        public void run() {
            for (int i=0; i<=15; i++){

                Log.d("Thread", "contador: " + i);
                try {
                    Thread.sleep(1000);  //sleep pausar a execução
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //super.run();
        }
    }
}