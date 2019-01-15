package com.vik.mddropdownsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vik.materialdropdown.MaterialDropDownListener
import com.vik.vikramsharma.mddropdownsample.R.array
import com.vik.vikramsharma.mddropdownsample.R.layout
import kotlinx.android.synthetic.main.activity_main.materialDropDownCountries

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    initUI()
  }

  private fun initUI() {
    val list:List<String> = resources.getStringArray(
        array.countries
    ).toList()
    materialDropDownCountries.setList(list,object :MaterialDropDownListener{
      override fun selectedItem(
        text: String,
        position: Int
      ) {
      //Do something with selected Item
      }

    })
  }
}
