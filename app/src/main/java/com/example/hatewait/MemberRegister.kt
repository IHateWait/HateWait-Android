package com.example.hatewait

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_members_register.*
import org.jetbrains.anko.support.v4.startActivity

class MemberRegister : Fragment() {
    val idRegex = Regex("^(?=.*[a-zA-Zㄱ-ㅎ가-힣0-9])[a-zA-Zㄱ-ㅎ가-힣0-9]{1,}$")

    fun verifyId(input_id : String) : Boolean = idRegex.matches(input_id)

    //   fragment 안에서 옵션 선택을 가능하게함.
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View ? {
        return inflater.inflate(R.layout.activity_members_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        user_id_input_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!verifyId(s.toString())) {
                    user_id_input_layout.error = "특수문자나 공백은 허용되지 않습니다."
                    register_customer_button.isEnabled = false
                } else {
                    user_id_input_layout.error = null
                    register_customer_button.isEnabled = true
                }

            }
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        register_customer_button.setOnClickListener {
//            등록이 끝났다면 지워줌!
            Toast.makeText(context, "등록되었어요!", Toast.LENGTH_SHORT).show()
            startActivity<Register_Check>(
                "USER_ID" to user_id_input_editText.toString()
            )
            inputLayoutInitialize()
        }
        super.onActivityCreated(savedInstanceState)

    }

    fun inputLayoutInitialize() {
        user_id_input_editText.error = null
        user_id_input_editText.clearFocus()
        user_id_input_editText.text?.clear()
    }


}