package com.example.instagramandroid


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.instagramandroid.fragments.CardFragment
import com.example.instagramandroid.fragments.DialogFrag
import com.example.instagramandroid.fragments.RecyclerFragment
import com.example.instagramandroid.fragments.TextFragment
import com.example.instagramandroid.rvForList.Task
import com.example.instagramandroid.rvForList.Tasks
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_fragment.*


class MainActivity : AppCompatActivity(),DialogFrag.DialogListener {

    private lateinit var lastFragment: Fragment
    private lateinit var recyclerFragment: RecyclerFragment
    private lateinit var textFragment: TextFragment
    private lateinit var cardFragment: CardFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentId = R.id.frame_layout
        textFragment = TextFragment()
        recyclerFragment = RecyclerFragment()
        cardFragment = CardFragment()
        supportFragmentManager.beginTransaction()
            .add(fragmentId,textFragment)
            .add(fragmentId,recyclerFragment)
            .add(fragmentId,cardFragment)
            .hide(recyclerFragment)
            .hide(cardFragment)
            .commit()
        lastFragment = textFragment

        btm_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.looks_1 -> {
                    if (lastFragment == recyclerFragment){
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.trance2,R.anim.trance3)
                            .hide(lastFragment)
                            .show(textFragment)
                            .commit()
                        lastFragment = textFragment
                    }
                    else
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.alpha_in,R.anim.alpha_out)
                            .hide(lastFragment)
                            .show(textFragment)
                            .commit()
                    lastFragment = textFragment
                    true
                }
                R.id.looks_2 -> {
                    if (lastFragment == textFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.trance,R.anim.trance4)
                            .hide(lastFragment)
                            .show(recyclerFragment)
                            .commit()
                        lastFragment = recyclerFragment
                    }
                    else
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.trance2,R.anim.trance3)
                            .hide(lastFragment)
                            .show(recyclerFragment)
                            .commit()
                    lastFragment = recyclerFragment
                    true
                }
                R.id.looks_3 ->{
                    if (lastFragment==recyclerFragment) {
                        supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.trance,R.anim.trance4)
                            .hide(lastFragment)
                            .show(cardFragment)
                            .commit()
                        lastFragment = cardFragment
                    }
                    else supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.alpha_in,R.anim.alpha_out)
                        .hide(lastFragment)
                        .show(cardFragment)
                        .commit()
                    lastFragment = cardFragment
                    true
                }
                else -> false
            }
        }
        btm_nav.setOnNavigationItemReselectedListener {
        }


    }

    override fun onPositiveClick(dialogFragment: DialogFragment) {
        val title = dialogFragment.dialog?.ed_name?.text.toString()
        val deck = dialogFragment.dialog?.ed_deck?.text.toString()
        val position = dialogFragment.dialog?.ed_count?.text.toString()

        val arr = Tasks.cloneData()

        if(
            position == "" ||
            arr.size <= position.toInt() ||
            position.toInt() <= 0
                )
            arr.add(
                Task(
                    Tasks.findMoreId(),
                    title,
                    deck,
                    R.drawable.ic_delete
                )
            )
        else
            arr.add(
                position.toInt() - 1,
                    Task(Tasks.findMoreId(),
                    title,
                    deck,
                    R.drawable.ic_delete)
            )

        recyclerFragment.updateList(arr)


    }
}