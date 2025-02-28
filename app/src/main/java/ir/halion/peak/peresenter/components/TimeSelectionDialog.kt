import android.annotation.SuppressLint
import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ir.halion.peak.peresenter.focus.firaSansFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
    list: List<Int> = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
) {
    var selectedNumber by remember { mutableIntStateOf(60) }
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss, properties = DialogProperties()) {
        Card(modifier = Modifier, colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(40.dp, 16.dp)
            ) {
                Text("انتخاب زمان ", fontFamily = firaSansFamily)
                Spacer(Modifier.size(14.dp))
                NumberPickerApp {
                    selectedNumber = it
                }

                Spacer(Modifier.size(24.dp))
                Button(shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFFFD54F)),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onConfirm.invoke(selectedNumber)
                        onDismiss.invoke()
                    }) {
                    Icon(
                        Icons.Default.Check, contentDescription = "Confirm", tint = Color.Black
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberPickerApp(
    list: List<Int> = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90), onConfirm: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("دقیقه", fontFamily = firaSansFamily)
        Column {
            val pagerState =
                rememberPagerState(pageCount = { list.size }, initialPage = list.size / 2)
            VerticalPager(
                modifier = Modifier.size(140.dp, 270.dp),
                state = pagerState,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(0.dp, 85.dp)
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${list[page]}",
                        fontWeight = if (page == pagerState.currentPage) FontWeight.ExtraLight else FontWeight.ExtraLight,
                        fontSize = if (page == pagerState.currentPage) 100.sp else 70.sp
                    )
                }
                onConfirm.invoke(list[pagerState.currentPage] * 60)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewNumberPicker() {
    MaterialTheme {
        Scaffold {
            NumberPickerDialog(onConfirm = {}, onDismiss = {})
        }

    }
}