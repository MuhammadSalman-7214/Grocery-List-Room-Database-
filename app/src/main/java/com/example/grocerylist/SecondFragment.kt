package com.example.grocerylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.grocerylist.databinding.FragmentSecondBinding
import com.example.grocerylist.enum.GroceryStatus
import com.example.grocerylist.model.Grocery

class SecondFragment : Fragment() {

  private var _binding: FragmentSecondBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private lateinit var viewModel: GroceryViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentSecondBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel = ViewModelProvider(this , ViewModelFactory(activity?.application!!)).get(GroceryViewModel::class.java)
    binding.btnAddGrocery.setOnClickListener {
      val grocery = Grocery(_groceryName = binding.etGroceryName.text.toString() ,
        _groceryPrice = binding.etGroceryPrice.text.toString().toDouble(),
        _groceryStatus = GroceryStatus.PENDING.status)
      viewModel.insertGrocery(grocery)
      findNavController().navigate(R.id.action_SecondFragment_to_bottomNavFragment)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}