package com.inverse.core.adapters

abstract class RecyclerModel {
    var adapterPosition: Int = -1
    var onItemClickEvent: RecyclerAdapter.OnItemClickEvent? = null
}