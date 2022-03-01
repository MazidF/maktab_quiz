package com.example.myapplication.quiz3.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.quiz3.fragments.FragmentUser.Companion.USER
import com.example.myapplication.quiz3.model.User
import com.example.myapplication.quiz3.model.UserItem
import com.example.myapplication.quiz3.network.NetworkManager3
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream

class FragmentProfile : Fragment() {
    private lateinit var launcher: ActivityResultLauncher<Void>
    lateinit var binding: FragmentProfileBinding
    var user: User? = null
    var bitmap: Bitmap? = null
    val service by lazy {
        NetworkManager3.service
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = requireArguments().get(USER) as UserItem
        binding.user = user

        with(binding.profileUser) {
            userImage.setOnClickListener {
                launcher.launch(null)
            }
        }
        binding.profileUploadBtn.setOnClickListener {
            val bytes = getBytes() ?: return@setOnClickListener
            val body = MultipartBody.create(MediaType.parse("image/png"), bytes)
            val image = MultipartBody.Part.createFormData("image", "user_profile_image.png", body)
            val call = service.setImage(user.id, image)
            call.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Toast.makeText(requireContext(), "Done!!", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(requireContext(), "Failed!!", Toast.LENGTH_SHORT).show()}
            })
        }
    }

    private fun getBytes(): ByteArray? {
        bitmap?.let {
            val out = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, out)
            return out.toByteArray()
        }
        return null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            if (it != null) {
                this.bitmap = it
                binding.profileUser.userImage.setImageBitmap(it)
            }
        }
    }
}
