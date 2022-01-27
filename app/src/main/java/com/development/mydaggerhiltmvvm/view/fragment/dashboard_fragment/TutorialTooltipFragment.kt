package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentTutorialTooltipBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousIntentBinding
import com.development.mydaggerhiltmvvm.util.tooltip.TooltipBuilder
import com.development.mydaggerhiltmvvm.util.tooltip.TooltipContentPosition
import com.development.mydaggerhiltmvvm.util.tooltip.TooltipDialog
import com.development.mydaggerhiltmvvm.util.tooltip.TooltipObject
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment


class TutorialTooltipFragment : BaseFragment() {

    private lateinit var binding: FragmentTutorialTooltipBinding
    var tooltipDialog: TooltipDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_tutorial_tooltip, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUglyTooltip()

        binding.iv3.setOnClickListener {
            startUglyTooltips()
        }
    }

    private fun initUglyTooltip() {
        tooltipDialog = TooltipBuilder()
            .setPackageName(requireActivity().packageName)
            .titleTextColorRes(R.color.white)
            .textColorRes(R.color.white)
            .shadowColorRes(R.color.shadow)
            .titleTextSizeRes(R.dimen.text_size)
            .textSizeRes(R.dimen.text_normal)
            .spacingRes(R.dimen.spacing_normal)
            .backgroundContentColorRes(R.color.darker_gray)
            .circleIndicatorBackgroundDrawableRes(R.drawable.selector_circle)
            .prevString(R.string.previous)
            .nextString(nextStringText = "Sonraki")
            .finishString(finishStringText = "Bitir da!")
            .useCircleIndicator(true)
            .showBottomContainer(false)
            .clickable(true)
            .useArrow(true)
            .useSkipWord(false)
            .setFragmentManager(requireActivity().supportFragmentManager)
            .lineColorRes(R.color.line_color)
            .lineWidthRes(R.dimen.line_width)
            .shouldShowIcons(true)
            .setTooltipRadius(R.dimen.tooltip_radius)
            .showSpotlight(true)
            .build()
    }

    fun startUglyTooltips() {
        val tooltips: ArrayList<TooltipObject> = ArrayList()

        tooltips.add(
            TooltipObject(
                binding.iv3,
                null,
                "No title, just description, simple text."
            )
        )

        tooltips.add(
            TooltipObject(
                binding.tvTest2,
                null,
                "No title, just description, simple text.",
                tooltipContentPosition = TooltipContentPosition.RIGHT
            )
        )

        tooltips.add(
            TooltipObject(
                binding.tvTest3,
                "<font color=\"#FFC300\"> an ImageView </font>",
                "This HTML description point to <font color=\"#FFC300\"> an ImageView </font> as you can see.<br/><br/> Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                tooltipContentPosition = TooltipContentPosition.LEFT
            )
        )

        tooltips.add(
            TooltipObject(
                binding.iv4,
                "This is a title",
                "This is a description, but a little longer than number 3 but shorter than number 5 that you will see soon."
            )
        )

        tooltips.add(
            TooltipObject(
                binding.tvTest,
                "This is a title",
                "This is a description, but a little longer than number 3 but shorter than number 5 that you will see soon."
            )
        )

        tooltips.add(
            TooltipObject(
                binding.iv5,
                "This is another title",
                "This HTML description point to <font color=\"#FFC300\"> an ImageView </font> as you can see.<br/><br/> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suo enim quisque studio maxime ducitur. Scio enim esse quosdam, qui quavis lingua philosophari possint; Animum autem reliquis rebus ita perfecit, ut corpus; Quo modo autem optimum, si bonum praeterea nullum est?"
            )
        )

        tooltips.add(
            TooltipObject(
                binding.iv6,
                "This is another one",
                "This description point to number 6. <font color=\"#FFC300\"> This is yellow text </font> and this is white.",
                tooltipContentPosition = TooltipContentPosition.UNDEFINED,
                tintBackgroundColor = ResourcesCompat.getColor(resources, R.color.blue, null),
                null
            )
        )

        tooltips.add(
            TooltipObject(
                binding.tvTest4,
                "<font color=\"#FFC300\"> an ImageView </font>",
                "This HTML description point to <font color=\"#FFC300\"> an ImageView </font> as you can see.<br/><br/> Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                tooltipContentPosition = TooltipContentPosition.LEFT
            )
        )

        tooltipDialog?.show(requireActivity(), requireActivity().supportFragmentManager, "SHOWCASE_TAG", tooltips)
    }
}