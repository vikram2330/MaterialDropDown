# MaterialDropDown
A dropdown which looks like material component outlined TextInputLayout.

![Screenshot](https://media.giphy.com/media/9D5bX4rGGf3R4L0hzJ/giphy.gif)


##Syntax
```
materialDropDownCountries.setList(list,object :MaterialDropDownListener{
      override fun selectedItem(
        text: String,
        position: Int
      ) {
      //Do something with selected Item
      }

    })
```
    
 you can pass ```true``` to set first item of list as selected Item
    
