package com.example.instagramandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramandroid.R
import com.example.instagramandroid.rvForList.Task
import com.example.instagramandroid.rvForList.TaskAdapter
import com.example.instagramandroid.rvForList.Tasks
import kotlinx.android.synthetic.main.recycler_fragment.*

class RecyclerFragment : Fragment() {

    private var adapter: TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.recycler_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(Tasks.tasks) { i: Int ->
            val arr = Tasks.cloneData()
            arr.removeAt(i)

            adapter?.updateDS(arr)
        }
        rv_list.adapter = adapter

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
        }

        floating_action_btn.setOnClickListener{
            val dialogFrag = DialogFrag()
            dialogFrag.show(fragmentManager!!,"new_item")

            rv_list.scrollToPosition(0)
        }




        rv_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                adapter!!.onItemDelete(viewHolder.layoutPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_list)


    }

    fun updateList(newList: ArrayList<Task>){
        adapter?.updateDS(newList)
        rv_list.scrollToPosition(0)
    }
}