package com.example.lazylistproject.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lazylistproject.model.ItemData

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(uiState.itemList) { index, item ->
            ItemCard(
                item = item,
                index = index,
                onSwitchChanged = { isChecked ->
                    viewModel.updateSwitchState(item.id, isChecked)
                }
            )
        }
    }
}

@Composable
fun ItemCard(
    item: ItemData,
    index: Int,
    onSwitchChanged: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val backgroundColor = if (index % 2 != 0) {
        Color(0xFF97E89B)
    } else {
        Color(0xFFFFFFFF)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable {
                Toast.makeText(context, "Item telah ditekan untuk item ${item.id}", Toast.LENGTH_SHORT).show()
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = "Gambar Item",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(id = item.title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = item.description),
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = item.isSwitchOn,
                    onCheckedChange = { isChecked ->
                        onSwitchChanged(isChecked)
                        val status = if (isChecked) "hidup" else "mati"
                        Toast.makeText(context, "Switch $status pada item ${item.id}", Toast.LENGTH_SHORT).show()
                    }
                )
                Button(
                    onClick = {
                        Toast.makeText(context, "Tombol telah ditekan untuk item ${item.id}", Toast.LENGTH_SHORT).show()
                    },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Aksi")
                }
            }
        }
    }
}