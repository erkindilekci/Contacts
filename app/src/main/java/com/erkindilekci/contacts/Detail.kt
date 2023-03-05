package com.erkindilekci.contacts

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.erkindilekci.contacts.model.Person
import com.erkindilekci.contacts.viewmodel.DetailScreenViewModel
import com.erkindilekci.contacts.viewmodelfactory.DetailScreenViewModelFactory

@Composable
fun DetailScreen(sentPerson: Person, navController: NavController){
    val tfPersonName = remember { mutableStateOf("") }
    val tfPersonNumber = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    val context = LocalContext.current
    val viewModel: DetailScreenViewModel = viewModel(
        factory = DetailScreenViewModelFactory(context.applicationContext as Application)
    )

    LaunchedEffect(key1 = true){
        tfPersonName.value = sentPerson.person_name
        tfPersonNumber.value = sentPerson.person_number
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                backgroundColor = colorResource(id = R.color.middle_orange),
                contentColor = Color.White
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = tfPersonName.value,
                    onValueChange = { tfPersonName.value = it },
                    label = { Text(text = "Person Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorResource(id = R.color.light_orange),
                        textColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(20)
                )

                TextField(
                    value = tfPersonNumber.value,
                    onValueChange = { tfPersonNumber.value = it },
                    label = { Text(text = "Person Number") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = colorResource(id = R.color.light_orange),
                        textColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White),
                    shape = RoundedCornerShape(20),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedButton(onClick = {
                    val personName = tfPersonName.value
                    val personNumber = tfPersonNumber.value
                    viewModel.update(sentPerson.person_id, personName, personNumber)

                    localFocusManager.clearFocus()
                    //Rotate Main Screen
                    navController.navigate("mainscreen"){
                        popUpTo("detailscreen") {inclusive = true}
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.light_orange),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.size(200.dp, 50.dp),
                    border = BorderStroke(1.dp, Color.Black)
                )
                {
                    Text(text = "Update", color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                }
            }
        },
    )
}