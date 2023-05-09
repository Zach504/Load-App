package com.udacity.loadapp.customcontrols

import android.animation.*
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColor
import com.udacity.loadapp.ButtonState
import com.udacity.loadapp.R

class CustomLoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr), ValueAnimator.AnimatorUpdateListener{

    private var buttonState : ButtonState = ButtonState.Idle

    //Text
    private var textYOffset : Float = 0f

    //Colors
    private lateinit var backgroundColor: Color
    private lateinit var loadingBarColor: Color
    private lateinit var textColor: Color
    private lateinit var loadingTextColor: Color
    private lateinit var idleText: String
    private lateinit var loadingText: String


    //Animator
    private val valueAnimator = ValueAnimator.ofFloat(0f,1f)
    private val ANIMATION_TIME = 2000L

    private var loadPercentage : Float = 0f

    //Loading Circle
    private lateinit var circleDrawable: Drawable
    private var circleArc = 0

    //Loading Bar
    private lateinit var loadingBarDrawable: LayerDrawable
    private lateinit var clipDrawable: ClipDrawable
    private val FULLY_LOADED = 100.0
    private val view = this

    //Paint
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 24.0f * resources.displayMetrics.scaledDensity
        typeface = Typeface.create( "", Typeface.NORMAL)
    }
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    init {
        isClickable = true

        context.withStyledAttributes(attrs, R.styleable.CustomLoadingButton) {
            //Coloring
            backgroundColor = getColor(R.styleable.CustomLoadingButton_backgroundColor, Color.YELLOW).toColor()
            loadingBarColor = getColor(R.styleable.CustomLoadingButton_loadingBarColor, Color.RED).toColor()
            textColor = getColor(R.styleable.CustomLoadingButton_textColor, Color.BLACK).toColor()
            loadingTextColor = getColor(R.styleable.CustomLoadingButton_loadingTextColor, Color.BLACK).toColor()
            //Text
            idleText = getString(R.styleable.CustomLoadingButton_idleText).toString()
            loadingText = getString(R.styleable.CustomLoadingButton_loadingText).toString()
        }

        val textBounds = Rect()
        textPaint.getTextBounds(idleText, 0, idleText.length, textBounds)
        textYOffset = textBounds.exactCenterY()

        valueAnimator.addUpdateListener(this)
        valueAnimator.duration = ANIMATION_TIME
        valueAnimator.repeatCount = 0
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                view.isEnabled = false
                buttonState = ButtonState.Loading
                super.onAnimationStart(animation)
            }

            override fun onAnimationEnd(animation: Animator) {
                view.isEnabled = true
                buttonState = ButtonState.Idle
                loadPercentage = 0f
                invalidate()
                super.onAnimationEnd(animation)
            }
        })

    }

    //Ensure the button is redrawn when the value animator updates the value
    override fun onAnimationUpdate(p0: ValueAnimator) {
        loadPercentage = p0.animatedFraction
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        //canvas.drawArc()
        val backgroundRect = RectF(0f,0f,width.toFloat(),height.toFloat())
        backgroundPaint.color = backgroundColor.toArgb()
        canvas?.drawRoundRect(backgroundRect, 16f, 16f, backgroundPaint)
        when(buttonState){
            ButtonState.Idle -> {
                //Draw Text
                textPaint.color = textColor.toArgb()
                canvas?.drawText(idleText, (width/2).toFloat(), (height/2).toFloat() - textYOffset, textPaint)
            }
            ButtonState.Loading -> {
                //Draw Square
                val loadingRect = RectF(0f,0f,width.toFloat() * loadPercentage ,height.toFloat())
                backgroundPaint.color = loadingBarColor.toArgb()
                canvas?.drawRoundRect(loadingRect, 16f, 16f, backgroundPaint)
                textPaint.color = loadingTextColor.toArgb()
                //Draw Circle
                canvas?.drawArc(width.toFloat() * (13f/15f),height/4f, width.toFloat() * (13f/15f) + height/2f, height.toFloat() * (3f/4f), 0f, 360f * loadPercentage, true, textPaint)
                //Draw Text
                canvas?.drawText(loadingText, (width / 2).toFloat(), (height/2).toFloat() - textYOffset, textPaint
                )
            }
        }
        println()
        super.onDraw(canvas)
    }

    fun getYOffsetForText(text: String, paint: Paint): Float{
        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)
        return textBounds.exactCenterY()
    }

    fun animateLoading() {
        valueAnimator.start()
    }


    /*
    override fun onTimeUpdate(animator: TimeAnimator?, totalTime: Long, deltaTime: Long) {
        //Animation Complete
        if(totalTime > ANIMATION_TIME){
            animator?.cancel()
            loadPercentage = 0
        }
        //Update Button Appearance
        else{
            val newLoadPercentage: Double = (totalTime / ANIMATION_TIME_DOUBLE) * FULLY_LOADED
            loadPercentage = newLoadPercentage.toInt()
        }
        invalidate()
    }

     */



}
