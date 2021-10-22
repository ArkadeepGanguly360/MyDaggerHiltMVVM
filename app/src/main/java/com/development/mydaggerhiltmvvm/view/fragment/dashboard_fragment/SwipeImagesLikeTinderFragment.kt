package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.CardStackAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomPopupBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentSwipeImagesLikeTinderBinding
import com.development.mydaggerhiltmvvm.model.CardData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.yuyakaido.android.cardstackview.*

class SwipeImagesLikeTinderFragment : BaseFragment() {

    private lateinit var binding: FragmentSwipeImagesLikeTinderBinding
    private var manager: CardStackLayoutManager? = null
    private var adapter: CardStackAdapter? = null
    var cardList = ArrayList<CardData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_swipe_images_like_tinder, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCardList()
        cardSetUp()
    }

    private fun cardSetUp() {
        manager = CardStackLayoutManager(requireActivity(), object : CardStackListener {
            override fun onCardDragging(direction: Direction, ratio: Float) {
                Log.d("", "onCardDragging: d=" + direction.name + " ratio=" + ratio)
            }

            override fun onCardSwiped(direction: Direction) {
                Log.d("", "onCardSwiped: p=" + manager!!.topPosition + " d=" + direction)
                if (direction == Direction.Right) {
                    Toast.makeText(requireActivity(), "Direction Right", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Top) {
                    Toast.makeText(requireActivity(), "Direction Top", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Left) {
                    Toast.makeText(requireActivity(), "Direction Left", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Bottom) {
                    Toast.makeText(requireActivity(), "Direction Bottom", Toast.LENGTH_SHORT).show()
                }

                // Paginating
                if (manager!!.topPosition == adapter!!.itemCount - 5) {
                    //paginate()
                }
            }

            override fun onCardRewound() {
                Log.d("", "onCardRewound: " + manager!!.topPosition)
            }

            override fun onCardCanceled() {
                Log.d("", "onCardRewound: " + manager!!.topPosition)
            }

            override fun onCardAppeared(view: View, position: Int) {
                val tv = view.findViewById<TextView>(R.id.item_name)
                Log.d("", "onCardAppeared: " + position + ", nama: " + tv.text)
            }

            override fun onCardDisappeared(view: View, position: Int) {
                val tv = view.findViewById<TextView>(R.id.item_name)
                Log.d("", "onCardAppeared: " + position + ", nama: " + tv.text)
            }
        })

        manager!!.setStackFrom(StackFrom.None)
        manager!!.setVisibleCount(3)
        manager!!.setTranslationInterval(8.0f)
        manager!!.setScaleInterval(0.95f)
        manager!!.setSwipeThreshold(0.3f)
        manager!!.setMaxDegree(20.0f)
        manager!!.setDirections(Direction.FREEDOM)
        manager!!.setCanScrollHorizontal(true)
        manager!!.setSwipeableMethod(SwipeableMethod.Manual)
        manager!!.setOverlayInterpolator(LinearInterpolator())
        adapter = CardStackAdapter(requireActivity(), cardList/*addList() as ArrayList<ItemModel>*/)
        binding.cardStackView.setLayoutManager(manager)
        binding.cardStackView.setAdapter(adapter)
        binding.cardStackView.setItemAnimator(DefaultItemAnimator())
    }

    private fun getCardList() {
        cardList.add(
            CardData(
                ContextCompat.getDrawable(requireActivity(), R.drawable.samplee1)!!,
                "arka",
                "21",
                "Jhargram"
            )
        )
        cardList.add(
            CardData(
                ContextCompat.getDrawable(requireActivity(), R.drawable.sample2)!!,
                "deep",
                "22",
                "Jhargram"
            )
        )
        cardList.add(
            CardData(
                ContextCompat.getDrawable(requireActivity(), R.drawable.sample3)!!,
                "gora",
                "22",
                "Jhargram"
            )
        )
        cardList.add(
            CardData(
                ContextCompat.getDrawable(requireActivity(), R.drawable.sample4)!!,
                "abc",
                "21",
                "Jhargram"
            )
        )
        cardList.add(
            CardData(
                ContextCompat.getDrawable(requireActivity(), R.drawable.sample5)!!,
                "hgf",
                "21",
                "Jhargram"
            )
        )
    }
}