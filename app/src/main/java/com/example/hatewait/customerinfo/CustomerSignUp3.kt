package com.example.hatewait.customerinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import com.example.hatewait.R
import com.example.hatewait.socket.CustomerRegisterAsyncTask
import kotlinx.android.synthetic.main.activity_customer_register3.*


// 1단계 이메일 , 인증번호 (네아로면 생략)
// 2단계 비번, 비번확인
// 3단계 이름(네아로 생략), 전화번호
// 가입완료 환영 메시지 액티비티 or 로그인바로됨

class CustomerSignUp3 : AppCompatActivity() {
    //    한글 2~4자 (공백 허용 X) or 영문 First name 2~10, Last name 2~10
    private val nameRegex = Regex("^[가-힣]{2,4}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$")

    //    3자리 - 3 or 4자리 - 4자리
    //    첫자리는 반드시 0으로 시작.
    private val phoneRegex = Regex("^[0](\\d{2})(\\d{3,4})(\\d{4})")
    fun verifyName(input_name: String): Boolean = input_name.matches(nameRegex)
    fun verifyPhoneNumber(input_phone_number: String): Boolean =
        input_phone_number.matches(phoneRegex)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register3)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                Log.i("State", "onCreate Restore Instance")
                user_name_input_editText.setText(savedInstanceState.getString("USER_NAME"))
                user_phone_number_editText.setText(savedInstanceState.getString("USER_PHONE"))
            }

        }
        addTextChangeListener()

        button_finish.setOnClickListener {
            val userId = intent.getStringExtra("USER_ID")
            val userPassword = intent.getStringExtra("USER_PASSWORD")
            val userName = user_name_input_editText.text.toString()
            val userPhone = user_phone_number_editText.text.toString()
            CustomerRegisterAsyncTask(this@CustomerSignUp3).execute(
                userId,
                userPassword,
                userName,
                userPhone
            )
        }
        setSupportActionBar(register_toolbar)
        supportActionBar?.apply {
            //            Set this to true if selecting "home" returns up by a single level in your UI rather than back to the top level or front page.
            setDisplayHomeAsUpEnabled(true)
//            you should also call setHomeActionContentDescription() to provide a correct description of the action for accessibility support.
            setHomeAsUpIndicator(R.drawable.back_icon)
            setHomeActionContentDescription("아이디 패스워드 설정")
            setDisplayShowTitleEnabled(false)
        }
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return true
//    }

    //    뒤로가기 (onBackPressed)는 해당 Activity를 완전히 BackStack에서 제거하기 때문에
//    되살릴 수 있는 방법이없음.
//    살리고싶다면 Fragment 단위로 붙였다 떼어내야함.
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    //    뒤로 가기 버튼을 누르면 번들을 생성하며 기존 입력된
//    유저 이름과 전화번호를 번들에 저장함.
    override fun onBackPressed() {
        val bundle = Bundle()
//        bundle.putString("USER_NAME", user_name_input_editText.text.toString())
//        bundle.putString("USER_PHONE", user_phone_number_editText.text.toString())
        onSaveInstanceState(bundle)

        super.onBackPressed()
    }

    private fun addTextChangeListener() {
        user_name_input_editText.addTextChangedListener(object : TextWatcher {
            //            text에 변화가 있을 때마다
            override fun afterTextChanged(s: Editable?) {
                if (!verifyName(s.toString())) {
                    user_name_input_layout.error = "2~4자 한글 또는 영문이름을 입력해주세요"
                    button_finish.isEnabled = false
                } else {
                    user_name_input_layout.error = null
                    user_name_input_layout.hint = null
                }

                //    둘다 에러가 없을 때 등록 버튼 활성화
                button_finish.isEnabled =
                    (user_name_input_layout.error == null
                            && user_phone_number_layout.error == null
                            && !user_phone_number_editText.text.isNullOrBlank())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        user_phone_number_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!verifyPhoneNumber(s.toString())) {
                    user_phone_number_layout.error = "10~11자리 전화번호를 입력해주세요"
                    button_finish.isEnabled = false
                } else {
                    user_phone_number_layout.error = null
                    user_phone_number_layout.hint = null
                }
                button_finish.isEnabled =
                    (user_name_input_layout.error == null
                            && user_phone_number_layout.error == null
                            && !user_name_input_editText.text.isNullOrBlank())
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun inputLayoutInitialize() {
        user_name_input_editText.text?.clear()
        user_name_input_editText.clearFocus()
        user_name_input_layout.error = null
        user_name_input_layout.hint = "이름을 입력해주세요"
        user_phone_number_editText.text?.clear()
        user_phone_number_editText.clearFocus()
        user_phone_number_layout.error = null
        user_phone_number_layout.hint = "전화번호를 입력해주세요"
    }

    //outState – Bundle in which to place your saved state.
//    명시적으로 activity를 닫거나(ex. 뒤로가기) finish()호출시 다음 메소드는 호출되지않음.
//    onPause ~ onStop
    override fun onSaveInstanceState(outState: Bundle) {
//    입력했던것 저장
        Log.i("State", "save Instance")
        outState.putString("USER_NAME", user_name_input_editText.text.toString())
        outState.putString("USER_PHONE", user_phone_number_editText.text.toString())
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        Log.i("State", "Restore Instance")
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.run {
            user_name_input_editText.setText(savedInstanceState.getString("USER_NAME"))
            user_phone_number_editText.setText(savedInstanceState.getString("USER_PHONE"))
        }
    }

    override fun onStop() {
//        inputLayoutInitialize()
        Log.i("state", "onStop!")
        super.onStop()
    }

}