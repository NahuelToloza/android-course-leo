package com.example.pokemonclasses.presentation.ui.fragments.profile

import android.Manifest
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pokemonclasses.R
import com.example.pokemonclasses.databinding.FragmentProfileBinding
import com.example.pokemonclasses.presentation.ui.viewmodel.ProfileViewModel
import com.example.pokemonclasses.presentation.ui.viewmodel.SetupView
import com.example.pokemonclasses.presentation.ui.viewmodel.profile.ProfilePictureCommunicationViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Locale

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private val communicationViewModel: ProfilePictureCommunicationViewModel by activityViewModels()

    private var uri: Uri? = null

    private val requestPickGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { imageUri ->
        imageUri?.let {
            val bitmap = if (Build.VERSION.SDK_INT >= 29) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        imageUri
                    )
                )
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            }

            getImageUriFromBitmap(bitmap)?.apply {
                binding.imgProfile.setImageURI(this)
                viewModel.profilePictureWasTaken(this)
            }
        }
    }

    private val requestGalleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            requestPickGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            showMessage(R.string.gallery_permission_not_granted)
        }
    }

    private val requestTakePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { wasSaved ->
        if (wasSaved) {
            binding.imgProfile.setImageURI(uri)
            viewModel.profilePictureWasTaken(uri)
        } else {
            showMessage(R.string.error_picture_not_saved)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpeg"
            val file = File(requireActivity().getExternalFilesDir(null), name)
            uri = FileProvider.getUriForFile(
                requireContext(),
                requireActivity().application.packageName + ".provider",
                file
            )
            requestTakePhoto.launch(uri)
        } else {
            showMessage(R.string.camera_permission_not_granted)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        viewModel.getProfileData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            it.setupViews?.getContentIfNotHandled()?.let { setupViewModel ->
                setupViews(setupViewModel)
            }
            it.showMessage?.getContentIfNotHandled()?.let { messageRes ->
                showMessage(messageRes)
            }
        }
        communicationViewModel.clickCamera.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                askCameraPermission()
            }
        }
        communicationViewModel.clickGallery.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                requestGalleryPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            }
        }
    }

    private fun setupListeners() {
        binding.imgProfile.setOnClickListener {
            //askCameraPermission()
            val action =
                ProfileFragmentDirections.actionProfileFragmentToProfilePictureBottomSheetDialogFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupViews(setupView: SetupView) {
        binding.tvEmailValue.text = setupView.email
        binding.imgProfile.setImageURI(setupView.profilePicture)
    }

    private fun askCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun showMessage(messageRes: Int) {
        Snackbar.make(binding.root, getString(messageRes), Snackbar.LENGTH_LONG).show()
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri? {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageInQ(bitmap, filename)
        } else {
            saveImageInLegacy(bitmap, filename)
        }
    }

    //Make sure to call this function on a worker thread, else it will block main thread
    private fun saveImageInQ(bitmap: Bitmap, filename: String): Uri? {
        var fos: OutputStream?
        var imageUri: Uri?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        val contentResolver = requireActivity().application.contentResolver

        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }

        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        imageUri?.let { contentResolver.update(it, contentValues, null, null) }
        return imageUri
    }

    //Make sure to call this function on a worker thread, else it will block main thread
    private fun saveImageInLegacy(bitmap: Bitmap, filename: String): Uri? {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        val fos = FileOutputStream(image)
        fos.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        return Uri.fromFile(requireContext().getFileStreamPath(filename))
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
