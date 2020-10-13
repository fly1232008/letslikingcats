package com.example.letslikingcats

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.letslikingcats.di.HasInject
import com.example.letslikingcats.features.cats.AllCatsFragment
import com.example.letslikingcats.features.cats.FavoriteCatsFragment
import com.example.letslikingcats.ui.InjectableActivity
import com.example.letslikingcats.utils.Permissible
import com.example.letslikingcats.utils.Permissions
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalStateException

class MainActivity : InjectableActivity(R.layout.activity_main), HasInject, Permissible {

    private val allCatsFragment by lazy { AllCatsFragment() }
    private val favoriteCatsFragment by lazy { FavoriteCatsFragment() }
    lateinit var permis: Permissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permis = Permissions(this)
        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.all -> applyFragment(allCatsFragment)
                R.id.favorites -> applyFragment(favoriteCatsFragment)
                else -> throw IllegalStateException("Not implemented item menu")
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottomBar.selectedItemId = R.id.all
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permis.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun applyFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, fragment)
            .commit()
    }

    override fun permissions(): Permissions = permis
}