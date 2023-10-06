package com.androidstudy.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.androidstudy.customview.R.styleable.CustomViewLoginButton_loginButtonBackgroundColor
import com.androidstudy.customview.R.styleable.CustomViewLoginButton_loginButtonTextColor
import com.androidstudy.customview.R.styleable.CustomViewLoginButton_loginButtonTitle
import com.androidstudy.customview.databinding.CustomViewLoginButtonBinding

class LoginButtonCustomView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var _binding: CustomViewLoginButtonBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomViewLoginButtonBinding.inflate(LayoutInflater.from(context), this, true)
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomViewLoginButton)
            val customLoginTitle = typedArray.getString(CustomViewLoginButton_loginButtonTitle)
            val customLoginBackgroundColor = typedArray.getColor(
                CustomViewLoginButton_loginButtonBackgroundColor,
                Color.WHITE
            )
            val customLoginBackgroundTextColor = typedArray.getColor(
                CustomViewLoginButton_loginButtonTextColor,
                Color.BLACK
            )
            val customLoginLogo = typedArray.getResourceId(
                R.styleable.CustomViewLoginButton_loginButtonLogo,
                R.drawable.ic_launcher_foreground
            )
            typedArray.recycle()

            updateView(
                customLoginTitle,
                customLoginBackgroundColor,
                customLoginBackgroundTextColor,
                customLoginLogo
            )
        }
    }

    private fun updateView(title: String?, backgroundColor: Int, textColor: Int, logoResId: Int) {
        with(binding) {
            customViewLoginTitle.text = title
            customViewLoginTitle.setTextColor(textColor)
            customViewLoginLayout.backgroundTintList = ColorStateList.valueOf(backgroundColor)
            val logoDrawable = ResourcesCompat.getDrawable(resources, logoResId, null)
            customViewLoginLogo.setImageDrawable(logoDrawable)
        }
    }

}