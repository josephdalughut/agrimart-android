/*
 * Created by Joseph Dalughut on 13/06/2021, 15:28
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.view.popups

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import ng.agrimart.android.databinding.AlertDialogBinding

/**
 * A custom implementation of android default [AlertDialog] with our own custom UI.
 *
 * To build this, please make use of the [Builder] builder class.
 */
class AlertDialog(context: Context, val builder: Builder): AlertDialog(context) {
    
    lateinit var binding: AlertDialogBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlertDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        builder.title?.let {
            binding.txtTitle.text = it
            binding.txtTitle.visibility = View.VISIBLE
        } ?: run {
            if (builder.cancelable) {
                binding.txtTitle.visibility = View.INVISIBLE
            } else {
                binding.txtTitle.visibility = View.GONE
            }
        }

        builder.message?.let {
            binding.txtMessage.text = it
            binding.txtMessage.visibility = View.VISIBLE
        } ?: run {
            binding.txtMessage.visibility = View.GONE
        }
        
        if (builder.cancelable) {
            binding.btnClose.visibility = View.VISIBLE
            binding.btnClose.setOnClickListener {
                dismiss()
            }
            this.setCancelable(true)
        } else {
            this.setCancelable(false)
            builder.title?.let { 
                binding.btnClose.visibility = View.INVISIBLE
            } ?: run {
                binding.btnClose.visibility = View.GONE
            }
        }
        
        builder.positiveButtonTitle?.let {
            binding.btnPositive.visibility = View.VISIBLE
            binding.btnPositive.setOnClickListener {
                dismiss()
                builder.positiveButtonClickListener?.onClick(binding.btnPositive)
            }
        } ?: run {
            binding.btnPositive.visibility = View.GONE
        }

        builder.negativeButtonTitle?.let {
            binding.btnNegative.visibility = View.VISIBLE
            builder.negativeButtonClickListener?.let {
                binding.btnNegative.setOnClickListener(it)
            }
        } ?: run {
            binding.btnNegative.visibility = View.GONE
        }

        builder.icon?.let {
            binding.imgIcon.setImageResource(it)
            binding.imgIcon.visibility = View.VISIBLE
        } ?: run {
            binding.imgIcon.visibility = View.GONE
        }
        
    }

    override fun show() {
        super.show()
        val insetDrawable = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 56)
        window?.setBackgroundDrawable(insetDrawable)
    }



    /**
     * Builder class for the [AlertDialog]
     */
    class Builder(val context: Context) {
        
        internal var title: String? = null
        internal var message: String? = null
        internal var cancelable: Boolean = true
        internal var positiveButtonTitle: String? = null
        internal var negativeButtonTitle: String? = null
        internal var icon: Int? = null
        internal var positiveButtonClickListener: View.OnClickListener? = null
        internal var negativeButtonClickListener: View.OnClickListener? = null
        
        fun title(@StringRes title: Int): Builder {
            this.title = context.getString(title)
            return this
        }

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun message(@StringRes message: Int): Builder {
            this.message = context.getString(message)
            return this
        }

        fun message(message: String): Builder {
            this.message = message
            return this
        }

        fun cancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun positiveButton(@StringRes title: Int, onClickListener: View.OnClickListener): Builder {
            this.positiveButtonTitle = context.getString(title)
            this.positiveButtonClickListener = onClickListener
            return this
        }

        fun positiveButton(title: String, onClickListener: View.OnClickListener): Builder {
            this.positiveButtonTitle = title
            this.positiveButtonClickListener = onClickListener
            return this
        }

        fun negativeButton(@StringRes title: Int, onClickListener: View.OnClickListener): Builder {
            this.negativeButtonTitle = context.getString(title)
            this.negativeButtonClickListener = onClickListener
            return this
        }

        fun negativeButton(title: String, onClickListener: View.OnClickListener): Builder {
            this.negativeButtonTitle = title
            this.negativeButtonClickListener = onClickListener
            return this
        }

        fun icon(@DrawableRes iconRes: Int): Builder {
            this.icon = iconRes
            return this
        }

        fun build(): ng.agrimart.android.view.popups.AlertDialog {
            return AlertDialog(context, this)
        }
        
    }
    
}