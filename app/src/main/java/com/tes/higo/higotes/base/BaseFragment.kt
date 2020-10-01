package com.tes.higo.higotes.base

import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.tes.higo.higotes.app.AppPreference
import dagger.android.support.DaggerFragment
import javax.inject.Inject


abstract class BaseFragment<B : ViewDataBinding> : DaggerFragment() {

    lateinit var binding: B

    @Inject
    lateinit var appPreference : AppPreference
    lateinit var progressDialog : ProgressDialog

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId(), container, false)
        setProgressDialog()
        return binding.root
    }

    private fun setProgressDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }

    abstract fun layoutResId() : Int

    fun launchActivity(cls: Class<*>?) {
        val i = Intent(activity, cls)
        startActivity(i)
    }

    fun launchActivity(bundle: Bundle, cls: Class<*>?) {
        val i = Intent(activity, cls)
        i.putExtras(bundle)
        startActivity(i)
    }

    fun moveToAnotherFragment(containerFrame: Int, fragment: DaggerFragment) {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerFrame, fragment)
        fragmentTransaction.addToBackStack("tag")
        fragmentTransaction.commit()
    }

    fun showProgressDialog() {
        try {
            progressDialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun hideProgressDialog() {
        try {
            progressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}