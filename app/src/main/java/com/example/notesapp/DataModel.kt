package com.example.notesapp

data class DataModel(var key : String, var title : String, var content : String, var color : Int){
    constructor():this("", "", "", 0)
}
