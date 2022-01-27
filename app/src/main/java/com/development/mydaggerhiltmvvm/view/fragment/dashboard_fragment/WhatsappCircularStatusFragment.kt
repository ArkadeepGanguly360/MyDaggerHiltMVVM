package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.ExpandableChildAdapter
import com.development.mydaggerhiltmvvm.adapter.WhatsappCircularStatusAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentWhatsappCircularStatusBinding
import com.development.mydaggerhiltmvvm.interfaces.CircularStatusViewOnClickListener
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListenerWithView
import com.development.mydaggerhiltmvvm.model.CardData
import com.development.mydaggerhiltmvvm.model.WhatsappStatus
import com.development.mydaggerhiltmvvm.model.WhatsappUser
import com.development.mydaggerhiltmvvm.util.whatsappCircularStatus.CircularStatusView
import com.development.mydaggerhiltmvvm.util.whatsappCircularStatus.DummyDataGenerator
import com.development.mydaggerhiltmvvm.util.whatsappCircularStatus.DummyDataGenerator.generateStatuses
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.android.synthetic.main.whatsapp_circular_status_adapter_item.*
import java.util.*

class WhatsappCircularStatusFragment : BaseFragment() {

    private lateinit var binding: FragmentWhatsappCircularStatusBinding
    var userList = ArrayList<WhatsappUser>()
    var statusList = ArrayList<WhatsappStatus>()
    private lateinit var whatsappCircularStatusAdapter: WhatsappCircularStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(
            R.layout.fragment_whatsapp_circular_status,
            layoutInflater,
            container
        )
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWhatsappStatusAdapter()
        getUsers()
    }

    private fun initWhatsappStatusAdapter() {
        whatsappCircularStatusAdapter = WhatsappCircularStatusAdapter(
            requireActivity(),
            userList,
            object : CircularStatusViewOnClickListener {
                override fun onIemClicked(view: CircularStatusView?, Position: Int?) {
                    val userStatus: WhatsappUser = userList[Position!!]
                    if (!userStatus.allSeen) {
                        for (i in userStatus.statusList.indices) {
                            val status: WhatsappStatus = userStatus.statusList[i]
                            if (!status.isSeen) {
                                //update view
                                view?.setPortionColorForIndex(i, Color.GRAY)
                                //update adapter to prevent changes when scrolling
                                status.isSeen = true
                                break
                            }
                        }
                    }
                    goToNextFragment(
                        R.id.action_whatsappCircularStatusFragment_to_whatsAppStoryViewFragment,
                        null
                    )
                }
            })

        binding.recycleList.apply {
            adapter = whatsappCircularStatusAdapter
        }
    }

    private fun getUsers() {

        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))

        userList.add(
            WhatsappUser(
                ContextCompat.getDrawable(requireActivity(), R.drawable.leeminho)!!,
                "arka",
                false,
                statusList
            )
        )
        userList.add(
            WhatsappUser(
                ContextCompat.getDrawable(requireActivity(), R.drawable.leejongsuk)!!,
                "deep",
                false,
                statusList
            )
        )
        userList.add(
            WhatsappUser(
                ContextCompat.getDrawable(requireActivity(), R.drawable.chaeunwoo)!!,
                "gora",
                false,
                statusList
            )
        )
        userList.add(
            WhatsappUser(
                ContextCompat.getDrawable(requireActivity(), R.drawable.seokangjoon)!!,
                "abc",
                false,
                statusList
            )
        )
        userList.add(
            WhatsappUser(
                ContextCompat.getDrawable(requireActivity(), R.drawable.kimsoohyun)!!,
                "hgf",
                false,
                statusList
            )
        )

        for (i in 0..userList.size) {
            val statusList: MutableList<WhatsappStatus> = ArrayList<WhatsappStatus>()
            for (j in 0 until DummyDataGenerator.getCount(i)) {
                statusList.add(WhatsappStatus(DummyDataGenerator.generateIsSeenForStatus(j, i)))
            }
        }
    }
}