package com.test.androidmini_memoapp2

import android.content.Intent
import android.graphics.Color
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.test.androidmini_memoapp2.databinding.ActivityMainBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    //비밀번호가 설정되었는지 확인하는 변수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        var isSetPassword=getPasswordExist()

        when(isSetPassword){
            //비밀번호가 설정되지 않았을 때
            false->{
                activityMainBinding.run {
                    toolbar.title="비밀번호 설정"
                    toolbar.setTitleTextColor(Color.WHITE)
                    login.text="설정 완료"
                    login.setOnClickListener {
                        var Password1=inputPassword.text.toString().toInt()
                        var Password2=inputPasswordAgain.text.toString().toInt()
                        if(Password1==Password2){

                            val fos=openFileOutput("password.txt", MODE_PRIVATE)
                            val dos=DataOutputStream(fos)
                            dos.writeInt(Password1)
                            dos.flush()
                            dos.close()

                            toolbar.title="로그인"
                            toolbar.setTitleTextColor(Color.WHITE)
                            login.text="로그인"
                            notSetPassword.visibility= View.GONE
                            inputPassword.setText("")
                            login.setOnClickListener{
                                val inputPassword=inputPassword.text.toString().toInt()
                                val fis=openFileInput("password.txt")
                                val dis= DataInputStream(fis)
                                val filePassword = dis.readInt()
                                dis.close()
                                fis.close()
                                if(inputPassword==filePassword){
                                    startActivity(Intent(this@MainActivity,CategoryActivity::class.java))
                                }else{
                                    Toast.makeText(this@MainActivity,"비밀번호가 틀립니다!",Toast.LENGTH_SHORT).show()
                                }
                            }


                        }else{
                            Toast.makeText(this@MainActivity,"입력한 두 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
                            inputPassword.setText("")
                            inputPasswordAgain.setText("")
                        }

                    }
                }
            }
            //비밀번호가 설정되었을 떼
            true->{
                activityMainBinding.run {
                    toolbar.title="로그인"
                    toolbar.setTitleTextColor(Color.WHITE)
                    login.text="로그인"
                    notSetPassword.visibility= View.GONE
                    login.setOnClickListener {
                        val inputPassword=inputPassword.text.toString().toInt()
                        val fis=openFileInput("password.txt")
                        val dis= DataInputStream(fis)
                        val filePassword = dis.readInt()
                        dis.close()
                        fis.close()
                        if(inputPassword==filePassword){
                            startActivity(Intent(this@MainActivity,CategoryActivity::class.java))
                        }else{
                            Toast.makeText(this@MainActivity,"비밀번호가 틀립니다!",Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }
        }











    }
    fun getPasswordExist():Boolean{
        val filePath=getFileStreamPath("password.txt")
//        val file=File("/data/data/com.test.androidmini_memoapp2/files/password.txt")
        val file=File(filePath.toString())
        val isfile=file.exists()
        return isfile
    }
}
