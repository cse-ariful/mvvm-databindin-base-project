package com.inverse.photoeditor.screens.imagePicker

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.inverse.core.adapters.RecyclerAdapter
import com.inverse.core.baseClasses.BaseFragment
import com.inverse.photoeditor.MainActivity
import com.inverse.photoeditor.R
import com.inverse.photoeditor.databinding.ImagePickerFragmentBinding
import com.inverse.photoeditor.models.ImageFile
import kotlinx.android.synthetic.main.image_picker_fragment.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ImagePicker : BaseFragment<ImagePickerFragmentBinding, ImagePickerViewModel>() {

    companion object {
        fun newInstance() = ImagePicker()
        const val TAG ="imagePicker"
    }

    override val viewModel: ImagePickerViewModel by viewModels<ImagePickerViewModel>()
    override fun getLayoutResId(): Int  = R.layout.image_picker_fragment

    override fun init() {
        Log.d(TAG, "init: ")
        binding.swipeRefreshLayout.setOnRefreshListener {
            methodRequiresTwoPermission()
        }
        viewModel.images.observe(viewLifecycleOwner, Observer {
            if (binding.swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = false
            }

            binding.recyclerView.adapter =
                RecyclerAdapter<ImageFile>(R.layout.image_item_layout).apply {
                    addItems(it)
                    notifyDataSetChanged()
                    val eventListener = object : RecyclerAdapter.OnItemClickEvent {
                        override fun onClick(view: View, item: Any) {
                            val extras = FragmentNavigatorExtras(
                                view to "editImage"
                            )
                            val model = item as ImageFile
                            var direction = ImagePickerDirections.pickerToEditImage(item.uri)
                            Navigation.findNavController(view).navigate(direction, extras)
                        }
                    }
                    onItemClickEvent(eventListener)
                }
        })
        if(!viewModel.initialized){
            methodRequiresTwoPermission()
        }

    }
    fun loadImages(){
        if(binding.swipeRefreshLayout.isRefreshing){
            swipeRefreshLayout.isRefreshing=false
        }
        viewModel.getAllImages(null, requireActivity())
    }

    @AfterPermissionGranted(MainActivity.RC_CAMERA_AND_LOCATION)
    private fun methodRequiresTwoPermission() {
        Log.d(TAG, "methodRequiresTwoPermission: ")
        val perms =
            arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            loadImages()
        } else {
            EasyPermissions.requestPermissions(
                requireActivity(), getString(R.string.camera_and_location_rationale),
                MainActivity.RC_CAMERA_AND_LOCATION, *perms
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                requireContext(),
                "Permission changed",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                startPostponedEnterTransition()
                view.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })
    }

}