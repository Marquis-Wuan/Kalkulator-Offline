package com.example.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalkulatorScreen()
        }
    }
}

@Composable
fun KalkulatorScreen() {
    var expression by remember { mutableStateOf("") }
    val bgColor = Color(0xFF000000)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Display Angka
        Text(
            text = expression,
            fontSize = 70.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            maxLines = 2
        )
        
        // Kursor Putih
        Box(modifier = Modifier.align(Alignment.End).width(3.dp).height(50.dp).background(Color.White))
        
        Spacer(modifier = Modifier.height(30.dp))

        val buttons = listOf(
            listOf("AC", "( )", "%", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "⌫", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { label ->
                    CalcButton(label, modifier = Modifier.weight(1f)) {
                        expression = when(label) {
                            "AC" -> ""
                            "⌫" -> if(expression.isNotEmpty()) expression.dropLast(1) else ""
                            "÷" -> expression + "/"
                            "×" -> expression + "*"
                            "=" -> try { "10" } catch(e: Exception) { "Error" } // Contoh simpel
                            else -> expression + label
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalcButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    val containerColor = when {
        label == "=" -> Color(0xFFE0E0E0)
        label in listOf("AC", "( )", "%") -> Color(0xFFA5A5A5)
        label in listOf("÷", "×", "-", "+") -> Color(0xFF444444)
        else -> Color(0xFF333333)
    }
    val contentColor = if (label == "=" || label in listOf("AC", "( )", "%")) Color.Black else Color.White

    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(1f),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = label, fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
}
