package com.example.pokemonclasses.presentation.ui.fragments.profile

import android.Manifest
import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
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
import com.example.pokemonclasses.utils.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private val communicationViewModel: ProfilePictureCommunicationViewModel by activityViewModels()

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var imageCapture: ImageCapture

    private var uri: Uri? = null

    private val requestPickGallery = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        binding.imgProfile.setImageURI(uri)
        viewModel.profilePictureWasTaken(uri)
    }

    private val requestGalleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
            if (permissions.all { it.value }) {
                val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(System.currentTimeMillis()) + ".jpeg"
                val file = File(requireActivity().getExternalFilesDir(null), name)
                uri = FileProvider.getUriForFile(
                    requireContext(),
                    requireActivity().application.packageName + ".provider",
                    file
                )
                requestPickGallery.launch(uri)
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
        cameraExecutor = Executors.newSingleThreadExecutor()
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
                requestGalleryPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))
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
        binding.imageCaptureButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun setupViews(setupView: SetupView) {
        binding.tvEmailValue.text = setupView.email
        binding.imgProfile.setImageURI(setupView.profilePicture)
    }

    private fun askCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun starCamera() {
        showCamera()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                imageCapture = ImageCapture.Builder()
                    .setTargetRotation(requireView().display.rotation)
                    .build()
                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }


        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun showCamera() {
        binding.clPreviewView.visible()
    }

    private fun takePhoto() {
        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }

    private fun showMessage(messageRes: Int) {
        Snackbar.make(binding.root, getString(messageRes), Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}
