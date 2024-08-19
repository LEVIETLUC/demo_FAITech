package com.example.demoandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.demoandroid.CustomHandleJSON
import com.example.demoandroid.R
import com.example.demoandroid.data.Device
import com.example.demoandroid.data.User

class ParseJsonActivity : AppCompatActivity() {
    private lateinit var  tvParseJson : TextView
    private lateinit var btnJsonParser: Button
    private lateinit var btnFormatJson: Button
    private lateinit var userData: User
    private var CustomHandleJSON = CustomHandleJSON()
    private var stateBtnnGroup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse_json)
        tvParseJson = findViewById(R.id.text_view_result)
        btnJsonParser = findViewById(R.id.button_json_parser)
        btnFormatJson = findViewById(R.id.button_format_json)
        val jsonData = """
          {
              "username":"Jonas",
              "quantity": 2,
              "isPayment": true,
              "devices":
              [
                {
                  "id": 1,
                  "nameModel":"iPhone 13 Pro",
                  "colorModel": ["Black", "White"],
                  "price": 900
                },
                {
                    "id": 2,
                    "nameModel":"Samsung Galaxy S30",
                    "colorModel": ["Black", "Blue"],
                    "price": 1000
                }
              ]
          }
        """
        tvParseJson.text = jsonData

        btnJsonParser.setOnClickListener {
            val map = CustomHandleJSON.jsonToMapCus(tvParseJson.text.toString())
            Log.d("ParseJsonActivity", "map: $map")
            userData = mapToUser(map)
            val userView = formatStringToDisplay(userData)
            tvParseJson.text = userView
            stateBtnnGroup = true
            updateButtonStates()
        }

        btnFormatJson.setOnClickListener {
            val json = CustomHandleJSON.mapToJsonCus(userToMap(userData))
            Log.d("ParseJsonActivity", "json: $json")
            tvParseJson.text = json
            stateBtnnGroup = false
            updateButtonStates()
        }


    }


    private fun updateButtonStates() {
        btnJsonParser.isEnabled = !stateBtnnGroup
        btnFormatJson.isEnabled = stateBtnnGroup
    }


    private fun mapToUser(map: Map<String, Any>): User {
        val userName = map["username"] as String
        val quantity = map["quantity"] as Int
        val isPayment = map["isPayment"] as Boolean
        val devices = (map["devices"] as List<Map<String, Any>>).map { deviceMap ->
            Device(
                id = deviceMap["id"] as Int,
                name = deviceMap["nameModel"] as String,
                color = deviceMap["colorModel"] as List<String>,
                price = deviceMap["price"] as Int
            )
        }
        return User(userName, quantity, isPayment, devices)
    }

    private fun formatStringToDisplay(userData: User): String {
        val userView = """
            Username: ${userData.userName}
            Quantity: ${userData.quantity}
            Is Payment: ${userData.isPayment}
            Devices: ${userData.devices.size}
                id: ${userData.devices[0].id}
                nameModel: ${userData.devices[0].name}
                colorModel: ${userData.devices[0].color}
                price: ${userData.devices[0].price}
                
                id: ${userData.devices[1].id}
                nameModel: ${userData.devices[1].name}
                colorModel: ${userData.devices[1].color}
                price: ${userData.devices[1].price}
            """
        return userView
    }

   private fun userToMap(user: User): Map<String, Any> {
        val devices = user.devices.map { device ->
            mapOf(
                "id" to device.id,
                "nameModel" to device.name,
                "colorModel" to device.color,
                "price" to device.price
            )
        }
        return mapOf(
            "username" to user.userName,
            "quantity" to user.quantity,
            "isPayment" to user.isPayment,
            "devices" to devices
        )
    }

}