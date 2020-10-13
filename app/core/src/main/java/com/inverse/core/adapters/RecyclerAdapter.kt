package com.inverse.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.inverse.core.BR


class RecyclerAdapter<T : RecyclerModel>(@LayoutRes val layoutId: Int) :
    RecyclerView.Adapter<RecyclerAdapter.GenericViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    private var onItemClickEvent: OnItemClickEvent? = null
    private var createAnimation:Animation?=null
    private var bindAnimation:Animation?=null
    interface OnItemClickEvent {
        fun onClick(view: View, item: Any)
    }


    fun addItems(items: List<T>?) {
        this.items.clear()
        if(items!=null){
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun onItemClickEvent(itemClickEvent: OnItemClickEvent?){
        this.onItemClickEvent = itemClickEvent
    }
    fun createAnimation(animations: Animation?){
        this.createAnimation = animations
    }
    fun bindnimation(animations: Animation?){
        this.bindAnimation = animations
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        createAnimation?.let {
            binding.root.startAnimation(createAnimation)
        }
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        onItemClickEvent?.let {
            itemViewModel.onItemClickEvent = it }
        holder.bind(itemViewModel)
        bindAnimation?.let {
            holder.itemView.startAnimation(bindAnimation)
        }
    }


    class GenericViewHolder<T : RecyclerModel>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemViewModel: T) {
            binding.setVariable(BR.item, itemViewModel)
            binding.executePendingBindings()
        }

    }


}