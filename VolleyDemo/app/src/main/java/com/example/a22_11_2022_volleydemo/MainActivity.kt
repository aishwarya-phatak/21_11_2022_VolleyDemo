package com.example.a22_11_2022_volleydemo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.a22_11_2022_volleydemo.R
import com.example.a22_11_2022_volleydemo.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var users = ArrayList<User>()
    var pageNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VolleySingleton.initializeRequestQueue(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCreateUser.setOnClickListener {
            addUser()
        }

        /*binding.btnStringRequest.setOnClickListener {

        }*/
        binding.btnJSONObjectRequest.setOnClickListener {
                jsonObjectRequest()
        }
    }

    private fun addUser(){
        var inputJSONObject = JSONObject()
        inputJSONObject.put("email",binding.edtUsername.text.toString())
        inputJSONObject.put("password",binding.edtPassword.text.toString())
        var jsonObjectRequestQueue = JsonObjectRequest(
            Request.Method.POST,
            "https://reqres.in/api/register",
            inputJSONObject,
            AddUserListener(),
            StringRequestErrorListener()
        )

        VolleySingleton.volleyRequestQueue?.add(jsonObjectRequestQueue)
    }

    class AddUserListener : Response.Listener<JSONObject>{
        override fun onResponse(response: JSONObject?) {
            Log.e("tag",response.toString())
        }
    }

    private fun jsonObjectRequest(){
        var volleyJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://reqres.in/api/users?page=${pageNumber}",
            null,
            JsonObjectRequestSuccessListener(),
            StringRequestErrorListener()
        )
        VolleySingleton.volleyRequestQueue?.add(volleyJsonObjectRequest)
        pageNumber++
    }

    inner class JsonObjectRequestSuccessListener : Response.Listener<JSONObject>{
        override fun onResponse(response: JSONObject?) {
            var usersResponse = Gson().fromJson<UserResponse>(
                response.toString(),
                UserResponse::class.java
            )

            users.addAll(usersResponse.users)

            Log.e("tag","----------------------")
            for (eachUser in users) {
                Log.e("tag",eachUser.toString())
            }
            Log.e("tag","---------------------")
        }
    }

    class StringRequestErrorListener : Response.ErrorListener{
        override fun onErrorResponse(error: VolleyError?) {
            Log.e("tag","$error")
        }

    }
}