package com.adminunibite.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adminunibite.app.R
import com.adminunibite.app.databinding.ActivitySignUpBinding
import com.adminunibite.app.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var userName : String
    private lateinit var coffeShopName: String
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private val binding: ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicializando Autenticacion De Firebase
        auth = Firebase.auth
        //Inicializando La base de Datos de Firebase
        database = Firebase.database.reference

        val universityList = arrayOf("Universidad Dominico Americano", "Universidad 2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, universityList)
        val autoCompleteTextView = binding.listOfUniversity
        autoCompleteTextView.setAdapter(adapter)

        binding.registerButton.setOnClickListener{
            //Obteniendo datos de los Edit Text
            userName = binding.name.text.toString().trim()
            coffeShopName = binding.coffeShopName.text.toString().trim()
            email = binding.emailOrPhone.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (userName.isBlank() || coffeShopName.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (password.length < 6){
                    Toast.makeText(this, "La contraseña debe contener 6 carácteres como minimo", Toast.LENGTH_LONG).show()
                }
               createAccount(email, password)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.alreadyHaveAccountButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(this, "Cuenta Creada Exitosamente", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Ha Fallado la Creación de la Cuenta", Toast.LENGTH_SHORT).show()
                Log.d("Cuenta", "crearCuenta: Fallo", task.exception)
            }
        }
    }
    //Guardando datos del usuario en la base de datos
    private fun saveUserData() {
        //Obteniendo datos de los Edit Text
        userName = binding.name.text.toString().trim()
        coffeShopName = binding.coffeShopName.text.toString().trim()
        email = binding.emailOrPhone.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(userName, coffeShopName, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        //Guardando usuario en la base de datos de Firebase
        database.child("user").child(userId).setValue(user)
    }
}