package com.example.Jachi3kki.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Jachi3kki.MainActivity
import com.example.Jachi3kki.R
import com.example.Jachi3kki.SplashActivity
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.Profile
import com.kakao.util.OptionalBoolean
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private var email : String? = null
    private var id : String? = null
    var profile : Profile? = null

    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

//        btn_login!!.setOnClickListener {
//            session?.open(
//                AuthType.KAKAO_LOGIN_ALL,
//                MainActivity.instance
//            )
//        }


        btn_register.setOnClickListener{
            updateKakaoInfo()
            if(id == null){
                Toast.makeText(MainActivity.instance, "로그인 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            }else{
                println("test:" + id + email)
                Toast.makeText(MainActivity.instance, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.mainFragment)
            }
        }

    }

    fun updateKakaoInfo(){
        id = MainActivity.id
        email = MainActivity.email
        profile = MainActivity.profile
    }
}