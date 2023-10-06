package com.woojugoing.study_customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class CustomView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private lateinit var layout: LinearLayout
    private lateinit var textView: TextView
    private lateinit var imageView: ImageView

    init {
        init(context)
        attrs?.let { getAttrs(it) }
    }

    private fun init(context: Context?) {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_view, this, false)
        addView(view)

        layout = findViewById(R.id.bg)
        textView = findViewById(R.id.text)
        imageView = findViewById(R.id.symbol)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.layout)
        setTypeArray(typeArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val imgResId = typedArray.getResourceId(R.styleable.layout_symbol, R.drawable.ic_launcher_foreground)
        val text = typedArray.getText(R.styleable.layout_text)

        imageView.setImageResource(imgResId)
        textView.text = text

        typedArray.recycle()
    }
}