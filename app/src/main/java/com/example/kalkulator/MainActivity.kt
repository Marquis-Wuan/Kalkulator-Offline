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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder // Tambahkan library ini di build.gradle jika ingin kalkulasi canggih

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalkulatorTheme {
                KalkulatorScreen()
            }
        }
    }
}

@Composable
fun KalkulatorScreen() {
    var display by remember { mutableStateOf("0") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // DISPLAY (Area Hasil)
        Text(
            text = display,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = if (display.length > 8) 40.sp else 70.sp, // Otomatis mengecil jika angka panjang
            lineHeight = 70.sp,
            maxLines = 2,
            fontWeight = FontWeight.Light
        )

        // TOMBOL-TOMBOL (Dibuat Grid agar rapi)
        val buttons = listOf(
            listOf("AC", "( )", "%", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "⌫", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                row.forEach { label ->
                    CalcButton(
                        label = label,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(vertical = 5.dp),
                        onClick = {
                            display = handleInput(display, label)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalcButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    val containerColor = when {
        label == "=" -> Color(0xFF4CAF50) // Hijau
        label in listOf("÷", "×", "-", "+") -> Color(0xFFFF9800) // Oranye
        label == "AC" -> Color(0xFFF44336) // Merah
        else -> Color(0xFF333333) // Abu gelap
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(containerColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// LOGIC KALKULATOR (Agar tidak ngaco)
fun handleInput(current: String, input: String): String {
    return when (input) {
        "AC" -> "0"
        "⌫" -> if (current.length <= 1) "0" else current.dropLast(1)
        "=" -> {
            try {
                // Sederhanakan: Ganti simbol visual ke simbol matematika
                val expression = current.replace("×", "*").replace("÷", "/")
                val result = ExpressionBuilder(expression).build().evaluate()
                if (result % 1 == 0.0) result.toInt().toString() else result.toString()
            } catch (e: Exception) {
                "Error"
            }
        }
        else -> {
            if (current == "0") input else current + input
        }
    }
}
