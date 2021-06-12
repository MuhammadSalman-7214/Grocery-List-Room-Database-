package com.example.grocerylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.databinding.FragmentFirstBinding
import com.example.grocerylist.enum.GroceryStatus
import com.example.grocerylist.model.Grocery
import com.example.grocerylist.utility.ItemDragSwipeCallback

const val GROCERY_STATUS = "grocery_status"

class FirstFragment : Fragment() , OnGroceryItemCompleteListener{

  private var _binding: FragmentFirstBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private lateinit var viewModel: GroceryViewModel

  private var groceryList: ArrayList<Grocery> = arrayListOf()
  private lateinit var adapter: GroceryListAdapter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentFirstBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel = ViewModelProvider(this , ViewModelFactory(activity?.application!!)).get(GroceryViewModel::class.java)
    setUpRecyclerView()
    setUpObserver()
  }

  private fun setUpRecyclerView() {
    adapter = GroceryListAdapter(groceryList , this)
    binding.rvGroceryList.layoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvGroceryList.adapter = adapter
    val itemTouchHelper = ItemTouchHelper(createHelperCallback())
    itemTouchHelper.attachToRecyclerView(binding.rvGroceryList)
  }

  private fun setUpObserver() {
    val groceryStatus = arguments?.getString(GROCERY_STATUS)
    groceryStatus?.let {
      if (it == GroceryStatus.ALL.status) {
        viewModel.getAllGroceryList().observe(viewLifecycleOwner , { groceriesList ->
          groceryList.clear()
          groceryList.addAll(groceriesList)
          adapter.notifyDataSetChanged()
        })
      } else {
        viewModel.getSpecificGroceryList(it).observe(viewLifecycleOwner , { groceriesList ->
          groceryList.clear()
          groceryList.addAll(groceriesList)
          adapter.notifyDataSetChanged()
        })
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onCompleteGroceryItem(grocery: Grocery) {
    grocery.id = grocery.id
    grocery._groceryName = grocery._groceryName
    grocery._groceryPrice = grocery._groceryPrice
    grocery._groceryStatus = GroceryStatus.COMPLETED.status
    viewModel.updateGroceryStatus(grocery)
  }

  private fun createHelperCallback(): ItemTouchHelper.Callback {
    return ItemDragSwipeCallback(requireContext(), R.color.grey, R.drawable.ic_delete_white,
      0, ItemTouchHelper.LEFT, object : ItemDragSwipeCallback.OnTouchListener {
        override fun onMove(
          recyclerView: RecyclerView?,
          viewHolder: RecyclerView.ViewHolder?,
          target: RecyclerView.ViewHolder?
        ): Boolean {
          return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
          // delete code
          val position = viewHolder!!.adapterPosition
          viewModel.deleteGrocery(groceryList[position])
          adapter.notifyDataSetChanged()
        }
      })
  }

  companion object {
    fun newInstance(groceryStatus: String): FirstFragment {
      return FirstFragment().apply {
        val args = Bundle()
        args.putString(GROCERY_STATUS, groceryStatus)
        arguments = args
      }
    }
  }
}