package com.inverse.photoeditor.screens

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.inverse.core.adapters.RecyclerAdapter
import com.inverse.core.baseClasses.BaseFragment
import com.inverse.photoeditor.R
import com.inverse.photoeditor.databinding.ImagePickerFragmentBinding
import com.inverse.photoeditor.models.ImageFile

class ImagePicker : BaseFragment<ImagePickerFragmentBinding,ImagePickerViewModel>() {

    companion object {
        fun newInstance() = ImagePicker()
    }

    override val viewModel: ImagePickerViewModel by viewModels<ImagePickerViewModel>()

    override fun getLayoutResId(): Int  = R.layout.image_picker_fragment

    override fun init() {
        val items = mutableListOf<ImageFile>()
        items.add(ImageFile("/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg","/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg"))
        items.add(ImageFile("/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg","/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg"))
        items.add(ImageFile("/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg","/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg"))
        items.add(ImageFile("/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg","/storage/emulated/0/DCIM/Camera/IMG_20201012_220113.jpg"))
        binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),4)
        binding.recyclerView.adapter = RecyclerAdapter<ImageFile>(R.layout.image_item_layout).apply {
            addItems(items)
            notifyDataSetChanged()
        }
    }

}