package com.inverse.photoeditor.models

import com.inverse.core.adapters.RecyclerModel
import java.net.URI

data class ImageFile(val path:String,val uri: String,val title:String,val selected:Boolean):RecyclerModel(){
    constructor(path: String,uri: String):this(path,uri,"",false)
}