package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.grocerylist.FirstFragment
import com.example.grocerylist.R
import com.example.grocerylist.enum.GroceryStatus
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_bottom_nav.bottomNavView
import kotlinx.android.synthetic.main.fragment_bottom_nav.fab


class BottomNavFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_bottom_nav, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    loadFragment(FirstFragment.newInstance(GroceryStatus.PENDING.status))
    bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    fab.setOnClickListener {
      findNavController().navigate(R.id.action_bottomNavFragment_to_SecondFragment)
    }
  }
  private val mOnNavigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      val fragment: Fragment
      when (item.itemId) {
        R.id.navigation_completed -> {
          fragment = FirstFragment.newInstance(GroceryStatus.COMPLETED.status)
          loadFragment(fragment)
          return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_pending -> {
          fragment = FirstFragment.newInstance(GroceryStatus.PENDING.status)
          loadFragment(fragment)
          return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_all -> {
          fragment = FirstFragment.newInstance(GroceryStatus.ALL.status)
          loadFragment(fragment)
          return@OnNavigationItemSelectedListener true
        }
      }
      false
    }

  private fun loadFragment(fragment: Fragment) {
    // load fragment
    val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
    transaction.replace(R.id.frame_container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
}