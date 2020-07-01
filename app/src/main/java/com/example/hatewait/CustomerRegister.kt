package com.example.hatewait

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_customer_register.*
import kotlinx.android.synthetic.main.activity_customer_register.id_input_editText
import kotlinx.android.synthetic.main.activity_customer_register.id_input_layout
import kotlinx.android.synthetic.main.activity_customer_register.password_input_editText
import kotlinx.android.synthetic.main.activity_customer_register.password_input_layout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


// App Bar 추가
class CustomerRegister : AppCompatActivity() {
    private val idRegex = Regex("^(?=.*[a-zA-Zㄱ-ㅎ가-힣0-9])[a-zA-Zㄱ-ㅎ가-힣0-9]{1,}$")
    private val passwordRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    fun verifyId(input_id: String): Boolean = idRegex.matches(input_id)
    fun verifyPassword(input_password: String): Boolean = passwordRegex.matches(input_password)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)
        addTextChangeListener()
        button_continue.setOnClickListener {
//            startActivity<CustomRegister2>()
            val intent = Intent(this, CustomRegister2::class.java)
            intent.putExtra("user_id", id_input_editText.text.toString())
            intent.putExtra("user_password", password_input_editText.text.toString())
//                시작 중인 활동이 현재 활동(백 스택의 맨 위에 있는)이면 활동의 새 인스턴스가 생성되는 대신 기존 인스턴스가 onNewIntent() 호출을 수신합니다.
            intent.flags = FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
//        Set a Toolbar to act as the ActionBar for this Activity window
        setSupportActionBar(register_toolbar)
// Default Task : App Name + 더보기 // Option Menu has only a'settings'
        supportActionBar?.apply {
//            Set whether home should be displayed as an "up" affordance.
//            Set this to true if selecting "home" returns up by a single level in your UI rather than back to the top level or front page.
            setDisplayHomeAsUpEnabled(true)
//            you should also call setHomeActionContentDescription() to provide a correct description of the action for accessibility support.
            setHomeAsUpIndicator(R.drawable.back_icon)

            setHomeActionContentDescription("로그인 화면 이동")
            setDisplayShowTitleEnabled(false)
//            setDisplayShowHomeEnabled(true)
        }
    }

//    앱내의 여러 액티비티가 같은 포멧의 옵션 메뉴를 제공할 경우
//    onCreateOptionsMenu, onOptionItemSelected Method만 구현해놓은 액티비티를 만들어두는것도 좋음.

//     Menu 추가 콜백 메소드
//    Initialize the contents of the Activity's standard options menu.
//    메뉴를 생성하는 최초 1번만 호출함.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back_front_button_menu, menu)
        return true

    }


    //    메뉴에서 선택된 아이템 클릭 리스너 역할
//    When you add items to the menu, you can implement the Activity's onOptionsItemSelected method to handle them there.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.forward_button -> {
//                입력완료 or 앞서 입력했던 이름, 주소 액티비티 상태로 이동.
                val intent = Intent(this, CustomRegister2::class.java)
                intent.putExtra("user_id", id_input_editText.text.toString())
                intent.putExtra("user_password", password_input_editText.text.toString())
//                시작 중인 활동이 현재 활동(백 스택의 맨 위에 있는)이면 활동의 새 인스턴스가 생성되는 대신 기존 인스턴스가 onNewIntent() 호출을 수신합니다.
                intent.flags = FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return if (button_continue.isEnabled) {
            menuInflater.inflate(R.menu.back_front_button_menu, menu)
            true
        } else {
            false
        }
    }

    private fun addTextChangeListener() {
        id_input_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!verifyId(s.toString())) {
                    id_input_layout.error = "특수문자나 공백은 허용되지 않습니다."
                    button_continue.isEnabled = false
                } else {
                    id_input_layout.error = null
                    id_input_layout.hint = null
                }
                button_continue.isEnabled =
                        (id_input_layout.error == null
                            && password_input_layout.error == null
                            && password_reinput_layout.error == null
                            && !password_input_editText.text.isNullOrBlank()
                            && !password_reinput_editText.text.isNullOrBlank())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        password_input_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!verifyPassword(s.toString())) {
                    password_input_layout.error = "영문, 숫자, 특수문자 포함 8자 이상 입력해주세요"
                    button_continue.isEnabled = false
                } else {
                    password_input_layout.error = null
                    password_input_layout.hint = null
                }
                button_continue.isEnabled =
                        (id_input_layout.error == null
                            && password_input_layout.error == null
                            && password_reinput_layout.error == null
                            && !id_input_editText.text.isNullOrBlank()
                            && !password_reinput_editText.text.isNullOrBlank())
// enabled 상태에 따라 button 색상 ColorPrimary 로 설정할 수 있어야함. (selector 사용 or app Compat Button)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        password_reinput_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(reinputText: Editable?) {
                if (reinputText.toString() != password_input_editText.text.toString()) {
                    password_reinput_layout.error = "비밀번호가 일치하지 않아요"
                    button_continue.isEnabled = false
                } else {
                    password_reinput_layout.error = null
                    password_reinput_layout.hint = null
                }
                button_continue.isEnabled =
                    (id_input_layout.error == null
                            && password_input_layout.error == null
                            && password_reinput_layout.error == null
                            && !id_input_editText.text.isNullOrBlank()
                            && !password_input_editText.text.isNullOrBlank())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun inputLayoutInitialize() {
        id_input_editText.text?.clear()
        id_input_layout.error = null
        id_input_layout.clearFocus()
        id_input_layout.hint = resources.getString(R.string.id_input_hint)
        password_input_editText.text?.clear()
        password_input_layout.clearFocus()
        password_input_layout.error = null
        password_input_layout.hint = resources.getString(R.string.password_input_hint)
        password_reinput_editText.text?.clear()
        password_reinput_layout.error = null
        password_reinput_layout.clearFocus()
        password_reinput_layout.hint = resources.getString(R.string.password_reinput_hint)
    }

    override fun onStop() {
//        inputLayoutInitialize()
        super.onStop()
    }

}