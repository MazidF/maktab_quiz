package com.example.myapplication.quiz3.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentQuiz3SigninBinding
import com.example.myapplication.quiz3.model.User
import com.example.myapplication.quiz3.model.UserItem
import com.example.myapplication.quiz3.network.NetworkManager3
import com.example.myapplication.quiz3.network.NetworkManager3.service
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class FragmentSignIn : Fragment() {
    val controller by lazy {
        findNavController()
    }
    lateinit var dialog: ProgressDialog
    lateinit var binding: FragmentQuiz3SigninBinding
    var user: UserItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuiz3SigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        NetworkManager3.client
        dialog = ProgressDialog(requireContext()).apply {
            setTitle("Creating User ....")
            setMessage("Please wait")
            setCancelable(false)
        }
        with(binding) {
            quiz3SignInBtn.setOnClickListener {
                if (check()) {
                    setUser()
                }
            }
        }
    }

    private fun setUser() {
        val context = requireContext()
        if (user == null) {
            Toast.makeText(context, "Failed to create user", Toast.LENGTH_SHORT).show()
            return
        }
        with(user!!) {
            val call = service.setUser(this)
            dialog.show()
            call.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()!!
                        val id = body.string().toString()
                        controller.navigate(FragmentSignInDirections.actionFragmentSignInToFragmentProfile(user!!.apply {
                            this.id = id
                        }))
                    } else {
                        Toast.makeText(context, "Failed.status code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "Failed!!", Toast.LENGTH_SHORT).show()
                }

            })
            dialog.cancel()
        }
    }

    private fun check(): Boolean {
        var name = ""
        var family = ""
        var nationalCode = ""
        var result = true
        with(binding) {
            if (quiz3SignInName.text!!.isNotBlank()) {
                name = quiz3SignInName.text.toString()
            } else {
                quiz3SignInName.error = "Invalid!!!"
                result = false
            }
            if (quiz3SignInFamily.text!!.isNotBlank()) {
                family = quiz3SignInFamily.text.toString()
            } else {
                quiz3SignInFamily.error = "Invalid!!!"
                result = false
            }
            if (quiz3SignInNationalCode.text!!.isNotBlank()) {
                nationalCode = quiz3SignInNationalCode.text.toString()
            } else {
                quiz3SignInNationalCode.error = "Invalid!!!"
                result = false
            }
        }
        if (result) {
            user = UserItem(name, listOf("movie", "game"), family, nationalCode)
        }
        return result
    }
}