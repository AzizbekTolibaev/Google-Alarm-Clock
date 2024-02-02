package com.example.googlealarmclock

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.googlealarmclock.databinding.ActivityAlarmScreenBinding

class AlarmScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmScreenBinding

    private var originalX: Float = 0.0f
    private var lastX = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmScreenBinding.inflate(layoutInflater)
        showOnLockScreenAndTurnScreenOn()
        setContentView(binding.root)

        swipe()
    }

    private fun showOnLockScreenAndTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun swipe() {

        // Animation
        val alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_alpha)
        val putAsideAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_put_aside)
        val turnOffAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_turn_off)

        val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation)
        binding.icAlarm.startAnimation(shakeAnimation)

        binding.bgAnimationPutAside.startAnimation(putAsideAnimation)

        putAsideAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                binding.bgAnimationPutAside.visibility = View.VISIBLE
                binding.bgAnimationTurnOff.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.bgAnimationPutAside.startAnimation(alphaAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        turnOffAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                binding.bgAnimationTurnOff.visibility = View.VISIBLE
                binding.bgAnimationPutAside.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.bgAnimationTurnOff.startAnimation(alphaAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })

        alphaAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                if (binding.bgAnimationPutAside.isVisible) {
                    binding.bgAnimationTurnOff.startAnimation(turnOffAnimation)
                } else {
                    binding.bgAnimationPutAside.startAnimation(putAsideAnimation)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })



        // Swipe to icTurnOff

        binding.apply {
            icTurnOff.setOnTouchListener(object : OnTouchListener {
                var a = true
                var b = true

                override fun onTouch(view: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            lastX = event.x
                            originalX = icTurnOff.x
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val deltaX = event.x - lastX

                            if (deltaX > 0) {
                                if (a && icTurnOff.x >= tvTurnOff.right - icTurnOff.width) {
                                    a = false
                                    Toast.makeText(
                                        this@AlarmScreenActivity,
                                        "Right",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return true
                                }
                                icTurnOff.x += deltaX
                            } else {
                                if (b && icTurnOff.x <= tvPutAside.left + icTurnOff.width) {
                                    b = false
                                    Toast.makeText(
                                        this@AlarmScreenActivity,
                                        "Left",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return true
                                }
                                icTurnOff.x += deltaX
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            if (icTurnOff.x == originalX) {
                                return false
                            }
                            icTurnOff.animate().x(originalX)
                        }
                    }
                    return true
                }
            })
        }
    }
}
