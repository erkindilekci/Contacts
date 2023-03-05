package com.erkindilekci.contacts

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erkindilekci.contacts.model.Person
import com.erkindilekci.contacts.ui.theme.ContactsTheme
import com.erkindilekci.contacts.viewmodel.MainScreenViewModel
import com.erkindilekci.contacts.viewmodelfactory.MainScreenViewModelFactory
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigation()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContactsTheme {

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController){
    val isSearching = remember { mutableStateOf(false) }
    val tf = remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel: MainScreenViewModel = viewModel(
        factory = MainScreenViewModelFactory(context.applicationContext as Application)
    )
    val personList = viewModel.personList.observeAsState(listOf())
    
    LaunchedEffect(key1 = true){
        viewModel.loadContacts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    /*
                    if (isSearching.value){
                        TextField(
                            value = tf.value,
                            onValueChange = {
                                tf.value = it
                                viewModel.search(it)
                                            },
                            label = { Text(text = "Search") },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorResource(id = R.color.light_orange),
                                textColor = Color.White,
                                focusedLabelColor = Color.White,
                                unfocusedLabelColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedIndicatorColor = Color.White
                            ),
                            shape = RoundedCornerShape(20)
                        )
                    }*/
                    Text(text = "Contacts")
                },
                backgroundColor = colorResource(id = R.color.middle_orange),
                contentColor = Color.White,
                /*
                actions = {
                    if (isSearching.value){
                        IconButton(onClick = {
                            isSearching.value = false
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.close_image),
                                contentDescription = null,
                                tint = Color.White) }
                    } else {
                        IconButton(onClick = {
                            isSearching.value = true
                        }) {
                            Icon(painter = painterResource(id = R.drawable.search_image),
                                contentDescription = null,
                                tint = Color.White) }
                    }
                }

                 */
            )
        },
        content = {
            LazyColumn{
                items(
                    count = personList.value!!.size,
                    itemContent = {
                        val person = personList.value!![it]
                        Card(modifier = Modifier
                            .padding(all = 3.dp)
                            .fillMaxWidth()
                            ) {
                            Row(modifier = Modifier
                                .background(
                                    colorResource(id = R.color.light_orange)
                                )
                                .clickable {
                                    val personJson = Gson().toJson(person)
                                    navController.navigate("detailscreen/$personJson")
                                }) {
                                Row(
                                    modifier = Modifier
                                        .padding(all = 10.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "${person.person_name}\n${person.person_number}", color = Color.White)

                                    IconButton(onClick = {
                                        viewModel.delete(person.person_id)
                                    }) {
                                        Icon(painter = painterResource(id = R.drawable.delete_image),
                                            contentDescription = null,
                                            tint = Color.White) }
                                }
                            }
                        }
                    }
                )

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("registerscreen") },
                backgroundColor = colorResource(id = R.color.middle_orange),
                content = {
                    Icon(painter = painterResource(id = R.drawable.add_image),
                        contentDescription = null,
                        tint = Color.White)
                }
            )
        }
    )
}

@Composable
fun ComposeNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainscreen"){
        composable("mainscreen"){
            MainScreen(navController = navController)
        }
        composable("registerscreen"){
            RegisterScreen(navController)
        }
        composable("detailscreen/{person}", arguments = listOf(
            navArgument("person"){type = NavType.StringType}
        )){
            val json = it.arguments?.getString("person")
            val sentPerson = Gson().fromJson(json, Person::class.java)
            DetailScreen(sentPerson, navController)
        }
    }
}