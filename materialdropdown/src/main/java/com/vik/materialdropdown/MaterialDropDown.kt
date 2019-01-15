package com.vik.materialdropdown

import android.R.layout
import android.content.Context
import android.content.res.TypedArray
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ListPopupWindow
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.view_material_drop_down.view.etDropDown
import kotlinx.android.synthetic.main.view_material_drop_down.view.tilMaterialDropdown

class MaterialDropDown(
  context: Context,
  attrs: AttributeSet
) : LinearLayout(context, attrs) {

  private var array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialDropDown)
  private lateinit var listMenu: ListPopupWindow
  private var selectedItemPosition: Int = 0

  init {
    View.inflate(context, R.layout.view_material_drop_down, this)
    initiateUi()
  }

  private fun initiateUi() {
    array.getString(R.styleable.MaterialDropDown_hintText)
        ?.let { tilMaterialDropdown.hint = it }

    etDropDown.isLongClickable = false
    etDropDown.keyListener = null
    etDropDown.setOnClickListener { showList() }
    etDropDown.setOnFocusChangeListener { _, hasFocus ->
      if (hasFocus) showList()
      if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
        etDropDown.showSoftInputOnFocus = false
      } else {
        hideKeyboard()
      }
    }
    initiateList()
  }

  private fun initiateList() {
    listMenu = ListPopupWindow(context)
    listMenu.anchorView = etDropDown
    array.getInteger(R.styleable.MaterialDropDown_listHeight, 0)
        .let { if (it != 0) listMenu.height }
    listMenu.isModal = true
    etDropDown.post { listMenu.width = etDropDown.measuredWidth }
  }

  fun setList(
    list: List<String>,
    materialDropDownListener: MaterialDropDownListener?,
    setFirstElementSelected: Boolean = false
  ) {
    if (list.isNotEmpty() and setFirstElementSelected)
      setSelectedText(list[0])
    listMenu.setAdapter(ArrayAdapter(context, layout.simple_spinner_dropdown_item, list))
    listMenu.setOnItemClickListener { _, _, position, _ ->
      materialDropDownListener?.selectedItem(list[position],position)
      setSelectedText(list[position])
      selectedItemPosition = position
      listMenu.dismiss()
    }
  }

  fun getText(): String = etDropDown.text.toString()

  fun getSelectedItemPosition() = selectedItemPosition

  fun setItemClickListener(listener: OnItemClickListener) {
    listMenu.setOnItemClickListener(listener)
  }

  /**
   * set Custom Adapter to show custom view in list
   */
  fun setCustomAdapter(adapter: ListAdapter) {
    listMenu.setAdapter(adapter)
  }

  fun setHint(hint: String) {
    tilMaterialDropdown.hint = hint
  }

  private fun setSelectedText(text: String) {
    etDropDown.setText(text)
    listMenu.dismiss()
  }

  private fun hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(applicationWindowToken, 0)
  }

  private fun showList() {
    if ((context as AppCompatActivity).hasWindowFocus()) listMenu.show()
  }

}

