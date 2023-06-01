package com.elaniin.technical_test.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.elaniin.technical_test.R
import com.elaniin.technical_test.databinding.ActivityLoginBinding
import com.elaniin.technical_test.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    private val googleSO: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
            .build()
    }
    private val googleSC: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, googleSO)
    }

    private val resultLauncherGoogleLogin =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            showLoading(false)
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val email = account.email
                    if (email != null) {
                        viewModel.prefs.accountName = account.displayName ?: "-"
                        viewModel.prefs.accountEmail = account.email ?: ""
                        changeActivity()
                    } else {
                        showErrorLogin()
                    }
                } catch (e: ApiException) {
                    showErrorLogin()
                    e.printStackTrace()
                }
            } else {
                showErrorLogin()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {
        showLoading()
        val intent = googleSC.signInIntent
        resultLauncherGoogleLogin.launch(intent)
    }

    private fun changeActivity(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    private fun showLoading(show: Boolean = true) {
        binding.progressBar.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showErrorLogin() {
        showLoading(false)
        Toast.makeText(
            this,
            getString(R.string.error_login),
            Toast.LENGTH_SHORT
        ).show()
    }
}