package com.tmelo.missasapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import org.json.JSONObject
import com.google.gson.Gson
import java.io.BufferedReader
import android.graphics.Typeface
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Título
        val tituloView = TextView(this).apply {
            text = "Horários das Missas de Rio Claro"
            textSize = 24f
            setTypeface(null, android.graphics.Typeface.BOLD)
            gravity = android.view.Gravity.CENTER
            setPadding(16, 32, 16, 16)
        }


//        val conteudoView = TextView(this).apply {
//            textSize = 20f
//            setPadding(16, 16, 16, 16)
//        }

        // Texto do conteúdo
        val textView = TextView(this).apply {
            typeface = Typeface.MONOSPACE
            textSize = 18f
        }

        textView.setPadding(16, 16, 16, 16)

        // Lê o JSON da pasta assets
        val jsonString = assets.open("dados.json")
            .bufferedReader()
            .use(BufferedReader::readText)

        // Converte para objetos usando Gson
        val missasData = Gson().fromJson(jsonString, MissasData::class.java)

        // Formata os dados para exibição
        val texto = StringBuilder()
        for (paroquia in missasData.paroquias) {
            texto.append("⛪ ${paroquia.nome}\n")
            for (capela in paroquia.capelas) {
                texto.append("  ➢ ${capela.nome}\n")
                for (horario in capela.horarios) {
                    texto.append("     \uD83D\uDD39 $horario\n")
                }
            }
            texto.append("\n\n")
        }

        textView.text = texto.toString()

        // Coloca o TextView dentro de um ScrollView
        val scroll = android.widget.ScrollView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,  // Altura 0 + peso para ocupar espaço restante
                1f  // Peso 1: ocupa tudo que sobrar do layout
            )
        }
        scroll.addView(textView)


        // footer
        val footerView = TextView(this).apply {
            text = "Criado por Thiago de Melo"
            textSize = 10f
//            setTypeface(null, android.graphics.Typeface.NORMAL)
            gravity = android.view.Gravity.CENTER
            setPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Layout principal (vertical)
        val layout = android.widget.LinearLayout(this).apply {

            orientation = android.widget.LinearLayout.VERTICAL
            addView(tituloView)
            addView(scroll)
            addView(footerView)
        }

        setContentView(layout)
    }
}
