package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.view.*
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.GmailLikeRecyclerviewAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentGmailLikeRecyclerviewBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.GmailData
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment


class GmailLikeRecyclerviewFragment : BaseFragment() {

    private lateinit var binding: FragmentGmailLikeRecyclerviewBinding
    private var gmailList = ArrayList<GmailData>()
    private lateinit var gmailLikeRecyclerviewAdapter: GmailLikeRecyclerviewAdapter

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(
            R.layout.fragment_gmail_like_recyclerview,
            layoutInflater,
            container
        )
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMyFriendListAdapter()
    }

    private fun initMyFriendListAdapter() {
        gmailLikeRecyclerviewAdapter = GmailLikeRecyclerviewAdapter(
            requireActivity(),
            gmailList,
            object : RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {
                  //  enableActionMode(position)
                }
            })

        binding.recyclerView.apply {
            adapter = gmailLikeRecyclerviewAdapter
        }
    }

   /* private fun enableActionMode(position: Int) {
        if (actionMode == null)
            actionMode = (this.activity as DashboardActivity?)!! startSupportActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(
                    mode: ActionMode,
                    menu: Menu
                ): Boolean {
                    mode.menuInflater.inflate(R.menu.menu_action_mode, menu)
                    return true
                }

                override fun onPrepareActionMode(
                    mode: ActionMode,
                    menu: Menu
                ): Boolean {
                    return false
                }

                override fun onActionItemClicked(
                    mode: ActionMode,
                    item: MenuItem
                ): Boolean {
                    if (item.itemId == R.id.action_delete) {
                        gmailLikeRecyclerviewAdapter.deleteEmails()
                        mode.finish()
                        return true
                    }
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {
                    gmailLikeRecyclerviewAdapter.selectedItems.clear()
                    val emails: List<GmailData> = gmailLikeRecyclerviewAdapter.getEmails() as List<GmailData>
                    for (email in emails) {
                        if (email.isSelected) email.isSelected = false
                    }
                    gmailLikeRecyclerviewAdapter.notifyDataSetChanged()
                    actionMode = null
                }
            })
        gmailLikeRecyclerviewAdapter.toggleSelection(position)
        val size: Int = gmailLikeRecyclerviewAdapter.selectedItems.size()
        if (size == 0) {
            actionMode!!.finish()
        } else {
            actionMode!!.title = size.toString() + ""
            actionMode!!.invalidate()
        }
    }*/
}