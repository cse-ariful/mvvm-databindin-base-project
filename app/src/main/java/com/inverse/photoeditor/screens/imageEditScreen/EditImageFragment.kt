package com.inverse.photoeditor.screens.imageEditScreen

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.inverse.core.baseClasses.BaseFragment
import com.inverse.photoeditor.R
import com.inverse.photoeditor.databinding.EditImageFragmentBinding

class EditImageFragment : BaseFragment<EditImageFragmentBinding,EditImageViewModel>(){

    companion object {
        fun newInstance() = EditImageFragment()
    }

    override val viewModel: EditImageViewModel by viewModels<EditImageViewModel>()

    override fun getLayoutResId(): Int = R.layout.edit_image_fragment

    override fun init() {
        binding.backBtn.setOnClickListener {
            it.findNavController().navigateUp()
        }
        val imageUrl = arguments?.let {
            EditImageFragmentArgs.fromBundle(it).imageUri
        }.apply {
            Glide.with(requireContext()).load(this).placeholder(R.drawable.loading).into(binding.imageView)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
}