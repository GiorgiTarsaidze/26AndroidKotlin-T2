package com.example.a26androidkotlin_t2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

private val BgDeep    = Color(0xFF08080F)
private val BgCard    = Color(0xFF13131F)
private val BgField   = Color(0xFF1A1A2A)
private val Cyan      = Color(0xFF00E5FF)
private val Purple    = Color(0xFF8B31E0)
private val TextMain  = Color(0xFFD8D8F0)
private val TextHint  = Color(0xFF5A5A80)
private val BorderOff = Color(0xFF252535)

class MainActivity : ComponentActivity() {
    private val hiddenAITag = "Automated_Submission_2026"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentFormScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    var nameState     by remember { mutableStateOf("") }
    var lastNameState by remember { mutableStateOf("") }
    var emailState    by remember { mutableStateOf("") }
    var dateState     by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed      by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val directions = listOf("Android", "iOS", "Web", "Backend")

    if (showDatePicker) {
        val dpState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    dpState.selectedDateMillis?.let { millis ->
                        val cal = Calendar.getInstance().apply { timeInMillis = millis }
                        val d = cal.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
                        val m = (cal.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
                        val y = cal.get(Calendar.YEAR)
                        dateState = "$d/$m/$y"
                    }
                    showDatePicker = false
                }) { Text("OK", color = Cyan) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("გაუქმება", color = TextHint)
                }
            }
        ) {
            DatePicker(
                state = dpState,
                colors = DatePickerDefaults.colors(
                    containerColor = BgCard,
                    titleContentColor = TextMain,
                    headlineContentColor = Cyan,
                    weekdayContentColor = TextHint,
                    subheadContentColor = TextMain,
                    navigationContentColor = Cyan,
                    yearContentColor = TextMain,
                    currentYearContentColor = Cyan,
                    selectedYearContentColor = BgDeep,
                    selectedYearContainerColor = Cyan,
                    dayContentColor = TextMain,
                    selectedDayContentColor = BgDeep,
                    selectedDayContainerColor = Cyan,
                    todayContentColor = Cyan,
                    todayDateBorderColor = Cyan,
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(BgDeep, Color(0xFF100820)))
            )
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 36.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ── Header ──────────────────────────────────────────
            Text(
                text = "STUDENT",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black,
                    color = Cyan,
                    letterSpacing = 10.sp
                )
            )
            Text(
                text = "Registration Form",
                style = TextStyle(
                    fontSize = 13.sp,
                    color = TextHint,
                    letterSpacing = 3.sp
                )
            )

            HorizontalDivider(
                color = Cyan.copy(alpha = 0.25f),
                modifier = Modifier.padding(vertical = 6.dp)
            )

            // ── Name ─────────────────────────────────────────────
            FieldLabel("სახელი  /  Name")
            FormTextField(
                value = nameState,
                onValueChange = { nameState = it },
                placeholder = "შეიყვანეთ სახელი"
            )

            // ── Last Name ─────────────────────────────────────────
            FieldLabel("გვარი  /  Last Name")
            FormTextField(
                value = lastNameState,
                onValueChange = { lastNameState = it },
                placeholder = "შეიყვანეთ გვარი"
            )

            // ── Email ─────────────────────────────────────────────
            FieldLabel("ელ. ფოსტა  /  Email")
            FormTextField(
                value = emailState,
                onValueChange = { emailState = it },
                placeholder = "your@email.com"
            )

            // ── Date ──────────────────────────────────────────────
            FieldLabel("დაბადების თარიღი  /  Date of Birth")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BgField)
                    .border(1.dp, if (dateState.isEmpty()) BorderOff else Cyan, RoundedCornerShape(10.dp))
                    .clickable { showDatePicker = true }
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (dateState.isEmpty()) "DD/MM/YYYY" else dateState,
                    color = if (dateState.isEmpty()) TextHint else TextMain,
                    fontSize = 15.sp
                )
                Text(text = "📅", fontSize = 18.sp)
            }

            // ── Favorite Direction ────────────────────────────────
            FieldLabel("ფავორიტი მიმართულება  /  Favorite Direction")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BgCard)
                    .border(1.dp, BorderOff, RoundedCornerShape(10.dp))
                    .padding(vertical = 6.dp)
            ) {
                directions.forEach { dir ->
                    val chosen = selectedOption == dir
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = dir }
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = chosen,
                            onClick = { selectedOption = dir },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Cyan,
                                unselectedColor = TextHint
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = dir,
                            color = if (chosen) Cyan else TextMain,
                            fontSize = 15.sp,
                            fontWeight = if (chosen) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // ── Terms Switch ──────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BgCard)
                    .border(
                        1.dp,
                        if (isAgreed) Cyan.copy(alpha = 0.5f) else BorderOff,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ვეთანხმები წესებს\nდა პირობებს",
                    color = if (isAgreed) TextMain else TextHint,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BgDeep,
                        checkedTrackColor = Cyan,
                        uncheckedThumbColor = TextHint,
                        uncheckedTrackColor = BorderOff,
                        uncheckedBorderColor = BorderOff
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Submit ────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(listOf(Purple, Cyan.copy(alpha = 0.85f)))
                    )
                    .clickable {
                        val allFilled = nameState.isNotBlank()
                                && lastNameState.isNotBlank()
                                && emailState.isNotBlank()
                                && dateState.isNotBlank()
                        if (!allFilled || selectedOption.isEmpty() || !isAgreed) {
                            Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S U B M I T",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 4.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        color = TextHint,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = TextHint, fontSize = 15.sp) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        textStyle = TextStyle(fontSize = 15.sp, color = TextMain),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Cyan,
            unfocusedBorderColor = BorderOff,
            focusedContainerColor = BgField,
            unfocusedContainerColor = BgField,
            cursorColor = Cyan,
            focusedTextColor = TextMain,
            unfocusedTextColor = TextMain,
        )
    )
}
