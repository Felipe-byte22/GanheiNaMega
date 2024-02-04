package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //aqui onde você decide o que o app vai fazer...

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        // banco de dados de preferências
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        /*if (result != null) {
            txtResult.text = "Ultima aposta: $result"
        }*/

        result?.let {
            txtResult.text = "Ultima aposta: $it"
        }

        /* alternativa 2
        prefs.edit().apply{
            putString("result", txtResult.text.toString())
            apply()
        }*/

        /*txtResult.text = "Ultima aposta: $result"*/

        // opção 1: xml

        // opção 2: variavel que seja do tipo View.OnClickListener (interface)
        //btnGenerate.setOnClickListener(buttonClickListener)

        //!! opção 3: mais simples possivel:
        btnGenerate.setOnClickListener {
            // aqui podemos colocar nossa logica de programação, porque será disparado depois do
            // evento de touch do usuário

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        //aqui é a falha 2
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe o numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt() // converte string para inteiro

        //aqui é a falha 2
        if (qtd < 6 || qtd > 15) {
            // deu falha
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        // aqui é o sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random

        while (true) {
            val number = random.nextInt(60) // 0..59
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

        // commit -> salvar de forma sincrona (bloquear a interface)
            // informar se teve sucesso ou não

        // apply -> salvar de forma assincrona (não vai bloquear a interface)
            // não informa se teve sucesso ou não

    }
}


//    val buttonClickedListener = View.OnClickListener {
//        Log.i("Teste", "botao clicado!!!")
//    }

//    val buttonClickedListener = object : View.OnClickListener {
//        override fun onClick(v: View?) {
//            Log.i("Teste", "botao clicado!!!")
//        }
//    }

//    fun buttonClicked(view: View) {
//        Log.i("Teste", "botao clicado!!!")
//    }
