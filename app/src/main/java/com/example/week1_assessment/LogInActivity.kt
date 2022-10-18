package com.example.week1_assessment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LogInActivity : AppCompatActivity() {

    private lateinit var _txtCreateAccount: TextView
    private lateinit var _imageVPrecedent: ImageView
    private lateinit var _txtEmailAd: TextView
    private lateinit var _etEmailAd: EditText
    private lateinit var _tvPassword: TextView
    private lateinit var _etPassword: EditText
    private lateinit var _txtRepeatPassword: TextView
    private lateinit var _etRepeatPassword: EditText
    private lateinit var _txtInfoPassword: TextView
    private lateinit var _btnNext: Button
    private lateinit var _txtErrorEmail: TextView
    private lateinit var _txtErrorPassword: TextView

    private var _isValidate: Boolean = false
    private var _emailValidate : Boolean =false //can i dont use this?
    private var _passwordsValid: Boolean = // used tto check tthe msg of password error(dont match or invalid)
        false //if (!_passwordsValid) showmessage(one of your password wasn't valid
    val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}".toRegex()


   // private val clickListener: ClickListener? = null


//    @SuppressLint("ClickableViewAccessibility") I dont know how i got this maybe i clicked wrong combinaison
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)
        Log.d(this.localClassName, "onCreate: ")

        initView()

        _etEmailAd.setOnFocusChangeListener { v, hasFocus -> emailFocusChangeListener(v, hasFocus) }

        _etPassword.setOnFocusChangeListener { _, hasFocus -> passwordFocusChangeListener(hasFocus) }

        _etRepeatPassword.setOnFocusChangeListener { _, hasFocus ->repeatPasswordFocusChangeListener(hasFocus) }

        _btnNext.setOnClickListener {_ -> btnClickListener() }//(this::btnClickListener)//

        _imageVPrecedent.setOnClickListener(this::navigatePrecedent)

    /**
     * trying tto use textwacher for checking passwords
     */
//        _etPassword.addTextChangedListener(textWatcher)
//        _etRepeatPassword.addTextChangedListener(textWatcher2)
    /**
     * trying to make a clickListener on the drawable of the textView
     * by calling the section at the end of this code
     */
//        _txtCreateAccount.setOnTouchListener() { view: View, motionEvent: MotionEvent ->
//            ClickListener.onClick(ClickListener.DrawablePosition.LEFT) {
//                if (ClickListener.DrawablePosition.LEFT) {
//                    println()
//                }
//            }
//        }
    }

    private fun initView() {
        _txtCreateAccount = findViewById(R.id.tvCreateAccount)
        _imageVPrecedent = findViewById(R.id.imageVPrecedent)
        _txtEmailAd = findViewById(R.id.txtEmailAd)
        _etEmailAd = findViewById(R.id.etEmailAd)
        _tvPassword = findViewById(R.id.tvPassword)
        _etPassword = findViewById(R.id.etPassword)
        _txtRepeatPassword = findViewById(R.id.txtRepeatPassword)
        _etRepeatPassword = findViewById(R.id.etRepeatPassword)
        _txtInfoPassword = findViewById(R.id.txtInfoPassword)
        _btnNext = findViewById(R.id.btnNext)
        _txtErrorEmail = findViewById(R.id.txtErrorEmail)
        _txtErrorPassword = findViewById(R.id.txtErrorPassword)

        _txtCreateAccount.text = getString(R.string.btn_create)
        _txtEmailAd.text = getString(R.string.tv_email_address)
        _tvPassword.text = getString(R.string.tv_password)
        _txtRepeatPassword.text = getString(R.string.tv_repeat_password)
        _txtInfoPassword.text = getString(R.string.tv_info_password)
        _btnNext.text = getString(R.string.btn_next)
        _txtErrorEmail.text = getString(R.string.txt_error_email)
        // _txtErrorPassword.text = getString(R.string.txt_err_match_password)

    }

    private fun navigatePrecedent(view: View) {
        val navigateIntent =
            Intent()  //os checks first if it has enough resources to open tthe activity
        navigateIntent.setClass(this, MainActivity::class.java)
        startActivity(navigateIntent)
    }

    private fun emailFocusChangeListener(v: View, hasFocus: Boolean) {
        val _email: String = _etEmailAd.text.toString()
        if (!hasFocus) {
            if (isEmailValid(_email)) {
                _emailValidate = true
                _etEmailAd.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.tick, 0
                )
                _etEmailAd.setBackgroundResource(R.drawable.ic_outline_check)
                _txtErrorEmail.visibility = View.GONE
            } else {
                _emailValidate = false
                _etEmailAd.setBackgroundResource(R.drawable.txt_error)
                _txtErrorEmail.visibility = View.VISIBLE
            }
        }
    }

    private fun passwordFocusChangeListener(hasFocus: Boolean) {
        var pwd: String = _etPassword.text.toString()
        var pwdR: String = _etRepeatPassword.text.toString()
        // val matchResult = regex.matches(pwd) => when i do this sometimes the regex works sometimes no
        if (!hasFocus) {

            if (isPasswordValid(pwd)) {
                println("passwordFocusChangeListener   ${_isValidate.toString()}")
                todoPasswordValid(_etPassword)
                if (pwdR != null && !pwdR.isBlank()) checkPasswords(pwd, pwdR)
            } else {
                _passwordsValid = false
                todoPasswordInvalid(_etPassword, _etRepeatPassword)
            }
        }
    }

   private fun repeatPasswordFocusChangeListener(hasFocus: Boolean) {
        var pwd: String = _etPassword.text.toString()
        var pwdR: String = _etRepeatPassword.text.toString()
        if (!hasFocus) checkPasswords(pwd, pwdR)
    }
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(pwd: String): Boolean {
        return regex.matches(pwd)
    }

    private fun checkPasswords(pwd: String, pwdR: String) {
        if (isPasswordValid(pwdR) && pwd == pwdR) {
            todoPasswordValid(_etRepeatPassword)
            todoPasswordValid(_etPassword)
            _isValidate = true
        } else {
            _isValidate = false
            _passwordsValid = isPasswordValid(pwdR)
            todoPasswordInvalid(_etPassword, _etRepeatPassword)
        }
    }

    private fun todoPasswordInvalid(etPwd: EditText, etPwdR: EditText) {
        etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        etPwd.setBackgroundResource(R.drawable.txt_error)
        etPwdR.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        etPwdR.setBackgroundResource(R.drawable.txt_error)
        //println("test--- ${_passwordsValid.toString()}_____${etPwd.text.toString() == etPwdR.text.toString()}")
        if (_passwordsValid) _txtErrorPassword.text = getString(R.string.txt_err_match_password)
        else _txtErrorPassword.text = getString(R.string.txt_err_invalid_password)
        _txtErrorPassword.visibility = View.VISIBLE
        _isValidate = false
    }
    private fun todoPasswordValid(pwd: EditText) {
        pwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.tick, 0)
        pwd.setBackgroundResource(R.drawable.ic_outline_check)
        _txtErrorPassword.visibility = View.GONE
        _passwordsValid = true
    }
   private fun btnClickListener() {
        _etEmailAd.clearFocus()
        _etPassword.clearFocus()
        _etRepeatPassword.clearFocus()
       if (!_isValidate || !_emailValidate /*|| !_passwordsValid*/) showError("Please check your information !")
       else showError("Account created successfully !")
    }
    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.localClassName.toString(), "onStart: ")
    }


    /**
     * trying tto use textwacher for checking passwords
     */
    //*********
//    private val textWatcher = object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            val output: CharSequence? = s
//            if (s.toString() == textWatcher2.toString()) {
//                _etPassword.setBackgroundResource(R.drawable.ic_outline_check)
//            } else {
//                _etPassword.setBackgroundResource(R.drawable.txt_error)
//            }
//        }
//    }
    /**
     * trying to make a clickListener on the drawable of the textView
     * by calling thit section
     */
//    private val textWatcher2 = object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            val output: CharSequence? = s
//            if (start == 12) {
//                Toast.makeText(applicationContext, "Maximum Limit Reached", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }

//    interface ClickListener {
//        enum class DrawablePosition {
//            TOP, BOTTOM, LEFT, RIGHT
//        }
//
//        fun onClick(target: DrawablePosition?)
//    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            // this works for left since container shares 0,0 origin with bounds
//            if (drawableLeft != null) {
//                if (clickListener != null) {
//                    clickListener
//                        .onClick(ClickListener.DrawablePosition.LEFT);
//                    event.setAction(MotionEvent.ACTION_CANCEL);
//                    return false;
//                }
//            }
//            return super.onTouchEvent(event);
//        }
//        return super.onTouchEvent(event);
//    }

//    @Throws(Throwable::class)
//    protected fun finalize() {
//        drawableLeft = null
//        finalize()
//    }

}