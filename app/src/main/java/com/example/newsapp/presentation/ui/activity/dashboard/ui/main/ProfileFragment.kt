package com.example.newsapp.presentation.ui.activity.dashboard.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.presentation.ui.activity.auth.LoginActivity
import com.example.newsapp.presentation.ui.activity.dashboard.HomeActivity
import com.example.newsapp.presentation.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

       return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val userModel = userViewModel.getUserSessionInfo()

            if(userModel != null){
                firstNameEditText = view.findViewById(R.id.firstNameText)
                lastNameEditText = view.findViewById(R.id.lastNameText)
                emailEditText = view.findViewById(R.id.emailText)

                firstNameEditText.setText(userModel?.firstName)
                lastNameEditText.setText(userModel?.lastName)
                emailEditText.setText(userModel?.email)
            }
        }

        val logoutButton = view.findViewById<TextView>(R.id.logoutButton)
        logoutButton.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Action")
                .setMessage("Are you sure you want to Logout now?")
                .setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    userViewModel.addUserSessionInfo(null)
                    // Trigger your retrofit call here
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()

        }
    }



}