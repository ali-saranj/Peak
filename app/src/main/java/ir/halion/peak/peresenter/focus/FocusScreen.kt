package ir.halion.peak.peresenter.focus

import NumberPickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.halion.peak.PersianDate
import ir.halion.peak.R
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


val firaSansFamily = FontFamily(
    Font(R.font.iranyekanwebregular, FontWeight.Light),
    Font(R.font.iranyekanwebregular, FontWeight.Normal),
    Font(R.font.iranyekanwebregular, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.iranyekanwebregular, FontWeight.Medium),
    Font(R.font.iranyekanwebregular, FontWeight.Bold)
)

@Composable
fun FocusScreen(
    viewModel: FocusViewModel = koinViewModel<FocusViewModel>(),
    modifier: Modifier = Modifier
) {

    val state by viewModel.focusState.collectAsState()
    var showDialogEdit by remember { mutableStateOf(false) }

    val progressAnimDuration = 1_500
    val progressAnimation by animateFloatAsState(
        targetValue = state.counter.toFloat(),
        animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing),
    )
    val scope = rememberCoroutineScope()
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(painter = painterResource(R.drawable.menu), contentDescription = null)
            Icon(painter = painterResource(R.drawable.time), contentDescription = null)
            Icon(painter = painterResource(R.drawable.music), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = getCurrentJalaliDate(), fontSize = 18.sp, fontFamily = firaSansFamily)
            Spacer(modifier = Modifier.padding(24.dp))
            Text(
                "به گوشی نگاه نکن و ادامه بده \uD83D\uDD2A",
                fontSize = 16.sp,
                fontFamily = firaSansFamily
            )
            Spacer(modifier = Modifier.padding(24.dp))
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progressAnimation / state.time },
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 15.dp,
                    color = Color(0xFFFFD54F),
                    trackColor = Color(0xFFEAEAEA),
                    modifier = Modifier.size(280.dp)
                )
                Image(
                    modifier = Modifier.size(250.dp),
                    painter = if (state.counter <= state.time / 2) painterResource(R.drawable.middle) else if (state.counter > state.time / 2) painterResource(
                        R.drawable.start
                    ) else painterResource(R.drawable.finish),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text("ریاضی", fontSize = 16.sp, fontFamily = firaSansFamily)
                if (!state.isStart) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.edit_icon),
                            contentDescription = "edit"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Text(secondToMinute(state.counter), fontSize = 94.sp, fontWeight = FontWeight.W100)
                if (!state.isStart) {
                    IconButton(onClick = { showDialogEdit = true }) {
                        Icon(
                            painter = painterResource(R.drawable.edit_icon),
                            contentDescription = "edit"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F)),
                onClick = {
                    if (state.isStart)
                        viewModel.eventHandler(FocusEvent.Stop)
                    else
                        viewModel.eventHandler(FocusEvent.Start)
                }) {
                Text(
                    "شروع/پایان",
                    fontSize = 16.sp,
                    fontFamily = firaSansFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
        AnimatedVisibility(showDialogEdit) {
            NumberPickerDialog(onDismiss = {
                showDialogEdit = false
            }, onConfirm = {
                viewModel.eventHandler(FocusEvent.UpdateTimer(it))
                showDialogEdit = false
            })
        }
    }
}

@Preview(name = "FocusScreen", showBackground = true)
@Composable
private fun PreviewFocusScreen() {
    FocusScreen()
}


fun secondToMinute(second: Int): String {
    return "${second / 60}:${second % 60}"
}

fun getCurrentJalaliDate(): String {
    val persianDate = PersianDate()
    val months = arrayOf(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
    )
    return " ${months[persianDate.shMonth - 1]} ${persianDate.shDay} ، ${persianDate.shYear}"
}

fun main() = println(getCurrentJalaliDate())
